package com.datamelt.artikel.app.web.util;

public enum Endpoints
{
    INDEX("/index/","read", "get"),
    ABOUT("/about/","read", "get"),
    ALLDOCUMENTS("/alldocuments/*","read", "get"),
    DOCUMENTS("/documents/*","read", "get"),
    LOGIN("/login/","read", "get"),
    AUTHENTICATE("/authenticate/","read", "post"),
    LOGOUT("/logout/","read", "get"),
    PRODUCTS("/products/producer/:producerid/","read", "get"),
    USERS("/users/","admin", "get"),
    USER_SELECT_UPDATE("/user/:id/","readwrite", "get"),
    USER_UPDATE("/user/:id/","readwrite", "post"),
    USERS_SELECT_CHANGE_PASSWORD("/users/changepassword/:id/","readwrite", "get"),
    USERS_CHANGE_PASSWORD("/users/changepassword/:id/","readwrite", "post"),
    GENERATE_LABELS("/products/labels/:producerid/","readwrite", "get"),
    GENERATE_SHOP_LABELS("/products/shoplabels/:producerid/","readwrite", "get"),
    PRODUCT_SELECT_UPDATE("/product/:id/producer/:producerid/","readwrite", "get"),
    PRODUCT_UPDATE("/product/:id/producer/:producerid/","readwrite", "post"),
    PRODUCT_SELECT_DELETE("/product/delete/:id/producer/:producerid/","readwrite", "get"),
    PRODUCT_DELETE("/product/delete/:id/producer/:producerid/","readwrite", "post"),
    PRODUCERS("/producers/","read", "get"),
    PRODUCER_SELECT_UPDATE("/producer/:id/","readwrite", "get"),
    PRODUCER_UPDATE("/producer/:id/","readwrite", "post"),
    PRODUCER_SELECT_DELETE("/producer/delete/:id/","readwrite", "get"),
    PRODUCER_DELETE("/producer/delete/:id/","readwrite", "post"),
    MARKETS("/markets/","read", "get"),
    PRODUCTCONTAINERS("/productcontainers/","read", "get"),
    PRODUCTCONTAINER_SELECT_UPDATE("/productcontainer/:id/","readwrite", "get"),
    PRODUCTCONTAINER_UPDATE("/productcontainer/:id/","readwrite", "post"),
    PRODUCTCONTAINER_SELECT_DELETE("/productcontainer/delete/:id/","readwrite", "get"),
    PRODUCTCONTAINER_DELETE("/productcontainer/delete/:id/","readwrite", "post"),
    PRODUCTORIGINS("/productorigins/","read", "get"),
    PRODUCTORIGIN_SELECT_UPDATE("/productorigin/:id/","readwrite", "get"),
    PRODUCTORIGIN_UPDATE("/productorigin/:id/","readwrite", "post"),
    PRODUCT_SHOP("/shopproducts/producer/:producerid/" ,"readwrite", "post"),
    PRODUCT_SHOP_LABELS("/productlabels/producer/:producerid/" ,"readwrite", "post"),
    PRODUCT_SHOP_AMOUNT("/product/shop/amount/:id/producer/:producerid/" ,"readwrite", "get"),
    PRODUCT_SHOP_REMOVE("/product/shop/remove/:id/producer/:producerid/" ,"readwrite", "get"),
    SHOP_SELECT_COMPLETE("/shopcomplete/producer/:producerid/" ,"readwrite", "get"),
    SHOP_COMPLETE("/shopcomplete/producer/:producerid/" ,"readwrite", "post"),
    SHOPPRODUCTS("/shopproducts/producer/:producerid/","readwrite", "get"),
    ORDERS("/orders/","read", "get"),
    ORDERITEMS("/orderitems/:id/","read", "get"),
    ORDER_SELECT_DELETE("/order/delete/:id/","readwrite", "get"),
    ORDER_DELETE("/order/delete/:id/","readwrite", "post"),
    ORDERITEMS_PDF("/orderitemspdf/:id/","readwrite", "get"),
    SELECT_ORDER_EMAIL("/selectorderemail/:id/","readwrite", "get"),
    ORDER_EMAIL("/orderitemsemail/:id/","readwrite", "get"),
    NOTAUTHORIZED("/notauthorized/","read", "get");

    private final String path;
    private final String role;
    private final String method;

    Endpoints(String path, String role, String method)
    {
        this.path = path;
        this.role = role;
        this.method = method;
    }

    public String getPath()
    {
        return path;
    }

    public String getMethod() { return method; }

    public String getRole()
    {
        return role;
    }
}
