package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;
import com.datamelt.artikelverwaltung.enums.MengenTyp;

import java.util.Calendar;
import java.util.Date;

public class ArtikelTest01
{
    public static void main(String[] args)
    {




        System.out.println();




    }

    private static void generiereArtikel()
    {
        Date gueltigVon = new Calendar.Builder()
                .set(Calendar.YEAR,2021)
                .set(Calendar.MONTH,Calendar.JANUARY)
                .set(Calendar.DAY_OF_MONTH,1)
                .build()
                .getTime();

        Date gueltigBis = new Calendar.Builder()
                .set(Calendar.YEAR,2099)
                .set(Calendar.MONTH,Calendar.DECEMBER)
                .set(Calendar.DAY_OF_MONTH,31)
                .set(Calendar.HOUR_OF_DAY,23)
                .set(Calendar.MINUTE,59)
                .set(Calendar.SECOND,59)
                .build()
                .getTime();

        BasisArtikel basisartikel1 = new BasisArtikel(ArtikelUrsprung.BORGMEIER,"Ei Größe M",null);
        BasisArtikel basisartikel2 = new BasisArtikel(ArtikelUrsprung.MOGK,"Glas Sauerfleisch 250gr",null);
        BasisArtikel basisartikel3 = new BasisArtikel(ArtikelUrsprung.MOGK,"Becher Fleischsalat 250gr",null);

        Artikel artikel1 = ArtikelFabrik.erstelleArtikel(basisartikel1, "1 Karton Eier Größe M",null,100, MengenTyp.KARTON);
        Artikel artikel2 = ArtikelFabrik.erstelleArtikel(basisartikel2, "1 Glas Sauerfleisch 250gr",null,1, MengenTyp.EINZELMENGE);



    }
}
