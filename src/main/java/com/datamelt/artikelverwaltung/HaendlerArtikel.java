package com.datamelt.artikelverwaltung;

public class HaendlerArtikel extends Artikel
{
    public HaendlerArtikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung, ArtikelGueltigkeit gueltigkeit)
    {
        super(basisArtikel, bezeichnung, beschreibung, gueltigkeit);
        erstellen();
    }

    @Override
    protected void erstellen()
    {

    }
}
