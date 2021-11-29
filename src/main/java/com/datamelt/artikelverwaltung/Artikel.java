package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.MengenTyp;

import java.util.ArrayList;
import java.util.List;

public abstract class Artikel
{
    private String bezeichnung;
    private String beschreibung;
    private BasisArtikel basisArtikel;
    private double einzelStueckzahl;
    private MengenTyp mengenTyp;

    public Artikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung, double einzelStueckzahl, MengenTyp mengenTyp)
    {
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.basisArtikel = basisArtikel;
        this.einzelStueckzahl = einzelStueckzahl;
        this.mengenTyp = mengenTyp;
    }

    protected void erstellen()
    {

    }

}
