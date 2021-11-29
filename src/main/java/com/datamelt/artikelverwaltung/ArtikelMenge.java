package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.MengenTyp;

public class ArtikelMenge
{
    double einzelStueckzahl;
    MengenTyp mengenTyp;

    public ArtikelMenge(double einzelStueckzahl, MengenTyp mengenTyp)
    {
        this.einzelStueckzahl = einzelStueckzahl;
        this.mengenTyp = mengenTyp;
    }

    public double getEinzelStueckzahl()
    {
        return einzelStueckzahl;
    }

    public MengenTyp getMengenTyp()
    {
        return mengenTyp;
    }
}
