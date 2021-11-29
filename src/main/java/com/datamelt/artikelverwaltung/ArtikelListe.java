package com.datamelt.artikelverwaltung;

import java.util.ArrayList;
import java.util.List;

public class ArtikelListe
{
    List<Artikel> artikelListe = new ArrayList<>();

    public void addArtikel(Artikel artikel)
    {
        artikelListe.add(artikel);
    }

    public void removeArtikel(Artikel artikel)
    {
        artikelListe.remove(artikel);
    }

    public Artikel searchArtikel(String bezeichnung)
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

    public long count()
    {
        return artikelListe.size();
    }

}
