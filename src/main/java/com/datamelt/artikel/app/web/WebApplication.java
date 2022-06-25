package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.csv.CsvLabelFileWriter;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.adapter.email.EmailHandler;
import com.datamelt.artikel.adapter.order.OrderDocumentGenerator;
import com.datamelt.artikel.adapter.web.*;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.app.web.util.Filters;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.service.WebService;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.util.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class WebApplication
{
    private static final Logger logger =  LoggerFactory.getLogger(WebApplication.class);

    public static final String APPLCATION_VERSION = "v1.4";
    public static final String APPLCATION_LAST_UPDATE = "23.06.2022";

    private static MessageBundleInterface messages;
    private static NumberFormatter numberFormatter;

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
        if(!configurationFilesAndFoldersOk(configuration))
        {
            System.exit(1);
        }

        messages = new MessageBundle(configuration.getSparkJava().getLocale());
        numberFormatter = new NumberFormatter(configuration.getSparkJava().getLocale());

        staticFiles.location("/public");
        staticFiles.expireTime(configuration.getSparkJava().getStaticfilesExpiretime());

        WebServiceInterface service = new WebService(new SqliteRepository(configuration.getDatabase()), new CsvLabelFileWriter(configuration), new OrderDocumentGenerator(configuration), new EmailHandler());
        IndexController indexController = new IndexController(service);
        LoginController loginController = new LoginController(service);
        UserController userController = new UserController(service);
        ProductController productController = new ProductController(service);
        ProducerController producerController = new ProducerController(service);
        MarketController marketController = new MarketController(service);
        ProductContainerController containerController = new ProductContainerController(service);
        ProductOriginController originController = new ProductOriginController(service);
        ProductOrderController orderController = new ProductOrderController(service, configuration);

        before("*", Filters.redirectToLogin);

        get(Path.Web.INDEX, indexController.serveIndexPage);
        get(Path.Web.ABOUT, indexController.serveAboutPage);
        get(Path.Web.LOGIN, loginController.serveLoginPage);
        get(Path.Web.LOGOUT, loginController.logoutUser);
        post(Path.Web.LOGIN, loginController.authenticateUser);

        get(Path.Web.USERS, userController.serveAllUsersPage);

        get(Path.Web.PRODUCTS, productController.serveAllProductsPage);
        get(Path.Web.GENERATE_LABELS, productController.createLabels);
        get(Path.Web.GENERATE_SHOP_LABELS, productController.createShopLabels);
        get(Path.Web.PRODUCT, productController.serveProductPage);
        post(Path.Web.PRODUCT, productController.serveUpdateProductPage);
        get(Path.Web.PRODUCT_DELETE, productController.serveDeleteProductPage);
        post(Path.Web.PRODUCT_DELETE, productController.deleteProduct);
        get(Path.Web.PRODUCT_SHOP, productController.shopProduct);
        get(Path.Web.PRODUCT_SHOP_LABELS, productController.shopProductLabels);
        get(Path.Web.SHOPPRODUCTS, productController.serveShopProductsPage);
        get(Path.Web.PRODUCT_SHOP_AMOUNT, productController.shopProductAmount);
        get(Path.Web.PRODUCT_SHOP_REMOVE, productController.shopProductRemove);
        post(Path.Web.SHOP_COMPLETE, productController.shopProductComplete);

        get(Path.Web.ORDERS, orderController.serveAllOrdersPage);
        get(Path.Web.ORDERITEMS, orderController.serveOrderItemsPage);
        get(Path.Web.ORDERITEMS_PDF, orderController.generateOrderPdf);
        get(Path.Web.ORDER_DELETE, orderController.serveDeleteProductOrderPage);
        post(Path.Web.ORDER_DELETE, orderController.deleteProductOrder);
        get(Path.Web.SELECT_ORDER_EMAIL, orderController.selectOrderEmailAddress);
        post(Path.Web.ORDER_EMAIL, orderController.generateOrderEmail);

        get(Path.Web.PRODUCERS, producerController.serveAllProducersPage);
        get(Path.Web.PRODUCER, producerController.serveProducerPage);
        post(Path.Web.PRODUCER, producerController.serveUpdateProducerPage);
        get(Path.Web.PRODUCER_DELETE, producerController.serveDeleteProducerPage);
        post(Path.Web.PRODUCER_DELETE, producerController.deleteProducer);

        get(Path.Web.MARKETS, marketController.serveAllMarketsPage);

        get(Path.Web.PRODUCTCONTAINERS, containerController.serveAllProductContainersPage);
        get(Path.Web.PRODUCTCONTAINER, containerController.serveProductContainerPage);
        post(Path.Web.PRODUCTCONTAINER, containerController.serveUpdateProductContainerPage);
        get(Path.Web.PRODUCTCONTAINER_DELETE, containerController.serveDeleteProductContainerPage);
        post(Path.Web.PRODUCTCONTAINER_DELETE, containerController.deleteProductContainer);

        get(Path.Web.PRODUCTORIGINS, originController.serveAllProductOriginsPage);
        get(Path.Web.PRODUCTORIGIN, originController.serveProductOriginPage);
        post(Path.Web.PRODUCTORIGIN, originController.serveUpdateProductOriginPage);

        get("*", indexController.serveNotFoundPage);

        //after(Path.Web.SHOP_COMPLETE,  Filters.redirectToOrders);
    }

    private static boolean configurationFilesAndFoldersOk(MainConfiguration configuration)
    {
        boolean allOk = true;

        boolean sparkTempFolderOk = FileUtility.checkReadWriteAccessFolder(configuration.getSparkJava().getTempFolder());
        if(!sparkTempFolderOk)
        {
            allOk = false;
        }

        boolean glabelsFileOk = FileUtility.checkReadAccessFile(configuration.getLabels().getGlabelsFile());
        if(!glabelsFileOk)
        {
            allOk = false;
        }

        boolean glablesPdfOutputFolderOk = FileUtility.checkReadWriteAccessFolder(configuration.getLabels().getPdfOutputFolder());
        if(!glablesPdfOutputFolderOk)
        {
            allOk = false;
        }

        boolean glabelsBinaryOk = FileUtility.checkExecuteAccessFile(configuration.getLabels().getGlabelsBinary());
        if(!glabelsBinaryOk)
        {
            allOk = false;
        }

        boolean asciidocTemplateFileFolderOk = FileUtility.checkReadAccessFolder(configuration.getAsciidoc().getTemplateFileFolder());
        if(!asciidocTemplateFileFolderOk)
        {
            allOk = false;
        }

        boolean asciidocPdfOutputFolderOk = FileUtility.checkReadWriteAccessFolder(configuration.getAsciidoc().getPdfOutputFolder());
        if(!asciidocPdfOutputFolderOk)
        {
            allOk = false;
        }
        return allOk;
    }

    public static MessageBundleInterface getMessages()
    {
        return messages;
    }

    public static NumberFormatter getNumberFormatter()
    {
        return numberFormatter;
    }
}
