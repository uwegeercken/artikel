package com.datamelt.artikel.adapter.opa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpaInput
{
    @JsonProperty
    OpaInputDetails input;

    public OpaInput(String path, String method, String token)
    {
        input = new OpaInputDetails(path, method, token);
    }


}
