package com.datamelt.artikel.util;

import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Endpoints;
import com.datamelt.artikel.app.web.util.Timestamp;

public class Constants
{
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_ONLY_FORMAT = "yyyy-MM-dd";
    public static final String GERMAN_DATE_FORMAT = "dd.MM.yyyy HH:mm";
    public static final String GERMAN_DATE_FORMAT_WITH_WEEKDAY = "EEE dd.MM.yyyy HH:mm";
    public static final String GERMAN_DATE_FORMAT_WITH_WEEKDAY_NO_TIME = "EEE dd.MM.yyyy";
    public static final String GERMAN_DATE_ONLY_FORMAT = "dd.MM.yyyy";

    public static final String FORM_SUBMIT = "submit";

    public static final String CONTENT_DISPOSITION_KEY = "Content-Disposition";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/download";

    public static final String FILE_CONTENT_TYPE_PDF = "application/pdf;charset=UTF-8";

    public static final String LABELS_CSV_FILENAME = "labels.csv";

    public static final String CONTENT_DISPOSITION_VALUE = "attachment; filename=";
    public static final String LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 = "etiketten";
    public static final String LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2 = ".pdf";

    public static final String MODEL_FORM_KEY = "form";
    public static final String MODEL_FIELDS_KEY = "fields";
    public static final String MODEL_RESULT_KEY = "result";
    public static final String MODEL_CONTAINERS_KEY = "containers";
    public static final String MODEL_CONTAINER_KEY = "container";
    public static final String MODEL_PRODUCERS_KEY = "producers";
    public static final String MODEL_PRODUCER_KEY = "producer";
    public static final String MODEL_PRODUCTS_KEY = "products";
    public static final String MODEL_PRODUCT_KEY = "product";
    public static final String MODEL_ORIGINS_KEY = "origins";
    public static final String MODEL_MARKETS_KEY = "markets";
    public static final String MODEL_MARKET_KEY = "market";

    public static final String MODEL_ORDERS_KEY = "orders";
    public static final String MODEL_ORDER_KEY = "order";
    public static final String MODEL_USERS_KEY = "users";
    public static final String MODEL_USER_KEY = "user";
    public static final String MODEL_ORDERID_KEY = "orderid";
    public static final String MODEL_TOTAL_NUMBER_OF_EMAIL_ADDRESSES ="numberofemails";
    public static final String MODEL_WEBPATH_KEY = "WebPath";
    public static final String MODEL_TIMESTAMP_KEY = "Timestamp";
    public static final String MODEL_NUMBER_FORMATTER_KEY = "numberFormatter";
    public static final String MODEL_MESSAGES_KEY = "messages";
    public static final String MODEL_TOKEN_PAYLOAD_KEY = "tokenpayload";

    public static final String MODEL_RECENTLY_UNCHANGED_PRODUCTS_NUMBER_OF_DAYS_MIN_KEY = "recentlyUnchangedProductsNumberOfDaysMin";
    public static final String MODEL_RECENTLY_UNCHANGED_PRODUCTS_NUMBER_OF_DAYS_MAX_KEY = "recentlyUnchangedProductsNumberOfDaysMax";
    public static final String MODEL_SHORTTERM_UNCHANGED_PRODUCTS_NUMBER_OF_DAYS_MIN_KEY = "shorttermUnchangedProductsNumberOfDaysMin";
    public static final String MODEL_SHORTTERM_UNCHANGED_PRODUCTS_NUMBER_OF_DAYS_MAX_KEY = "shorttermUnchangedProductsNumberOfDaysMax";
    public static final String MODEL_LONGTERM_UNCHANGED_PRODUCTS_NUMBER_OF_DAYS_MIN_KEY = "longtermUnchangedProductsNumberOfDaysMin";
    public static final String MODEL_UNCHANGED_PRODUCTS_FILTER_KEY = "filter";

    public static final String MODEL_PRODUCTORDERITEMS_KEY = "productorderitems";
    public static final String MODEL_SHOPLABELSONLY_KEY = "shoplabelsonly";
    public static final String MODEL_VERSION_KEY = "version";
    public static final String MODEL_LASTUPDATE_KEY = "lastupdate";
    public static final String MODEL_DOCUMENTS_FOLDER = "documentsfolder";
    public static final String MODEL_LABELS_PDF_OUTPUT_FOLDER = "labelspdfoutputfolder";
    public static final String MODEL_ORDERS_PDF_OUTPUT_FOLDER = "orderspdfoutputfolder";

    public static final String ORDER_FILENAME_PREFIX = "order_";
    public static final String ASCIIDOC_FILENAME_EXTENSION = ".adoc";
    public static final String PDF_FILENAME_EXTENSION = ".pdf";

    public static final String USERTOKEN_KEY = "usertoken";
    public static final String REGO_FILENAME = "users-policy.rego";
    public static final String USERTOKEN_ISSUER = "article application";
    public static final String ORDER_DOCUMENT_GENERIC_TEAMPLATE = "order_[producer_id].adoc";

    public static final String SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN = "productsChangedNumberOfDaysMin";
    public static final String SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX = "productsChangedNumberOfDaysMax";
    public static final String SESSION_ATTRIBUTE_ORDER_COLLECTION = "ordercollection";
    public static final String SESSION_ATTRIBUTE_PRODUCERS = "producers";
    public static final String SESSION_ATTRIBUTE_FILTER = "filter";
}
