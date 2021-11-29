package com.datamelt.artikelverwaltung;

import java.util.ArrayList;
import java.util.List;

public class Bestellung
{
    private List<BasisArtikel> artikelListe = new ArrayList<>();
    private List<ArtikelSammlung> artikelSammlungListe = new ArrayList<>();

    public void addArtiktel(BasisArtikel artikel)
    {
        artikelListe.add(artikel);
    }

    public void removeArtikel(BasisArtikel artikel)
    {
        // todo
    }

    public void addArtiktelSammlung(ArtikelSammlung artikelSammlung)
    {
        artikelSammlungListe.add(artikelSammlung);
    }

    public void removeArtikelSammlung(ArtikelSammlung artikelSammlung)
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

    public List<ArtikelSammlung> getArtikelSammlungListe()
    {
        return artikelSammlungListe;
    }

    public void setArtikelSammlungListe(List<ArtikelSammlung> artikelSammlungListe)
    {
        this.artikelSammlungListe = artikelSammlungListe;
    }
}
