package com.datamelt.artikel.adapter.opa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpaAclUrl
{
    String path;
    String method;
    String role;

    public OpaAclUrl(String path, String method, String role)
    {
        this.path = path;
        this.method = method;
        this.role = role;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}
