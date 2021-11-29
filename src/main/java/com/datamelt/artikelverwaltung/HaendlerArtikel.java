package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;
import com.datamelt.artikelverwaltung.enums.MengenTyp;

import java.util.Date;

public class HaendlerArtikel extends Artikel
{
    public HaendlerArtikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung,  ArtikelMenge menge, ArtikelGueltigkeit gueltigkeit)
    {
        super(basisArtikel, bezeichnung, beschreibung, menge, gueltigkeit);
        erstellen();
    }

    @Override
    protected void erstellen()
    {

    }
}
