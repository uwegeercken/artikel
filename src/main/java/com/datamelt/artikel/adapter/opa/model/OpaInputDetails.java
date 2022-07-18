package com.datamelt.artikel.adapter.opa.model;

import com.datamelt.artikel.app.web.util.Token;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OpaInputDetails
{
    @JsonProperty
    String token;
    @JsonProperty
    String method;
    @JsonProperty
    String path;

    public OpaInputDetails(String path, String method, String token)
    {
        this.path = path;
        this.method = method;
        this.token = token;
    }
}
