package com.datamelt.artikel.port;

import com.datamelt.artikel.model.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface CollectionHandlerInterface
{
    long getAllProductsCount(Connection connection) throws Exception;
    Map<String,Long> getAllProducersProductsCount(Connection connection) throws Exception;
    List<Product> getAllProducts(Connection connection, long producerId, boolean availableOnly, int changedSinceNumberOfDaysMin, int changedSinceNumberOfDaysMax) throws Exception;
    List<Producer> getAllProducers(Connection connection) throws Exception;
    List<Market> getAllMarkets(Connection connection) throws Exception;
    List<ProductContainer> getAllProductContainers(Connection connection) throws Exception;
    List<ProductOrigin> getAllProductOrigins(Connection connection) throws Exception;
    List<ProductOrder> getAllProductOrders(Connection connection) throws Exception;
    Map<Long, ProductOrderItem> getAllProductOrderItems(Connection connection, long orderId) throws Exception;
    List<User> getAllUsers(Connection connection) throws Exception;
    List<ProductHistory> getProductHistory(Connection connection, Product product) throws Exception;

}
