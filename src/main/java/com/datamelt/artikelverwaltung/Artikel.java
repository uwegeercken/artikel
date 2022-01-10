package com.datamelt.artikelverwaltung;

public class Artikel
{
    private long nummer;
    private String ursprung;
    private String behälter;
    private String herkunft;
    private String bezeichnung;
    private String beschreibung;
    private float gewicht;
    private float preis;

    public long getNummer()
    {
        return nummer;
    }

    public void setNummer(long nummer)
    {
        this.nummer = nummer;
    }

    public String getUrsprung()
    {
        return ursprung;
    }

    public void setUrsprung(String ursprung)
    {
        this.ursprung = ursprung;
    }

    public String getBehälter()
    {
        return behälter;
    }

    public void setBehälter(String behälter)
    {
        this.behälter = behälter;
    }

    public String getHerkunft()
    {
        return herkunft;
    }

    public void setHerkunft(String herkunft)
    {
        this.herkunft = herkunft;
    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung)
    {
        this.bezeichnung = bezeichnung;
    }

    public String getBeschreibung()
    {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung)
    {
        this.beschreibung = beschreibung;
    }

    public float getGewicht()
    {
        return gewicht;
    }

    public void setGewicht(float gewicht)
    {
        this.gewicht = gewicht;
    }

    public float getPreis()
    {
        return preis;
    }

    public void setPreis(float preis)
    {
        this.preis = preis;
    }
}
