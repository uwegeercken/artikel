package com.datamelt.artikel.util;

public enum DateOfPacking
{
    YESTERDAY(-1),
    TODAY(0),
    TOMORROW(1);

    private final int dateOffset;

    DateOfPacking(int dateOffset)
    {
        this.dateOffset = dateOffset;
    }

    public int getDateOffset()
    {
        return dateOffset;
    }
}
