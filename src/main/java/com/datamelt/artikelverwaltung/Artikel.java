package com.datamelt.artikelverwaltung;

import java.util.Date;

public abstract class Artikel
{
    private String bezeichnung;
    private String beschreibung;
    private BasisArtikel basisArtikel;
    private double einzelStueckzahl;
    private Date gueltigVon;
    private Date gueltigBis;

    public Artikel(BasisArtikel basisArtikel, String bezeichnung, String beschreibung, ArtikelGueltigkeit gueltigkeit)
    {
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.basisArtikel = basisArtikel;
        this.einzelStueckzahl = einzelStueckzahl;
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

    public Date getGueltigVon()
    {
        return gueltigVon;
    }

    public Date getGueltigBis()
    {
        return gueltigBis;
    }
}
