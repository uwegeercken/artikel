package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.MengenTyp;

import java.util.Date;

public abstract class Artikel
{
    private String bezeichnung;
    private String beschreibung;
    private BasisArtikel basisArtikel;
    private double einzelStueckzahl;
    private MengenTyp mengenTyp;
    private Date gueltigVon;
    private Date gueltigBis;

    public Artikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung, ArtikelMenge menge, ArtikelGueltigkeit gueltigkeit)
    {
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.basisArtikel = basisArtikel;
        this.einzelStueckzahl = einzelStueckzahl;
        this.mengenTyp = mengenTyp;
        this.gueltigVon = gueltigVon;
        this.gueltigBis = gueltigBis;

    }

    protected void erstellen()
    {

    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public String getBeschreibung()
    {
        return beschreibung;
    }

    public BasisArtikel getBasisArtikel()
    {
        return basisArtikel;
    }

    public double getEinzelStueckzahl()
    {
        return einzelStueckzahl;
    }

    public MengenTyp getMengenTyp()
    {
        return mengenTyp;
    }

    public Date getGueltigVon()
    {
        return gueltigVon;
    }

    public Date getGueltigBis()
    {
        return gueltigBis;
    }
}
