package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Form;
import com.datamelt.artikel.app.web.util.Message;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.service.WebService;
import org.eclipse.jetty.http.HttpStatus;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductController implements ProductApiInterface
{
    private WebService service;

    public ProductController(WebService service)
    {
        this.service = service;
    }

    public Route serveAllProductsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pagetitle", "Produktliste");
        model.put("products", getAllProducts());
        return ViewUtility.render(request,model,Path.Template.PRODUCTS);

    };

    public Route serveProductPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pagetitle", "Produkt");
        model.put("fields",ProductFormField.class);
        model.put("form", ProductConverter.convertProduct(getProductById(Long.parseLong(request.params(":id")))));
        model.put("producers", getAllProducers());
        model.put("containers", getAllProductContainers());
        model.put("origins", getAllProductOrigins());
        return ViewUtility.render(request,model,Path.Template.PRODUCT);

    };

    public Route serveProductAddPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pagetitle", "Produkt");
        model.put("producers", getAllProducers());
        model.put("containers", getAllProductContainers());
        model.put("origins", getAllProductOrigins());
        return ViewUtility.render(request,model,Path.Template.PRODUCT);

    };

    public Route serveUpdateProductPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        String cancelled = request.queryParams("submit");
        if(!cancelled.equals("abbrechen"))
        {
            Map<String, String> queryParamsMap = new HashMap<>();
            ProductForm form = new ProductForm();
            for (ProductFormField field : ProductFormField.values())
            {
                String value = request.queryParams(field.fieldName());
                form.put(field,value );
                //queryParamsMap.put(fieldName, request.queryParams(fieldName));
            }
            model.put("form", form);
            model.put("pagetitle", "Produkt ändern");
            Product product=null;
            if (Long.parseLong(request.params(":id")) > 0)
            {
                try
                {
                    product = updateProduct(Long.parseLong(request.params(":id")), queryParamsMap);
                }
                catch(Exception ex)
                {
                    model.put("infomessage", ex.getMessage());
                }
            }
            else
            {
                try
                {
                    product = addProduct(queryParamsMap);
                }
                catch(Exception ex)
                {
                    model.put("infomessage", ex.getMessage());
                }
            }
            model.put("producers", getAllProducers());
            model.put("containers", getAllProductContainers());
            model.put("origins", getAllProductOrigins());
            return ViewUtility.render(request,model,Path.Template.PRODUCT);
        }
        else
        {
            model.put("pagetitle", "Produktliste");
            model.put("products", getAllProducts());
            return ViewUtility.render(request,model,Path.Template.PRODUCTS);
        }


    };

    @Override
    public List<Product> getAllProducts() throws Exception
    {
        return service.getAllProducts();
    }

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return service.getAllProducers();
    }

    @Override
    public List<ProductContainer> getAllProductContainers() throws Exception
    {
        return service.getAllProductContainers();
    }

    @Override
    public List<ProductOrigin> getAllProductOrigins() throws Exception
    {
        return service.getAllProductOrigins();
    }

    @Override
    public Product getProductById(long id) throws Exception
    {
        return service.getProductById(id);
    }

    @Override
    public Product updateProduct(long id, Map<String,String> queryParamsMap) throws Exception
    {
        return service.updateProduct(id, queryParamsMap);
    }

    @Override
    public Product addProduct(Map<String,String> queryParamsMap) throws Exception
    {
        return service.addProduct(queryParamsMap);
    }
}
