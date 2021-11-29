package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.MengenTyp;

import java.util.Date;

public class ArtikelFabrik
{
    public static Artikel erstelleArtikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung,  ArtikelMenge menge, ArtikelGueltigkeit gueltigkeit)
    {
        Artikel artikel = null;
        switch (basisArtikel.getUrsprung())
        {
            case BORGMEIER:
            case KUNSCHKE:
                artikel = new HaendlerArtikel(basisArtikel, bezeichnung, beschreibung, ArtikelMenge menge, ArtikelGueltigkeit gueltigkeit);
                break;
            case MOGK:
                artikel = new HerstellungsArtikel(basisArtikel, bezeichnung, beschreibung, ArtikelMenge menge, ArtikelGueltigkeit gueltigkeit);
                break;
            default:
                break;
        }
        return artikel;
    }
}
