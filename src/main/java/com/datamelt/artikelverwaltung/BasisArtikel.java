package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelBehälter;
import com.datamelt.artikelverwaltung.enums.ArtikelHerkunft;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BasisArtikel
{
    private ArtikelUrsprung ursprung;
    private ArtikelBehälter behälter;
    private ArtikelHerkunft herkunft;
    private String bezeichnung;
    private String beschreibung;
    private float gewicht;
    private float preis;

    public ArtikelHerkunft getHerkunft()
    {
        return herkunft;
    }

    public void setHerkunft(ArtikelHerkunft herkunft)
    {
        this.herkunft = herkunft;
    }

    public ArtikelBehälter getBehälter()
    {
        return behälter;
    }

    public void setBehälter(ArtikelBehälter behälter)
    {
        this.behälter = behälter;
    }

    public void setUrsprung(ArtikelUrsprung ursprung)
    {
        this.ursprung = ursprung;
    }

    public void setBezeichnung(String bezeichnung)
    {
        this.bezeichnung = bezeichnung;
    }

    public void setBeschreibung(String beschreibung)
    {
        this.beschreibung = beschreibung;
    }

    public void setGewicht(float gewicht)
    {
        this.gewicht = gewicht;
    }

    public ArtikelUrsprung getUrsprung()
    {
        return ursprung;
    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public String getBeschreibung() { return beschreibung; }

    public float getGewicht() { return gewicht; }

    public float getPreis()
    {
        return preis;
    }

    public void setPreis(float preis)
    {
        this.preis = preis;
    }
}
