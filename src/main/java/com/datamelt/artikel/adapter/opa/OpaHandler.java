package com.datamelt.artikel.adapter.opa;

import com.datamelt.artikel.adapter.opa.model.OpaAcl;
import com.datamelt.artikel.adapter.opa.model.OpaInput;
import com.datamelt.artikel.adapter.opa.model.OpaValidationResult;
import com.datamelt.artikel.app.web.WebApplication;
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

    public OpaHandler(MainConfiguration configuration) {
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
        return webTarget
            .path(ENDPOINT_VALIDATE)
            .request()
            .post(Entity.json(input))
            .readEntity(OpaValidationResult.class);
    }

    @Override
    public int sendAcl(OpaAcl acl)
    {
        ObjectMapper xxx = new JsonMapper();
        String json = null;
        try
        {
            json = xxx.writeValueAsString(acl);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return webTarget
                .path(ENDPOINT_ACL)
                .request()
                .put(Entity.json(json))
                .getStatus();
    }

    @Override
    public int sendPolicies(String rego)
    {
        return webTarget
                .path(ENDPOINT_POLICIES)
                .request()
                .put(Entity.json(rego))
                .getStatus();

    }
}
