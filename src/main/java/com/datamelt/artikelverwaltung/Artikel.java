package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelArt;
import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;
import org.graalvm.compiler.api.replacements.Snippet;

import java.util.Date;

public abstract class Artikel
{
    private ArtikelUrsprung ursprung;
    private String bezeichnung;
    private String beschreibung;
    private String bemerkung;
    private Date gueltigVon;
    private Date gueltigBis;


    public Artikel(ArtikelUrsprung ursprung, String bezeichnung, Date gueltigVon, Date gueltigBis)
    {
        this.ursprung = ursprung;
        this.bezeichnung = bezeichnung;
        this.gueltigVon = gueltigVon;
        this.gueltigBis = gueltigBis;
    }

    protected abstract void erstellen();

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

    public String getBemerkung()
    {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung)
    {
        this.bemerkung = bemerkung;
    }
}
