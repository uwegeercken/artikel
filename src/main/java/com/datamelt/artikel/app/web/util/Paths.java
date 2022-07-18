package com.datamelt.artikel.app.web.util;

public enum Paths
{
    INDEX("/index/","read"),
    ABOUT("/about/","read"),
    LOGIN("/login/","read"),
    LOGOUT("/logout/","read"),
    PRODUCTS("/products/producer/:producerid/","read"),
    USERS("/users/","admin"),
    USERS_CHANGE_PASSWORD("/users/changepassword/:id/","readwrite"),
    GENERATE_LABELS("/products/labels/:producerid/","read"),
    GENERATE_SHOP_LABELS("/products/shoplabels/:producerid/","read"),
    PRODUCT("/product/:id/producer/:producerid/","read"),
    PRODUCT_DELETE("/product/delete/:id/producer/:producerid/","readwrite"),
    PRODUCERS("/producers/","read"),
    PRODUCER("/producer/:id/","read"),
    PRODUCER_DELETE("/producer/delete/:id/","readwrite"),
    MARKETS("/markets/","read"),
    PRODUCTCONTAINERS("/productcontainers/","read"),
    PRODUCTCONTAINER("/productcontainer/:id/","read"),
    PRODUCTCONTAINER_DELETE("/productcontainer/delete/:id/","readwrite"),
    PRODUCTORIGINS("/productorigins/","read"),
    PRODUCTORIGIN("/productorigin/:id/","read"),
    PRODUCT_SHOP("/product/shop/:id/producer/:producerid/" ,"read"),
    PRODUCT_SHOP_LABELS("/product/labels/:id/producer/:producerid/" ,"read"),
    PRODUCT_SHOP_AMOUNT("/product/shop/amount/:id/producer/:producerid/" ,"read"),
    PRODUCT_SHOP_REMOVE("/product/shop/remove/:id/producer/:producerid/" ,"read"),
    SHOP_COMPLETE("/shopcomplete/producer/:producerid/" ,"read"),
    SHOPPRODUCTS("/shopproducts/producer/:producerid/","read"),
    ORDERS("/orders/","read"),
    ORDERITEMS("/orderitems/:id/","read"),
    ORDER_DELETE("/order/delete/:id/","readwrite"),
    ORDERITEMS_PDF("/orderitemspdf/:id/","read"),
    SELECT_ORDER_EMAIL("/selectorderemail/:id/","read"),
    ORDER_EMAIL("/orderitemsemail/:id/","read")
    ;

    private final String path;
    private final String role;

    private Paths(String path, String role)
    {
        this.path = path;
        this.role = role;
    }

    public String getPath()
    {
        return path;
    }

    public String getRole()
    {
        return role;
    }
}
