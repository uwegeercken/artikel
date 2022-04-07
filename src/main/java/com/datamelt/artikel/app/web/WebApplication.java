package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.adapter.web.IndexController;
import com.datamelt.artikel.adapter.web.MessageBundle;
import com.datamelt.artikel.adapter.web.ProducerController;
import com.datamelt.artikel.adapter.web.ProductController;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.app.web.util.Filters;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.service.WebService;
import com.datamelt.artikel.app.web.util.Path;

import static spark.Spark.*;

public class WebApplication
{
    public static final String APPLCATION_VERSION = "v0.1";
    public static final String APPLCATION_LAST_UPDATE = "30.03.2022";

    public static void main(String[] args)
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

        before("*", Filters.addTrailingSlashes);

        get(Path.Web.INDEX, indexController.serveIndexPage);
        get(Path.Web.ABOUT, indexController.serveAboutPage);

        get(Path.Web.PRODUCTS, productController.serveAllProductsPage);
        get(Path.Web.PRODUCT, productController.serveProductPage);
        post(Path.Web.PRODUCT, productController.serveUpdateProductPage);

        get(Path.Web.PRODUCERS, producerController.serveAllProducersPage);



        get("*", indexController.serveNotFoundPage);

    }
}
