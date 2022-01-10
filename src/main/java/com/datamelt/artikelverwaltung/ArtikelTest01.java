package com.datamelt.artikelverwaltung;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArtikelTest01
{
    public static void main(String[] args) throws Exception
    {
        List<Artikel> artikelListe = ArtikelListe.getAll(true);

        Bestellung bestellung = new Bestellung(new Date());

        Artikel bestellArtikel1 = ArtikelListe.getArtikelByNumber(artikelListe, 17045032);
        if(bestellArtikel1!=null)
        {
            bestellung.put(bestellArtikel1, 10);
            bestellung.put(bestellArtikel1, 10);
            bestellung.increaseBy(bestellArtikel1, 6);

            bestellung.put(bestellArtikel1, 999);
        }

        System.out.println();
    }

}
