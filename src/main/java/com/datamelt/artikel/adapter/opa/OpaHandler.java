package com.datamelt.artikel.adapter.opa;

import com.datamelt.artikel.adapter.opa.model.OpaAcl;
import com.datamelt.artikel.adapter.opa.model.OpaInput;
import com.datamelt.artikel.adapter.opa.model.OpaValidationResult;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.port.OpaApiInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpaHandler implements OpaApiInterface
{
    private static final Logger logger =  LoggerFactory.getLogger(OpaHandler.class);

    private static final String ENDPOINT_VALIDATE = "/v1/data/artikel/acl/allow";
    private static final String ENDPOINT_ACL = "/v1/data/artikel/acl";
    private static final String ENDPOINT_POLICIES = "/v1/policies/artikel";

    private final WebTarget webTarget;
    private final MainConfiguration configuration;

    public OpaHandler(MainConfiguration configuration) {
        this.configuration = configuration;
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 30000)
                .property(ClientProperties.CONNECT_TIMEOUT, 5000);

        webTarget = ClientBuilder
                .newClient(clientConfig)
                .target(configuration.getOpa().getHost());

        logger.info("configured client for open policy agent at [{}]",configuration.getOpa().getHost());
    }


    @Override
    public OpaValidationResult validateUser(OpaInput input)
    {
        ObjectMapper mapper = new JsonMapper();
        String json = null;
        try
        {
            json = mapper.writeValueAsString(input);
        }
        catch (Exception ex)
        {
            logger.error("error creating json from opa input object");
        }

        OpaValidationResult opaValidationResult = null;
        String result = null;
        try
        {
            result = webTarget
                .path(ENDPOINT_VALIDATE)
                .request()
                .post(Entity.json(json))
                .readEntity(String.class);

            opaValidationResult = mapper.readValue(result, OpaValidationResult.class);
        }
        catch (Exception ex)
        {
            logger.error("error opa validation for given input [{}]", input);
        }

        return opaValidationResult;
    }

    @Override
    public int sendAcl(OpaAcl acl) throws Exception
    {
        ObjectMapper mapper = new JsonMapper();
        String json = null;
        try
        {
            json = mapper.writeValueAsString(acl);
        }
        catch (Exception ex)
        {
            logger.error("error creating json from opa acl object");
        }

        int status = 0;
        try
        {
            status = webTarget
                    .path(ENDPOINT_ACL)
                    .request()
                    .put(Entity.json(json))
                    .getStatus();
        }
        catch (Exception ex)
        {
            throw new Exception("error sending acl to opa host: " + configuration.getOpa().getHost()) ;
        }
        return status;
    }

    @Override
    public int sendPolicies(String rego) throws Exception
    {
        int status = 0;
        try
        {
            status = webTarget
                .path(ENDPOINT_POLICIES)
                .request()
                .put(Entity.json(rego))
                .getStatus();
        }
        catch (Exception ex)
        {
            throw new Exception("error sending policies to opa host: " + configuration.getOpa().getHost()) ;
        }
        return status;
    }
}
