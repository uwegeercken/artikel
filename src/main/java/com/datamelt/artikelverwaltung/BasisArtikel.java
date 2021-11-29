package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;

public class BasisArtikel
{
    private ArtikelUrsprung ursprung;
    private String bezeichnung;
    private String beschreibung;

    public BasisArtikel(ArtikelUrsprung ursprung, String bezeichnung, String beschreibung)
    {
        this.ursprung = ursprung;
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
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
}
