package com.datamelt.artikelverwaltung;

import com.datamelt.artikelverwaltung.enums.ArtikelUrsprung;
import com.datamelt.artikelverwaltung.enums.MengenTyp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArtikelSammlung
{
    private ArtikelUrsprung ursprung;
    private String bezeichnung;
    private String beschreibung;
    private String bemerkung;
    private Date gueltigVon;
    private Date gueltigBis;
    private double menge;
    private MengenTyp mengenTyp;
    private double preis;

    private List<BasisArtikel> artikelListe = new ArrayList<>();

    public void addArtikel(BasisArtikel artikel)
    {
        artikelListe.add(artikel);
    }

    public void removeArtikel(BasisArtikel artikel)
    {
        // todo
    }

    public List<BasisArtikel> getArtikelListe()
    {
        return artikelListe;
    }

    public void setArtikelListe(List<BasisArtikel> artikelListe)
    {
        this.artikelListe = artikelListe;
    }
}
