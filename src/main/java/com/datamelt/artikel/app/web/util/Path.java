package com.datamelt.artikel.app.web.util;

public class Path
{
    public static class Web {
        public static final String INDEX = "/index/";
        public static final String ABOUT = "/about/";
        public static final String PRODUCTS = "/products/";
        public static final String PRODUCT = "/product/:id/";
        public static final String PRODUCERS = "/producers/";
    }

    public static class Template {
        public static final String INDEX = "/velocity/index.vm";
        public static final String ABOUT = "/velocity/about.vm";
        public static final String PRODUCTS = "/velocity/products.vm";
        public static final String PRODUCT = "/velocity/editproduct.vm";
        public static final String PRODUCERS = "/velocity/producers.vm";
        public static final String NOTFOUND = "/velocity/notfound.vm";
    }
}
