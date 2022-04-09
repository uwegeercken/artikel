package com.datamelt.artikel.adapter.web.validator;

public class ValidatorResult
{
    public static final int RESULT_TYPE_OK = 0;
    public static final int RESULTYPE_ERROR = 1;

    private int resultType;
    private String message;

    public ValidatorResult(String message)
    {
        this.resultType = RESULT_TYPE_OK;
        this.message = message;
    }

    public ValidatorResult(int resultType, String message)
    {
        this.resultType = resultType;
        this.message = message;
    }

    public int getResultType()
    {
        return resultType;
    }

    public String getMessage()
    {
        return message;
    }
}
