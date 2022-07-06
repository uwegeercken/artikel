package com.datamelt.artikel.app.web.util;

import com.datamelt.artikel.util.Constants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

public class Filters
{
    private static final Logger logger = LoggerFactory.getLogger(Filters.class);

    public static Filter redirectToLogin = (Request request, Response response) -> {
        if(!request.pathInfo().equals(Path.Web.LOGIN) && !request.pathInfo().equals(Path.Web.ABOUT) && !request.pathInfo().equals(Path.Web.INDEX))
        {
            if(request.session().attribute(Constants.USERTOKEN_KEY)!=null)
            {
                try
                {
                    Jws<Claims> token = Token.parseToken(request.session().attribute(Constants.USERTOKEN_KEY));
                    logger.info("received valid token for user [{}], expires [{}]", token.getBody().getSubject(), token.getBody().getExpiration());
                }
                catch(SecurityException ex)
                {
                    logger.error("error - the user token signature is invalid");
                    request.session().removeAttribute(Constants.USERTOKEN_KEY);
                    response.redirect(Path.Web.LOGIN);
                }
                catch (ExpiredJwtException ex)
                {
                    logger.error("error - the user token has expired");
                    request.session().removeAttribute(Constants.USERTOKEN_KEY);
                    response.redirect(Path.Web.LOGIN);
                }
                catch (JwtException ex)
                {
                    logger.error("error - the user token is invalid");
                    request.session().removeAttribute(Constants.USERTOKEN_KEY);
                    response.redirect(Path.Web.LOGIN);
                }

            }
            else
            {
                response.redirect(Path.Web.LOGIN);
            }

        }
    };

    public static Filter redirectToOrders = (Request request, Response response) ->
    {
        response.redirect(Path.Web.ORDERS);

    };
}
