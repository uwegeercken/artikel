package com.datamelt.artikel.app.web.util;

public class Path
{
    public static class Web {
        public static final String INDEX = "/index/";
        public static final String ABOUT = "/about/";
        public static final String PRODUCTS = "/products/";
        public static final String PRODUCT = "/product/:id/";
        public static final String PRODUCT_DELETE = "/product/delete/:id/";
        public static final String PRODUCERS = "/producers/";
        public static final String MARKETS = "/markets/";
        public static final String PRODUCTCONTAINERS = "/productcontainers/";
        public static final String PRODUCTORIGINS = "/productorigins/";
    }

    public static class Template {
        public static final String INDEX = "/velocity/index.vm";
        public static final String ABOUT = "/velocity/about.vm";
        public static final String PRODUCTS = "/velocity/products.vm";
        public static final String PRODUCT = "/velocity/editproduct.vm";
        public static final String PRODUCT_DELETE = "/velocity/deleteproduct.vm";
        public static final String PRODUCERS = "/velocity/producers.vm";
        public static final String MARKETS = "/velocity/markets.vm";
        public static final String PRODUCTCONTAINERS = "/velocity/productcontainers.vm";
        public static final String PRODUCTORIGINS = "/velocity/productorigins.vm";

        public static final String NOTFOUND = "/velocity/notfound.vm";
    }
}
