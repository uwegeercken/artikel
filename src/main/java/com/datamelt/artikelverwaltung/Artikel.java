package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelArt;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;

import java.util.Date;

public abstract class Artikel
{
    private ArtikelArt art;
    private ArtikelUrsprung ursprung;
    private String bezeichnung;
    private String beschreibung;
    private Date gueltigVon;
    private Date gueltigBis;


    public Artikel(ArtikelArt art, ArtikelUrsprung ursprung, String bezeichnung, Date gueltigVon, Date gueltigBis)
    {
        this.art = art;
        this.ursprung = ursprung;
        this.bezeichnung = bezeichnung;
        this.gueltigVon = gueltigVon;
        this.gueltigBis = gueltigBis;
    }

    protected abstract void erstellen();

    public ArtikelArt getArt()
    {
        return art;
    }

    public ArtikelUrsprung getUrsprung()
    {
        return ursprung;
    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public Date getGueltigVon()
    {
        return gueltigVon;
    }

    public Date getGueltigBis()
    {
        return gueltigBis;
    }

    public String getBeschreibung()
    {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung)
    {
        this.beschreibung = beschreibung;
    }
}
