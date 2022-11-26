package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.port.IndexApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.util.Constants;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IndexController implements IndexApiInterface
{
    private WebServiceInterface service;
    private MainConfiguration configuration = null;

    public IndexController(WebServiceInterface service, MainConfiguration configuration)
    {
        this.configuration = configuration;
        this.service = service;
    }

    public Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("totalproductscount", getAllProductsCount());
        return ViewUtility.render(request, model, Path.Template.INDEX);
    };

    public Route serveAboutPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_VERSION_KEY, WebApplication.APPLCATION_VERSION);
        model.put(Constants.MODEL_LASTUPDATE_KEY, WebApplication.APPLCATION_LAST_UPDATE);
        return ViewUtility.render(request, model, Path.Template.ABOUT);

    };

    public Route serveDocumentsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String folder = configuration.getSparkJava().getDocumentsFolder();
        ArrayList<String> list = new ArrayList<>();
        if (folder != null)
        {
            File documentsFolder = new File(folder);
            for(File file : documentsFolder.listFiles())
            {
                if(file.isFile() && file.canRead())
                {
                    list.add(file.getName());
                }
            }
        }
        Collections.sort(list);
        model.put("documents", list);
        return ViewUtility.render(request, model, Path.Template.DOCUMENTS);
    };

    public Route serveDocument = (Request request, Response response) -> {
        String path = request.pathInfo();
        int lastSlash = path.lastIndexOf("/");
        String filename = path.substring(lastSlash+1);
        String documentPath = configuration.getSparkJava().getDocumentsFolder() + "/" + filename;
        InputStream inputStream = new FileInputStream(new File(documentPath));
        if (inputStream != null)
        {
            response.status(200);
            byte[] buf = new byte[1024];
            OutputStream os = response.raw().getOutputStream();
            OutputStreamWriter outWriter = new OutputStreamWriter(os);
            int count = 0;
            while ((count = inputStream.read(buf)) >= 0)
            {
                os.write(buf, 0, count);
            }
            inputStream.close();
            outWriter.close();
            return "";
        } else
        {
            return null;
        }
    };

    public Route serveNotFoundPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        response.status(HttpStatus.NOT_FOUND_404);
        return ViewUtility.render(request,model,Path.Template.NOTFOUND);
    };

    public Route serveNotAuthorizedPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
        return ViewUtility.render(request,model,Path.Template.NOTAUTHORIZED);
    };

    @Override
    public long getAllProductsCount() throws Exception
    {
        return service.getAllProductsCount();
    }
}
