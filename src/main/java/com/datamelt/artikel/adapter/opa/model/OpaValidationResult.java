package com.datamelt.artikel.adapter.opa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpaValidationResult
{
    @JsonProperty
    boolean result;

    public boolean getResult()
    {
        return result;
    }
}
