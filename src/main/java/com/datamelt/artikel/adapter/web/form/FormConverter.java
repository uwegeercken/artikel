package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.model.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormConverter
{
    private static final DecimalFormat formatPrice = new DecimalFormat("#.00",new DecimalFormatSymbols(Locale.ENGLISH));

    public static Form convertToForm(Producer producer)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(producer.getId()));
        form.put(FormField.NAME, producer.getName());
        form.put(FormField.EMAIL, producer.getEmailAddress());
        form.put(FormField.NO_ORDERING, String.valueOf(producer.getNoOrdering()));
        return form;
    }

    public static Form convertToForm(User user)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(user.getId()));
        form.put(FormField.NAME, String.valueOf(user.getName()));
        form.put(FormField.FULL_NAME, String.valueOf(user.getFullName()));
        form.put(FormField.USER_ROLE, String.valueOf(user.getRole()));
        return form;
    }

    public static Form convertToForm(ProductContainer container)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(container.getId()));
        form.put(FormField.NAME, container.getName());
        return form;
    }

    public static Form convertToForm(Market market)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(market.getId()));
        form.put(FormField.MARKET_TYPE, String.valueOf(market.getType()));
        form.put(FormField.NAME, market.getName());
        return form;
    }

    public static Form convertToForm(ProductOrigin origin)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(origin.getId()));
        form.put(FormField.NAME, origin.getName());
        return form;
    }

    public static Form convertToForm(Product product, NumberFormatter numberFormatter)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(product.getId()));
        form.put(FormField.PRODUCT_NAME, product.getName());
        form.put(FormField.NUMBER, product.getNumber());
        form.put(FormField.TITLE, product.getTitle());
        form.put(FormField.SUBTITLE, product.getSubtitle());
        form.put(FormField.QUANTITY, String.valueOf(product.getQuantity()));
        form.put(FormField.WEIGHT,numberFormatter.convertToLocale(product.getWeight()));
        form.put(FormField.PRICE, numberFormatter.convertToLocale((product.getPrice())));
        form.put(FormField.PRODUCER_ID,String.valueOf(product.getProducer().getId()));
        form.put(FormField.CONTAINER_ID,String.valueOf(product.getContainer().getId()));
        form.put(FormField.ORIGIN_ID,String.valueOf(product.getOrigin().getId()));
        form.put(FormField.UNAVAILABLE,String.valueOf(product.getUnavailable()));
        return form;
    }

    public static Product convertToProduct(Form form, NumberFormatter numberFormatter)
    {
        Product product = new Product(form.get(FormField.NUMBER));
        product.setName(form.get(FormField.PRODUCT_NAME));
        product.setTitle(form.get(FormField.TITLE));
        product.setSubtitle(form.get(FormField.SUBTITLE));
        product.setQuantity(Integer.parseInt(form.get(FormField.QUANTITY)));
        product.setWeight(numberFormatter.convertToDouble(form.get(FormField.WEIGHT)));
        product.setPrice(numberFormatter.convertToDouble(form.get(FormField.PRICE)));
        product.setUnavailable(Integer.parseInt(form.get(FormField.UNAVAILABLE)));
        return product;
    }
}
