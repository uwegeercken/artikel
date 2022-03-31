package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Message;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.service.WebService;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Product> getAllProducts() throws Exception
    {
        return service.getAllProducts();
    }
}
