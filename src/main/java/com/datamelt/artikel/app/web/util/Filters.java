package com.datamelt.artikel.app.web.util;

import com.datamelt.artikel.adapter.opa.OpaHandler;
import com.datamelt.artikel.adapter.opa.model.OpaInput;
import com.datamelt.artikel.adapter.opa.model.OpaValidationResult;
import com.datamelt.artikel.util.Constants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.Locale;

public class Filters
{
    private static final Logger logger = LoggerFactory.getLogger(Filters.class);
    private static OpaHandler handler;

    public static void setOpaHandler(OpaHandler opaHandler)
    {
        handler = opaHandler;
    }

    public static Filter redirectToLogin = (Request request, Response response) -> {
        String test = request.pathInfo();
        if(!request.pathInfo().equals(Endpoints.LOGIN.getPath()) && !request.pathInfo().equals(Endpoints.AUTHENTICATE.getPath()) && !request.pathInfo().equals(Endpoints.ABOUT.getPath()) && !request.pathInfo().equals(Endpoints.INDEX.getPath()) && !request.pathInfo().equals(Endpoints.NOTAUTHORIZED.getPath()))
        {
            if(request.session().attribute(Constants.USERTOKEN_KEY)!=null)
            {
                try
                {
                    Jws<Claims> token = Token.parseToken(request.session().attribute(Constants.USERTOKEN_KEY));
                    OpaInput opaInput = new OpaInput(request.pathInfo(), request.requestMethod().toLowerCase(), request.session().attribute(Constants.USERTOKEN_KEY));
                    OpaValidationResult userHasAccess = handler.validateUser(opaInput);
                    if (!userHasAccess.getResult())
                    {
                        response.redirect(Endpoints.NOTAUTHORIZED.getPath());
                    }
                }
                catch(SecurityException ex)
                {
                    logger.error("error - the user token signature is invalid");
                    request.session().removeAttribute(Constants.USERTOKEN_KEY);
                    response.redirect(Endpoints.LOGIN.getPath());
                }
                catch (ExpiredJwtException ex)
                {
                    logger.error("error - the user token has expired");
                    request.session().removeAttribute(Constants.USERTOKEN_KEY);
                    response.redirect(Endpoints.LOGIN.getPath());
                }
                catch (JwtException ex)
                {
                    logger.error("error - the user token is invalid");
                    request.session().removeAttribute(Constants.USERTOKEN_KEY);
                    response.redirect(Endpoints.LOGIN.getPath());
                }
            }
            else
            {
                response.redirect(Endpoints.LOGIN.getPath());
            }
        }
    };

}
