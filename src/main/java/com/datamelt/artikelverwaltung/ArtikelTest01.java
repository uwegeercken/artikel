package com.datamelt.artikelverwaltung;

import com.datamelt.utilities.DateUtility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArtikelTest01
{
    public static void main(String[] args) throws Exception
    {
        List<Artikel> artikelListe = ArtikelListe.getAll(true);

        Artikel artikel1 = artikelListe.get(0);

        Date gültigVon = artikel1.getGültigVonAsDate();

        System.out.println();
    }



    private static void test()
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

        ArtikelGueltigkeit gueltigkeit = new ArtikelGueltigkeit(gueltigVon, gueltigBis);
    }
}
