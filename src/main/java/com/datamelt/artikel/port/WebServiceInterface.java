package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.opa.model.OpaInput;
import com.datamelt.artikel.adapter.opa.model.OpaValidationResult;
import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.*;

import java.util.List;
import java.util.Map;

public interface WebServiceInterface
{
    List<Product> getAllProducts(long producerId, boolean availableOnly, int changedSinceNumberOfDaysMin, int changedSinceNumberOfDaysMax) throws Exception;
    List<Product> getAllProductsForStickers() throws Exception;
    List<Product> getChangedProducts(long producerId, int limit) throws Exception;
    Product getProductById(long id) throws Exception;
    Product updateProduct(long id, Form form, NumberFormatter numberFormatter) throws Exception;
    Product addProduct(Form form, NumberFormatter numberFormatter) throws Exception;
    void deleteProduct(long id) throws Exception;
    boolean getExistProduct(String number) throws Exception;
    boolean getIsUniqueProduct(long id, String number) throws Exception;
    List<ProductHistory> getProductHistory(Product product) throws Exception;

    Producer updateProducer(long id, Form form) throws Exception;
    Producer addProducer(Form form) throws Exception;
    Producer getProducerById(long id) throws Exception;
    List<Producer> getAllProducers() throws Exception;
    boolean getIsUniqueProducer(long id, String name) throws Exception;
    void deleteProducer(long id) throws Exception;


    List<ProductOrigin> getAllProductOrigins() throws Exception;
    ProductOrigin getProductOriginById(long id) throws Exception;
    ProductOrigin updateProductOrigin(long id, Form form) throws Exception;
    ProductOrigin addProductOrigin(Form form) throws Exception;
    boolean getIsUniqueProductOrigin(long id, String name) throws Exception;

    List<Market> getAllMarkets() throws Exception;
    Market getMarketById(long id) throws Exception;
    Market updateMarket(long id, Form form) throws Exception;
    Market addMarket(Form form) throws Exception;
    boolean getIsUniqueMarket(long id, String name) throws Exception;
    void deleteMarket(long id) throws Exception;

    List<ProductContainer> getAllProductContainers() throws Exception;
    ProductContainer getProductContainerById(long id) throws Exception;
    ProductContainer updateProductContainer(long id, Form form) throws Exception;
    ProductContainer addProductContainer(Form form) throws Exception;
    boolean getIsUniqueProductContainer(long id, String name) throws Exception;
    void deleteProductContainer(long id) throws Exception;
    void deleteProductOrder(long id) throws Exception;
    void updateOrderEmailSent(ProductOrder order) throws Exception;

    void createDatabaseTables() throws Exception;

    User getUserByName(String name) throws Exception;
    User getUserById(long id) throws Exception;
    List<User> getAllUsers() throws Exception;
    void updateUser(User user) throws Exception;
    User updateUser(long id, Form form) throws Exception;
    User addUser(Form form) throws Exception;
    boolean getIsUniqueUser(long id, String name) throws Exception;

    Map<Long, ProductOrderItem> getShopProductOrderItems(ProductOrder order) throws Exception;
    void addProductOrder(ProductOrder order);
    List<ProductOrder> getAllProductOrders() throws Exception;
    ProductOrder getProductOrderById(long id) throws Exception;

    byte[] getProductLabelsOutputFile(List<ProductLabel> productLabels) throws Exception;
    void printProductStickers(List<ProductSticker> productStickers, int quantity) throws Exception;
    byte [] getOrderDocument(Producer producer, ProductOrder order, List<Product> products) throws Exception;
    String getOrderDocumentFilename(ProductOrder order);

    long getAllProductsCount() throws Exception;
    Product getLastChangedProduct() throws Exception;
    Map<String,Long> getAllProducersProductsCount() throws Exception;

    boolean sendEmail(ProductOrder order, String emailRecipient, MainConfiguration configuration);

    OpaValidationResult validateUser(OpaInput input);
    void sendAcl() throws Exception;
    void sendPolicies() throws Exception;
}
