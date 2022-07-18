package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.app.web.util.HashGenerator;
import com.datamelt.artikel.model.*;
import com.datamelt.artikel.config.CsvInput;
import com.datamelt.artikel.port.FileInterface;
import com.datamelt.artikel.service.LoaderService;
import com.datamelt.artikel.util.Constants;
import com.datamelt.artikel.util.CsvFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;

public class CsvLoader implements FileInterface
{
    private static final Logger logger =  LoggerFactory.getLogger(CsvLoader.class);

    private final LoaderService service;
    private CsvInput configuration;

    private SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);

    public CsvLoader(LoaderService service, CsvInput configuration)
    {
        this.service = service;
        this.configuration = configuration;
    }

    public void processFile(CsvFileType fileType)
    {
        switch (fileType)
        {
            case PRODUCT:
                this.processProductFile(getCsvFile(configuration.getProductsFilename()));
                break;
            case PRODUCER:
                this.processProducerFile(getCsvFile(configuration.getProducersFilename()));
                break;
            case MARKET:
                this.processMarketFile(getCsvFile(configuration.getMarketsFilename()));
                break;
            case ORDER:
                this.processOrderFile(getCsvFile(configuration.getOrdersFilename()));
                break;
            case ORDERITEMS:
                this.processOrderItemFile(getCsvFile(configuration.getOrderitemsFilename()));
                break;
            case CONTAINER:
                this.processProductContainerFile(getCsvFile(configuration.getProductContainersFilename()));
                break;
            case ORIGIN:
                this.processProductOriginFile(getCsvFile(configuration.getProductOriginsFilename()));
                break;
            case USER:
                this.processUserFile(getCsvFile(configuration.getUsersFilename()));
        }
    }

    private File getCsvFile(String filename)
    {
        File csvFile = null;
        try
        {
            csvFile = new File(configuration.getFilesFolder() + "/" + filename);
        }
        catch (Exception ex)
        {
            logger.error("error with csv file: [{}] ", configuration.getFilesFolder() + "/" + filename);
        }
        return csvFile;
    }

    private void processProductFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    Producer producer = getProducerByName(fields[7]);
                    ProductOrigin origin = getProductOriginByName(fields[8]);
                    ProductContainer container = getProductContainerByName(fields[9]);
                    if(producer!=null)
                    {
                        Product product = new Product(fields[0]);
                        product.setName(fields[1]);
                        product.setTitle(fields[2]);
                        product.setSubtitle(fields[3]);
                        product.setQuantity(Integer.parseInt(fields[4]));
                        product.setWeight(Double.parseDouble(fields[5]));
                        product.setPrice(Double.parseDouble(fields[6]));
                        product.setContainer(container);
                        product.setProducer(producer);
                        product.setOrigin(origin);

                        boolean exists = getExistProduct(fields[0]);
                        if(!exists)
                        {
                            addProduct(product);
                            counter++;
                        }
                        else
                        {
                            logger.info("already existing - product: [{}]", fields[0]);
                        }
                    }
                    else
                    {
                       logger.error("referenced producer unavailable. id: [{}]", fields[6]);
                    }
                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added products: [{}]", counter);
        }
        catch (Exception e)
        {
            logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    private void processProductContainerFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    ProductContainer container = new ProductContainer(fields[0]);
                    boolean exists = getExistProductContainer(fields[0]);
                    if(!exists)
                    {
                        addProductContainer(container);
                        counter++;
                    }
                    else
                    {
                        logger.warn("already existing - product container: [{}]", fields[0]);
                    }
                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added product containers: [{}]", counter);
        }
        catch (Exception e)
        {
            logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    private void processProductOriginFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    ProductOrigin origin = new ProductOrigin(fields[0]);
                    boolean exists = getExistProductOrigin(fields[0]);
                    if(!exists)
                    {
                        addProductOrigin(origin);
                        counter++;
                    }
                    else
                    {
                        logger.warn("already existing - product origin: [{}]", fields[0]);
                    }
                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added product origins: [{}]", counter);
        }
        catch (Exception e)
        {
            logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    private void processUserFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    User user = new User(fields[0]);
                    boolean exists = getExistUser(fields[0]);
                    if(!exists)
                    {
                        user.setFullName(fields[1]);
                        user.setPassword(HashGenerator.generate(fields[2]));
                        user.setRole(fields[3]);
                        addUser(user);
                        counter++;
                    }
                    else
                    {
                        logger.warn("already existing - user: [{}]", fields[0]);
                    }
                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added user: [{}]", counter);
        }
        catch (Exception e)
        {
            logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    private void processProducerFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    Producer producer = new Producer(fields[0]);
                    producer.setNoOrdering(Integer.parseInt(fields[1]));
                    producer.setEmailAddress(fields[2]);
                    boolean exists = getExistProducer(fields[0]);
                    if(!exists)
                    {
                        addProducer(producer);
                        counter++;
                    }
                    else
                    {
                        logger.warn("already existing - producer: [{}]", fields[0]);
                    }

                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added producers: [{}]", counter);
        }
        catch (Exception e)
        {
           logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    private void processMarketFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    Market market = new Market(fields[0]);
                    market.setType(fields[1]);
                    boolean exists = getExistMarket(fields[0]);
                    if(!exists)
                    {
                        addMarket(market);
                        counter++;
                    }
                    else
                    {
                        logger.warn("already existing - market: [{}]", fields[0]);
                    }
                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added markets: [{}]", counter);
        }
        catch (Exception e)
        {
            logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    private void processOrderFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    long timestamp = formatter.parse(fields[2]).getTime();

                    Producer producer = getProducerByName(fields[1]);
                    ProductOrder order = new ProductOrder(producer.getId());
                    order.setNumber(fields[0]);
                    order.setTimestampCreatedDate(timestamp);
                    boolean exists = getExistOrder(fields[0]);
                    if(!exists)
                    {
                        addProductOrder(order);
                        counter++;
                    }
                    else
                    {
                        logger.warn("already existing - order: [{}]", fields[0]);
                    }
                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added orders: [{}]", counter);
        }
        catch (Exception e)
        {
            logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    private void processOrderItemFile(File inputFile)
    {
        logger.info("loading data from csv file: [{}] ", inputFile.getName());
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(inputFile));
            String line;
            long counter=0;
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                String orderNumber = fields[0];
                String productNumber = fields[1];
                int amount = Integer.parseInt(fields[2]);
                try
                {
                    ProductOrder order = getOrderByNumber(orderNumber);
                    Product product = getProductByNumber(productNumber);

                    if(product !=null && product.getProducer().getNoOrdering()==0 && order != null)
                    {
                        boolean exists = getExistOrderItem(order.getId(),product.getId());
                        if (!exists)
                        {
                            ProductOrderItem item = new ProductOrderItem();
                            item.setProductOrderId(order.getId());
                            item.getProduct().setId(product.getId());
                            item.setAmount(amount);
                            addOrderItem(item);
                            counter++;
                        } else
                        {
                            logger.warn("already existing - order item: [{}], product: [{}]", order.getNumber(), product.getName());
                        }
                    }
                    else
                    {
                        if(order == null)
                        {
                            logger.error("order item not added - referenced order unavailable. number: [{}]" + orderNumber);
                        }
                        if(product ==null )
                        {
                            logger.error("order item not added - referenced product unavailable. number: [{}]", productNumber);
                        }
                        else if(product.getProducer().getNoOrdering()!=0)
                        {
                            logger.error("order item not added - referenced product is unavailable for ordering. name: [{}]", product.getName());
                        }
                    }

                } catch (Exception ex)
                {
                    logger.error("error processing file [{}], message: [{}]", inputFile.getName(),ex.getMessage());
                }
            }
            logger.info("added order items: [{}]", counter);

        }
        catch (Exception e)
        {
            logger.error("error processing file [{}], message: [{}]", inputFile.getName(),e.getMessage());
        }
    }

    @Override
    public void addProduct(Product product) { service.addProduct(product); }

    @Override
    public Product getProductById(long id) throws Exception { return service.getProductById(id); }

    @Override
    public boolean getExistProduct(String number) throws Exception { return service.getExistProduct(number); }

    @Override
    public void addProductContainer(ProductContainer container) { service.addProductContainer(container); }

    @Override
    public ProductContainer getProductContainerByName(String name) throws Exception
    {
        return service.getProducContainerByName(name);
    }

    @Override
    public boolean getExistProductContainer(String name) throws Exception { return service.getExistProductContainer(name); }

    @Override
    public void addProductOrigin(ProductOrigin origin) { service.addProductOrigin(origin); }

    @Override
    public ProductOrigin getProductOriginByName(String name) throws Exception
    {
        return service.getProducOriginByName(name);
    }

    @Override
    public boolean getExistProductOrigin(String name) throws Exception { return service.getExistProductOrigin(name); }

    @Override
    public void addProducer(Producer producer) { service.addProducer(producer); }

    @Override
    public boolean getExistProducer(String name) throws Exception { return service.getExistProducer(name); }

    @Override
    public Producer getProducerByName(String name) throws Exception { return service.getProducerByName(name); }

    @Override
    public Product getProductByNumber(String number) throws Exception { return service.getProductByNumber(number); }

    @Override
    public void addMarket(Market market) { service.addMarket(market); }

    @Override
    public boolean getExistMarket(String name) throws Exception { return service.getExistMarket(name); }

    @Override
    public void addProductOrder(ProductOrder order) { service.addProductOrder(order); }

    @Override
    public ProductOrder getOrderByNumber(String number) throws Exception { return service.getOrderByNumber(number); }

    @Override
    public boolean getExistOrder(String number) throws Exception { return service.getExistOrder(number); }

    @Override
    public void addOrderItem(ProductOrderItem item) { service.addOrderItem(item); }

    @Override
    public boolean getExistOrderItem(long orderId, long productId) throws Exception { return service.getExistOrderItem(orderId,productId);
    }

    @Override
    public void addUser(User user) { service.addUser(user); }

    @Override
    public boolean getExistUser(String name) throws Exception { return service.getExistUser(name); }

}
