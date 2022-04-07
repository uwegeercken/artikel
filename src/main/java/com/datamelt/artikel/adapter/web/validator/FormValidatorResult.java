package com.datamelt.artikel.adapter.web.validator;

public class FormValidatorResult
{
    private boolean resultOk;
    private String message;

    public FormValidatorResult(boolean resultOk, String message)
    {
        this.resultOk = resultOk;
        this.message = message;
    }

    public boolean getResultOk()
    {
        return resultOk;
    }

    public String getMessage()
    {
        return message;
    }
}
