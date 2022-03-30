package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.adapter.web.IndexController;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.app.web.util.Filters;
import com.datamelt.artikel.model.config.MainConfiguration;
import com.datamelt.artikel.service.WebService;
import com.datamelt.artikel.app.web.util.Path;
import spark.Spark;

import static spark.Spark.before;
import static spark.Spark.get;

public class WebApplication
{
    public static final String APPLCATION_VERSION = "v0.1";
    public static final String APPLCATION_LAST_UPDATE = "30.03.2022";

    public static void main(String[] args)
    {
        MainConfiguration configuration = new ConfigurationLoader().getMainConfiguration();

        WebService service = new WebService(new SqliteRepository(configuration.getDatabase()));
        IndexController indexController = new IndexController(service);

        Spark.staticFiles.location("web");

        before("*", Filters.addTrailingSlashes);

        get(Path.Web.ABOUT, indexController.serveAboutPage);





        get("*", indexController.serveNotFoundPage);

        /**
        get(Path.Web.ABOUT, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("version", "v1.0");
            model.put("lastupdate", "20-03.2022");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, Path.Template.ABOUT)
            );
        });
         */
    }
}
