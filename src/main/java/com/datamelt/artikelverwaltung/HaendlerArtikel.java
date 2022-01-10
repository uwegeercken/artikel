package com.datamelt.artikelverwaltung;

public class HaendlerArtikel extends BestellArtikel
{
    public HaendlerArtikel(Artikel basisArtikel, String bezeichnung, String beschreibung, ArtikelGueltigkeit gueltigkeit)
    {
        super(basisArtikel, bezeichnung, beschreibung, gueltigkeit);
        erstellen();
    }

    @Override
    protected void erstellen()
    {

    }
}
