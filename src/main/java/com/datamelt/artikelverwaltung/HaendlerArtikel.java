package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelArt;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;

import java.util.Date;

public class HaendlerArtikel extends Artikel
{
    public HaendlerArtikel(ArtikelUrsprung ursprung, String bezeichnung, Date gueltigVon, Date gueltigBis)
    {
        super(ursprung, bezeichnung, gueltigVon, gueltigBis);
        erstellen();
    }

    @Override
    protected void erstellen()
    {

    }
}
