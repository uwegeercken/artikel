package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.adapter.web.*;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.app.web.util.Filters;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.service.WebService;
import com.datamelt.artikel.app.web.util.Path;

import static spark.Spark.*;

public class WebApplication
{
    public static final String APPLCATION_VERSION = "v0.2";
    public static final String APPLCATION_LAST_UPDATE = "08.04.2022";

    public static void main(String[] args) throws Exception
    {

        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        //enableDebugScreen();

        MainConfiguration configuration = new ConfigurationLoader().getMainConfiguration();
        MessageBundle messages = new MessageBundle("de");

        WebService service = new WebService(new SqliteRepository(configuration.getDatabase()));
        IndexController indexController = new IndexController(service, messages);
        ProductController productController = new ProductController(service, messages);
        ProducerController producerController = new ProducerController(service, messages);
        MarketController marketController = new MarketController(service, messages);
        ProductContainerController containerController = new ProductContainerController(service, messages);
        ProductOriginController originController = new ProductOriginController(service, messages);

        before("*", Filters.redirectToIndex);
        before("*", Filters.addTrailingSlashes);

        get(Path.Web.INDEX, indexController.serveIndexPage);
        get(Path.Web.ABOUT, indexController.serveAboutPage);

        get(Path.Web.PRODUCTS, productController.serveAllProductsPage);
        get(Path.Web.PRODUCT, productController.serveProductPage);
        post(Path.Web.PRODUCT, productController.serveUpdateProductPage);

        get(Path.Web.PRODUCERS, producerController.serveAllProducersPage);
        get(Path.Web.PRODUCER, producerController.serveProducerPage);
        post(Path.Web.PRODUCER, producerController.serveUpdateProducerPage);

        get(Path.Web.MARKETS, marketController.serveAllMarketsPage);
        get(Path.Web.PRODUCTCONTAINERS, containerController.serveAllProductContainersPage);
        get(Path.Web.PRODUCTORIGINS, originController.serveAllProductOriginsPage);

        get("*", indexController.serveNotFoundPage);

    }
}
