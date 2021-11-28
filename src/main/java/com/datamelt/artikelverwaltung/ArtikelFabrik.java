package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelArt;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;

import java.util.Date;

public class ArtikelFabrik
{
    public static Artikel erstelleArtikel(ArtikelArt art, ArtikelUrsprung ursprung, String bezeichnung, Date gueltigVon, Date gueltigBis)
    {
        Artikel artikel = null;
        switch (art)
        {
            case HAENDLERARTIKEL:
                artikel = new HaendlerArtikel(ursprung, bezeichnung, gueltigVon, gueltigBis);
                break;

            case HERSTELLUNGSARTIKEL:
                artikel = new HerstellungsArtikel(ursprung, bezeichnung, gueltigVon, gueltigBis);
                break;
            default:
                break;
        }
        return artikel;
    }
}
