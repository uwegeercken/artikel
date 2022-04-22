package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.adapter.web.*;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.app.web.util.Filters;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.service.WebService;
import com.datamelt.artikel.app.web.util.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class WebApplication
{
    private static final Logger logger =  LoggerFactory.getLogger(WebApplication.class);

    public static final String APPLCATION_VERSION = "v0.2";
    public static final String APPLCATION_LAST_UPDATE = "15.04.2022";

    public static void main(String[] args) throws Exception
    {
        logger.info("initializing web application");
        MainConfiguration configuration;
        if(args!=null && args.length>0)
        {
            logger.info("loading configuration from file: [{}] ", args[0]);
            configuration = new ConfigurationLoader().getMainConfiguration(args[0]);
        }
        else
        {
            throw new Exception("a configuration yaml file is required");
        }
        MessageBundle messages = new MessageBundle("de");

        staticFiles.location("/public");
        staticFiles.expireTime(configuration.getSparkJava().getStaticfilesExpiretime());

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
        get(Path.Web.PRODUCT_DELETE, productController.serveDeleteProductPage);
        post(Path.Web.PRODUCT_DELETE, productController.deleteProduct);

        get(Path.Web.PRODUCERS, producerController.serveAllProducersPage);
        get(Path.Web.PRODUCER, producerController.serveProducerPage);
        post(Path.Web.PRODUCER, producerController.serveUpdateProducerPage);
        get(Path.Web.PRODUCER_DELETE, producerController.serveDeleteProducerPage);
        post(Path.Web.PRODUCER_DELETE, producerController.deleteProducer);

        get(Path.Web.MARKETS, marketController.serveAllMarketsPage);
        get(Path.Web.PRODUCTCONTAINERS, containerController.serveAllProductContainersPage);
        get(Path.Web.PRODUCTORIGINS, originController.serveAllProductOriginsPage);

        get("*", indexController.serveNotFoundPage);

    }
}
