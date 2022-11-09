package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.csv.CsvLabelFileWriter;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.adapter.email.EmailHandler;
import com.datamelt.artikel.adapter.opa.OpaHandler;
import com.datamelt.artikel.adapter.order.OrderDocumentGenerator;
import com.datamelt.artikel.adapter.web.*;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.app.web.util.Filters;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.app.web.util.Endpoints;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.service.WebService;
import com.datamelt.artikel.util.FileUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static spark.Spark.*;

public class WebApplication
{
    private static final Logger logger =  LoggerFactory.getLogger(WebApplication.class);

    public static final String APPLCATION_VERSION = "v2.6";
    public static final String APPLCATION_LAST_UPDATE = "09.11.2022";

    private static SecretKey secretKey = null;
    private static MessageBundleInterface messages;
    private static NumberFormatter numberFormatter;

    public static void main(String[] args) throws Exception
    {
        logger.info("initializing web application");
        logger.info("web application version [{}], last update [{}]", APPLCATION_VERSION, APPLCATION_LAST_UPDATE);
        MainConfiguration configuration=null;
        if(args!=null && args.length>0)
        {
            logger.info("loading configuration from file: [{}] ", args[0]);
            configuration = new ConfigurationLoader().getMainConfiguration(args[0]);
        }
        else
        {
            logger.error("a configuration yaml file is required");
            System.exit(1);
        }
        if(!configurationFilesAndFoldersOk(configuration))
        {
            System.exit(1);
        }

        if(configuration.getSparkJava().getPort()>0)
        {
            port(configuration.getSparkJava().getPort());
        }

        messages = new MessageBundle(configuration.getSparkJava().getLocale());
        numberFormatter = new NumberFormatter(configuration.getSparkJava().getLocale());

        staticFiles.location("/public");
        staticFiles.expireTime(configuration.getSparkJava().getStaticfilesExpiretime());

        try
        {
            secretKey = KeyGenerator.getInstance("HmacSha256").generateKey();
        }
        catch (Exception ex)
        {
            logger.error("error generating secret key [{}]", ex.getMessage());
            System.exit(1);
        }

        WebServiceInterface service = null;
        OpaHandler opaHandler = new OpaHandler(configuration);
        try
        {
            service = new WebService(new SqliteRepository(configuration.getDatabase()), new CsvLabelFileWriter(configuration), new OrderDocumentGenerator(configuration), new EmailHandler(), opaHandler);
        }
        catch(Exception ex)
        {
            logger.error("error initializing web service [{}]", ex.getMessage());
            System.exit(1);
        }

        Filters.setOpaHandler(opaHandler);

        IndexController indexController = new IndexController(service);
        LoginController loginController = new LoginController(service, configuration);
        UserController userController = new UserController(service);
        ProductController productController = new ProductController(service);
        ProducerController producerController = new ProducerController(service);
        MarketController marketController = new MarketController(service);
        ProductContainerController containerController = new ProductContainerController(service);
        ProductOriginController originController = new ProductOriginController(service);
        ProductOrderController orderController = new ProductOrderController(service, configuration);

        before("*", Filters.redirectToLogin);
        //before("*", Filters.validateUserHasAccess);

        get(Endpoints.INDEX.getPath(), indexController.serveIndexPage);
        get(Endpoints.ABOUT.getPath(), indexController.serveAboutPage);
        get(Endpoints.NOTAUTHORIZED.getPath(), indexController.serveNotAuthorizedPage);
        get(Endpoints.LOGIN.getPath(), loginController.serveLoginPage);
        get(Endpoints.LOGOUT.getPath(), loginController.logoutUser);
        post(Endpoints.AUTHENTICATE.getPath(), loginController.authenticateUser);

        get(Endpoints.USERS.getPath(), userController.serveAllUsersPage);
        get(Endpoints.USER_SELECT_UPDATE.getPath(), userController.serveUserPage);
        post(Endpoints.USER_UPDATE.getPath(), userController.serveUpdateUserPage);
        get(Endpoints.USERS_SELECT_CHANGE_PASSWORD.getPath(), userController.serveChangePasswordPage);
        post(Endpoints.USERS_CHANGE_PASSWORD.getPath(), userController.serveUpdatePasswordPage);

        get(Endpoints.PRODUCTS.getPath(), productController.serveAllProductsPage);
        get(Endpoints.GENERATE_LABELS.getPath(), productController.createLabels);
        get(Endpoints.GENERATE_SHOP_LABELS.getPath(), productController.createShopLabels);
        get(Endpoints.PRODUCT_SELECT_UPDATE.getPath(), productController.serveProductPage);
        post(Endpoints.PRODUCT_UPDATE.getPath(), productController.serveUpdateProductPage);
        get(Endpoints.PRODUCT_SELECT_DELETE.getPath(), productController.serveDeleteProductPage);
        post(Endpoints.PRODUCT_DELETE.getPath(), productController.deleteProduct);
        post(Endpoints.PRODUCT_SHOP.getPath(), productController.shopProduct);
        post(Endpoints.PRODUCT_SHOP_LABELS.getPath(), productController.shopProductLabels);
        //get(Endpoints.SHOPPRODUCTS.getPath(), productController.serveShopProductsPage);
        //get(Endpoints.PRODUCT_SHOP_AMOUNT.getPath(), productController.shopProductAmount);
        get(Endpoints.PRODUCT_SHOP_REMOVE.getPath(), productController.shopProductRemove);
        post(Endpoints.SHOP_SELECT_COMPLETE.getPath(), productController.shopProductComplete);

        get(Endpoints.ORDERS.getPath(), orderController.serveAllOrdersPage);
        get(Endpoints.ORDERITEMS.getPath(), orderController.serveOrderItemsPage);
        get(Endpoints.ORDERITEMS_PDF.getPath(), orderController.generateOrderPdf);
        get(Endpoints.ORDER_SELECT_DELETE.getPath(), orderController.serveDeleteProductOrderPage);
        post(Endpoints.ORDER_DELETE.getPath(), orderController.deleteProductOrder);
        get(Endpoints.SELECT_ORDER_EMAIL.getPath(), orderController.selectOrderEmailAddress);
        post(Endpoints.ORDER_EMAIL.getPath(), orderController.generateOrderEmail);

        get(Endpoints.PRODUCERS.getPath(), producerController.serveAllProducersPage);
        get(Endpoints.PRODUCER_SELECT_UPDATE.getPath(), producerController.serveProducerPage);
        post(Endpoints.PRODUCER_UPDATE.getPath(), producerController.serveUpdateProducerPage);
        get(Endpoints.PRODUCER_SELECT_DELETE.getPath(), producerController.serveDeleteProducerPage);
        post(Endpoints.PRODUCER_DELETE.getPath(), producerController.deleteProducer);

        get(Endpoints.MARKETS.getPath(), marketController.serveAllMarketsPage);

        get(Endpoints.PRODUCTCONTAINERS.getPath(), containerController.serveAllProductContainersPage);
        get(Endpoints.PRODUCTCONTAINER_SELECT_UPDATE.getPath(), containerController.serveProductContainerPage);
        post(Endpoints.PRODUCTCONTAINER_UPDATE.getPath(), containerController.serveUpdateProductContainerPage);
        get(Endpoints.PRODUCTCONTAINER_SELECT_DELETE.getPath(), containerController.serveDeleteProductContainerPage);
        post(Endpoints.PRODUCTCONTAINER_DELETE.getPath(), containerController.deleteProductContainer);

        get(Endpoints.PRODUCTORIGINS.getPath(), originController.serveAllProductOriginsPage);
        get(Endpoints.PRODUCTORIGIN_SELECT_UPDATE.getPath(), originController.serveProductOriginPage);
        post(Endpoints.PRODUCTORIGIN_UPDATE.getPath(), originController.serveUpdateProductOriginPage);

        get("*", indexController.serveNotFoundPage);
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

    public static SecretKey getSecretKey() { return secretKey; }
}
