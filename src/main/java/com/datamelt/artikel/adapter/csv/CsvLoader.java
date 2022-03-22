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
                        addProduct(product);
                    }
                    else
                    {
                        throw new Exception("producer not found: " + fields[6]);
                    }
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
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
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    Producer producer = new Producer(fields[0]);
                    addProducer(producer);
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
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
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    Market market = new Market(fields[0]);
                    addMarket(market);
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
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
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    long timestamp = formatter.parse(fields[1]).getTime();

                    Order order = new Order(fields[0],timestamp);
                    addOrder(order);
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
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
            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(",");
                try
                {
                    addOrderItem(Long.parseLong(fields[0]), Long.parseLong(fields[1]));
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product)
    {
        service.addProduct(product);
    }

    @Override
    public void addProducer(Producer producer) { service.addProducer(producer); }

    @Override
    public Producer getProducerByName(String name) throws Exception { return service.getProducerByName(name); }

    @Override
    public void addMarket(Market market)
    {
        service.addMarket(market);
    }

    @Override
    public void addOrder(Order order)
    {
        service.addOrder(order);
    }

    @Override
    public void addOrderItem(long orderId, long productId) { service.addOrderItem(orderId,productId); }
}
