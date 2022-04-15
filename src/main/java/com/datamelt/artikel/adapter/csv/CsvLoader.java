package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.config.MainConfiguration;
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
import java.net.URL;
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

    public static void main(String[] args) throws Exception
    {
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

        LoaderService service = new LoaderService(new SqliteRepository(configuration.getDatabase()));
        CsvLoader loader = new CsvLoader(service, configuration.getCsvInput());
        loader.processFile(CsvFileType.CONTAINER);
        loader.processFile(CsvFileType.PRODUCER);
        loader.processFile(CsvFileType.MARKET);
        loader.processFile(CsvFileType.ORIGIN);
        loader.processFile(CsvFileType.PRODUCT);
        loader.processFile(CsvFileType.ORDER);
        loader.processFile(CsvFileType.ORDERITEMS);

        System.out.println();
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
        }
    }

    private File getCsvFile(String filename)
    {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(filename);
        File file = null;
        try
        {
            file = new File(resource.getFile());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return file;
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
                    Producer producer = getProducerByName(fields[6]);
                    ProductOrigin origin = getProductOriginByName(fields[7]);
                    ProductContainer container = getProductContainerByName(fields[7]);
                    if(producer!=null)
                    {
                        Product product = new Product(fields[0]);
                        product.setName(fields[1]);
                        product.setDescription(fields[2]);
                        product.setQuantity(Integer.parseInt(fields[3]));
                        product.setWeight(Double.parseDouble(fields[4]));
                        product.setPrice(Double.parseDouble(fields[5]));
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
                    boolean exists = getExistProductContainer(fields[0]);
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
                    long timestamp = formatter.parse(fields[1]).getTime();

                    Order order = new Order(fields[0],timestamp);
                    boolean exists = getExistOrder(fields[0]);
                    if(!exists)
                    {
                        addOrder(order);
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
                try
                {
                    Order order = getOrderByNumber(orderNumber);
                    Product product = getProductByNumber(productNumber);

                    boolean exists = getExistOrderItem(order.getId(),product.getId());

                    if(product !=null && product.getProducer().getNoOrdering()==0 && order != null)
                    {
                        if (!exists)
                        {
                            addOrderItem(order.getId(), product.getId());
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
                            logger.error("referenced order unavailable. number: [{}]" + orderNumber);
                        }
                        if(product ==null )
                        {
                            logger.error("referenced product unavailable. number: [{}]", productNumber);
                        }
                        else if(product.getProducer().getNoOrdering()!=0)
                        {
                            logger.error("referenced product is unavailable for ordering. name: [{}]", product.getName());
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
    public void addOrder(Order order) { service.addOrder(order); }

    @Override
    public Order getOrderByNumber(String number) throws Exception { return service.getOrderByNumber(number); }

    @Override
    public boolean getExistOrder(String number) throws Exception { return service.getExistOrder(number); }

    @Override
    public void addOrderItem(long orderId, long productId) { service.addOrderItem(orderId,productId); }

    @Override
    public boolean getExistOrderItem(long orderId, long productId) throws Exception { return service.getExistOrderItem(orderId,productId);
    }

}
