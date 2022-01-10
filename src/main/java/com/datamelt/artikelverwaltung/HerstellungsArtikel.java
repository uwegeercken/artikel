package com.datamelt.artikelverwaltung;

public class HerstellungsArtikel extends Artikel
{
    public HerstellungsArtikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung, ArtikelGueltigkeit gueltigkeit)
    {
        super(basisArtikel, bezeichnung, beschreibung, gueltigkeit);
        erstellen();
    }

    @Override
    protected void erstellen()
    {

    }
}
