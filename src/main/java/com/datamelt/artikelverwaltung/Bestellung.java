package com.datamelt.artikelverwaltung;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bestellung
{
    private Map<Artikel,Integer> artikelMap = new HashMap<>();
    private Date bestellDatum;

    public Bestellung(Date bestellDatum)
    {
        this.bestellDatum = bestellDatum;
    }

    public Date getBestellDatum()
    {
        return bestellDatum;
    }

    public void put(Artikel artikel, int menge) throws Exception
    {
        artikelMap.put(artikel, menge);
    }

    public void remove(Artikel artikel)
    {
        artikelMap.remove(artikel);
    }

    public Map<Artikel,Integer> getArtikel()
    {
        return artikelMap;
    }
}
