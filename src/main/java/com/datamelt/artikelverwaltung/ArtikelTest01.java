package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelArt;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;

import java.util.Calendar;
import java.util.Date;

public class ArtikelTest01
{
    public static void main(String[] args)
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

        Artikel artikel1 = ArtikelFabrik.erstelleArtikel(ArtikelUrsprung.BORGMEIER, "Ei Größe M",gueltigVon, gueltigBis);
        artikel1.setBeschreibung("Ein Ei");

        Artikel artikel2 = ArtikelFabrik.erstelleArtikel(ArtikelUrsprung.MOGK,"Leberwurst",gueltigVon, gueltigBis);

        System.out.println();




    }
}
