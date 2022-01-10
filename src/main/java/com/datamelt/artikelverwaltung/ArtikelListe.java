package com.datamelt.artikelverwaltung;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ArtikelListe
{
    List<Artikel> artikelListe;

    public ArtikelListe()
    {
        ladeBasisArtikel();
    }

    public Artikel searchByDescription(String bezeichnung)
    {
        for(Artikel artikel : artikelListe)
        {
            if(artikel.getBezeichnung().equals(bezeichnung))
            {
                return artikel;
            }
        }
        return null;
    }

    public Artikel searchByNumber(long number)
    {
        for(Artikel artikel : artikelListe)
        {
            if(artikel.getNummer()== number)
            {
                return artikel;
            }
        }
        return null;
    }

    private void ladeBasisArtikel()
    {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite:/home/uwe/development/mogk.db");

        this.artikelListe = jdbi.withHandle(handle ->
                handle.createQuery("select basisartikel.*," +
                                " herkunft.name as herkunft," +
                                " ursprung.name as ursprung," +
                                " behälter.name as behälter" +
                                " from basisartikel, herkunft, ursprung, behälter " +
                                " where basisartikel.herkunft_id=herkunft.id" +
                                " and basisartikel.ursprung_id=ursprung.id" +
                                " and basisartikel.behälter_id=behälter.id"
                        )
                        .mapToBean(Artikel.class)
                        .list());


    }

}
