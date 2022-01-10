package com.datamelt.artikelverwaltung;

import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

public class BasisArtikelListe
{
    List<BasisArtikel> artikelListe;

    public BasisArtikelListe()
    {
        ladeBasisArtikel();
    }

    public BasisArtikel searchByDescription(String bezeichnung)
    {
        for(BasisArtikel artikel : artikelListe)
        {
            if(artikel.getBezeichnung().equals(bezeichnung))
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
                handle.createQuery("select * from basisartikel")
                        .mapToBean(BasisArtikel.class)
                        .list());


    }

}
