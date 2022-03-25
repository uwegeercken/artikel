package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.config.CsvInput;
import com.datamelt.artikel.port.FileInterface;
import com.datamelt.artikel.service.LoaderService;
import com.datamelt.artikel.util.Constants;
import com.datamelt.artikel.util.CsvFileType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class CsvLoader implements FileInterface
{
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
                    if(producer!=null)
                    {
                        Product product = new Product.ProductBuilder(fields[0], fields[1], producer)
                                .description(fields[2])
                                .quantity(Integer.parseInt(fields[3]))
                                .weight(Integer.parseInt(fields[4]))
                                .price(Double.parseDouble(fields[5]))
                                .build();

                        boolean exists = getExistProduct(fields[0]);
                        if(!exists)
                        {
                            addProduct(product);
                            counter++;
                        }
                        else
                        {
                            System.out.println("already existing - producer: " + fields[0]);
                        }
                    }
                    else
                    {
                        System.out.println("referenced producer unavailable. id: " + fields[6]);
                    }
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            System.out.println("added products: " + counter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void processProducerFile(File inputFile)
    {
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
                        System.out.println("already existing - producer: " + fields[0]);
                    }

                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            System.out.println("added producers: " + counter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void processMarketFile(File inputFile)
    {
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
                    boolean exists = getExistMarket(fields[0]);
                    if(!exists)
                    {
                        addMarket(market);
                        counter++;
                    }
                    else
                    {
                        System.out.println("already existing - market: " + fields[0]);
                    }
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            System.out.println("added markets: " + counter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void processOrderFile(File inputFile)
    {
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
                        System.out.println("already existing - order: " + fields[0]);
                    }
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            System.out.println("added orders: " + counter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void processOrderItemFile(File inputFile)
    {
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
                long productId = Long.parseLong(fields[1]);
                try
                {
                    Order order = getOrderByNumber(orderNumber);
                    Product product = getProductById(productId);

                    boolean exists = getExistOrderItem(order.getId(),product.getId());

                    if(product !=null && product.getProducer().getNoOrdering()==0 && order != null)
                    {
                        if (!exists)
                        {
                            addOrderItem(order.getId(), product.getId());
                            counter++;
                        } else
                        {
                            System.out.println("already existing - order item: " + order.getNumber() + "/" + product.getName());
                        }
                    }
                    else
                    {
                        if(order == null)
                        {
                            System.out.println("referenced order unavailable. number: " + orderNumber);
                        }
                        if(product ==null )
                        {
                            System.out.println("referenced product unavailable. id: " + productId);
                        }
                        if(product.getProducer().getNoOrdering()!=0)
                        {
                            System.out.println("referenced product is unavailable for ordering. id: " + productId);
                        }
                    }

                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            System.out.println("added products: " + counter);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product) { service.addProduct(product); }

    @Override
    public Product getProductById(long id) throws Exception { return service.getProductById(id); }

    @Override
    public boolean getExistProduct(String number) throws Exception { return service.getExistProduct(number); }

    @Override
    public void addProducer(Producer producer) { service.addProducer(producer); }

    @Override
    public boolean getExistProducer(String name) throws Exception { return service.getExistProducer(name); }

    @Override
    public Producer getProducerByName(String name) throws Exception { return service.getProducerByName(name); }

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
