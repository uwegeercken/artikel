package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelArt;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;

import java.util.Date;

public class ArtikelFabrik
{
    public static Artikel erstelleArtikel(ArtikelUrsprung ursprung, String bezeichnung, Date gueltigVon, Date gueltigBis)
    {
        Artikel artikel = null;
        switch (ursprung)
        {
            case BORGMEIER:
                artikel = new HaendlerArtikel(ursprung, bezeichnung, gueltigVon, gueltigBis);
                break;
            case KUNSCHKE:
                artikel = new HaendlerArtikel(ursprung, bezeichnung, gueltigVon, gueltigBis);
                break;
            case MOGK:
                artikel = new HerstellungsArtikel(ursprung, bezeichnung, gueltigVon, gueltigBis);
                break;
            default:
                break;
        }
        return artikel;
    }
}
