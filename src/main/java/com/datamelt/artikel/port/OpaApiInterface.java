package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.opa.model.OpaAcl;
import com.datamelt.artikel.adapter.opa.model.OpaInput;
import com.datamelt.artikel.adapter.opa.model.OpaValidationResult;

public interface OpaApiInterface
{
    OpaValidationResult validateUser(OpaInput input);
    int sendAcl(OpaAcl acl);
    int sendPolicies(String rego);
}
