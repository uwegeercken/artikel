package com.datamelt.artikel.adapter.opa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.list.SynchronizedList;

import java.util.ArrayList;
import java.util.List;

public class OpaAcl
{
    @JsonProperty
    private List<String> users = new ArrayList<>();
    @JsonProperty
    private List<OpaAclUrl> urls = new ArrayList<>();

    public OpaAcl()
    {

    }

    public List<String> getUsers()
    {
        return users;
    }

    public void addUser(String name)
    {
        users.add(name);
    }

    public void setUsers(List<String> users)
    {
        this.users = users;
    }

    public List<OpaAclUrl> getUrls()
    {
        return urls;
    }

    public void addUrl(OpaAclUrl url)
    {
        urls.add(url);
    }

    public void setUrls(List<OpaAclUrl> urls)
    {
        this.urls = urls;
    }
}
