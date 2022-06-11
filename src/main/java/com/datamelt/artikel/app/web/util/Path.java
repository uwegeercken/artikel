package com.datamelt.artikel.app.web.util;

public class Path
{
    public static class Web {
        public static final String INDEX = "/index/";
        public static final String ABOUT = "/about/";
        public static final String LOGIN = "/login/";
        public static final String LOGOUT = "/logout/";
        public static final String PRODUCTS = "/products/producer/:producerid/";
        public static final String USERS = "/users/";
        public static final String GENERATE_LABELS = "/products/labels/:producerid/";
        public static final String GENERATE_SHOP_LABELS = "/products/shoplabels/:producerid/";
        public static final String PRODUCT = "/product/:id/producer/:producerid/";
        public static final String PRODUCT_DELETE = "/product/delete/:id/producer/:producerid/";
        public static final String PRODUCERS = "/producers/";
        public static final String PRODUCER = "/producer/:id/";
        public static final String PRODUCER_DELETE = "/producer/delete/:id/";
        public static final String MARKETS = "/markets/";
        public static final String PRODUCTCONTAINERS = "/productcontainers/";
        public static final String PRODUCTCONTAINER = "/productcontainer/:id/";
        public static final String PRODUCTORIGINS = "/productorigins/";
        public static final String PRODUCTORIGIN = "/productorigin/:id/";
        public static final String PRODUCT_SHOP = "/product/shop/:id/producer/:producerid/" ;
        public static final String PRODUCT_SHOP_LABELS = "/product/labels/:id/producer/:producerid/" ;
        public static final String PRODUCT_SHOP_AMOUNT = "/product/shop/amount/:id/producer/:producerid/" ;
        public static final String PRODUCT_SHOP_REMOVE = "/product/shop/remove/:id/producer/:producerid/" ;
        public static final String SHOP_COMPLETE = "/shopcomplete/producer/:producerid/" ;
        public static final String SHOPPRODUCTS = "/shopproducts/producer/:producerid/";
        public static final String ORDERS = "/orders/";
        public static final String ORDERITEMS = "/orderitems/:id/";
        public static final String ORDERITEMS_PDF = "/orderitemspdf/:id/";
    }

    public static class Template {
        public static final String INDEX = "/velocity/index.vm";
        public static final String ABOUT = "/velocity/about.vm";
        public static final String LOGIN = "/velocity/login.vm";
        public static final String USERS = "/velocity/users.vm";
        public static final String PRODUCTS = "/velocity/products.vm";
        public static final String PRODUCT = "/velocity/editproduct.vm";
        public static final String SHOPPRODUCTS = "/velocity/shopproducts.vm";
        public static final String SHOPPRODUCTS_NO_ORDERING = "/velocity/shopproducts_noordering.vm";
        public static final String PRODUCT_DELETE = "/velocity/deleteproduct.vm";
        public static final String PRODUCERS = "/velocity/producers.vm";
        public static final String PRODUCER = "/velocity/editproducer.vm";
        public static final String PRODUCER_DELETE = "/velocity/deleteproducer.vm";
        public static final String MARKETS = "/velocity/markets.vm";
        public static final String PRODUCTCONTAINERS = "/velocity/productcontainers.vm";
        public static final String PRODUCTCONTAINER = "/velocity/editproductcontainer.vm";
        public static final String PRODUCTORIGINS = "/velocity/productorigins.vm";
        public static final String PRODUCTORIGIN = "/velocity/editproductorigin.vm";
        public static final String ORDERS = "/velocity/orders.vm";
        public static final String ORDERITEMS = "/velocity/orderitems.vm";

        public static final String NOTFOUND = "/velocity/notfound.vm";
    }
}
