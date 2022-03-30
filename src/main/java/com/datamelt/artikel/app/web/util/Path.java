package com.datamelt.artikel.app.web.util;

public class Path
{
    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        public static final String INDEX = "index";
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
        public static final String ABOUT = "about/:id/";
    }

    public static class Template {
        public static final String ABOUT = "web/templates/about.vm";
        public static final String NOTFOUND = "web/templates/notfound.vm";
    }
}
