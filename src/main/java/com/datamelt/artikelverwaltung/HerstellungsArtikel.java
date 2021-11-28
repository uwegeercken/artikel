package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelArt;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;

import java.util.Date;

public class HerstellungsArtikel extends Artikel
{
    public HerstellungsArtikel(ArtikelUrsprung ursprung, String bezeichnung, Date gueltigVon, Date gueltigBis)
    {
        super(ArtikelArt.HAENDLERARTIKEL, ursprung, bezeichnung, gueltigVon, gueltigBis);
        erstellen();
    }

    @Override
    protected void erstellen()
    {

    }
}
