package com.datamelt.artikel.model.highcharts;

import com.datamelt.artikel.util.CalendarUtility;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryCollection
{
    private ArrayList<Category> categories = new ArrayList<>();

    public void add(Category category)
    {
        categories.add(category);
    }

    public void add(String[] values)
    {
        for(int i=0;i<values.length;i++)
        {
            categories.add(new Category(values[i]));
        }
    }

    public void remove(Category category)
    {
        categories.remove(category);
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }

    public String getValues()
    {
        String[] values = new String[categories.size()];
        int counter=0;
        for(Category category : categories)
        {
            values[counter] = "'" + category.getValue() + "'";
            counter ++;
        }
        return Arrays.toString(values);
    }

}
