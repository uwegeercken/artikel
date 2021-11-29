package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.MengenTyp;

public class ArtikelFabrik
{
    public static Artikel erstelleArtikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung, double einzelStueckzahl, MengenTyp mengenTyp)
    {
        Artikel artikel = null;
        switch (basisArtikel.getUrsprung())
        {
            case BORGMEIER:
                artikel = new HaendlerArtikel(basisArtikel, bezeichnung, beschreibung, einzelStueckzahl, mengenTyp);
                break;
            case KUNSCHKE:
                artikel = new HaendlerArtikel(basisArtikel, bezeichnung, beschreibung, einzelStueckzahl, mengenTyp);
                break;
            case MOGK:
                artikel = new HerstellungsArtikel(basisArtikel, bezeichnung, beschreibung, einzelStueckzahl, mengenTyp);
                break;
            default:
                break;
        }
        return artikel;
    }
}
