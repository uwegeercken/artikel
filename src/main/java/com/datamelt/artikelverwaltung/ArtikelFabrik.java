package com.datamelt.artikelverwaltung;

public class ArtikelFabrik
{
    public static Artikel erstelleArtikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung,  ArtikelGueltigkeit gueltigkeit)
    {
        Artikel artikel = null;
        switch (basisArtikel.getUrsprung())
        {
            case BORGMEIER:
            case KUNSCHKE:
                artikel = new HaendlerArtikel(basisArtikel, bezeichnung, beschreibung, gueltigkeit);
                break;
            case MOGK:
                artikel = new HerstellungsArtikel(basisArtikel, bezeichnung, beschreibung, gueltigkeit);
                break;
            default:
                break;
        }
        return artikel;
    }
}
