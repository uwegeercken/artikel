package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;
import com.datamelt.artikelverwaltung.enums.MengenTyp;

public class HaendlerArtikel extends Artikel
{
    public HaendlerArtikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung, double menge, MengenTyp mengenTyp)
    {
        super(basisArtikel, bezeichnung, beschreibung, menge, mengenTyp);
        erstellen();
    }

    @Override
    protected void erstellen()
    {

    }
}
