package com.datamelt.artikel.app.web.util;

import java.util.Map;

public class Form
{
    public static class Product
    {
        public static final String[] FIELDNAMES = {"name", "description", "number","quantity","weight","price","producer_id", "container_id", "origin_id"};
        public static final String NAME = "name";
        public static final String DESCRIPTION = "descriptiion";
        public static final String NUMBER = "number";
        public static final String QUANTITY = "quantity";
        public static final String WEIGHT = "weight";
        public static final String PRICE = "price";
    }

    public static class Producer
    {
        public static final String NAME = "name";
    }

    public static class Market
    {
        public static final String NAME = "name";
    }



}
