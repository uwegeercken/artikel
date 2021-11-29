package com.datamelt.artikelverwaltung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bestellung
{
    private Map<Artikel,Integer> artikelMap = new HashMap<>();

    public void put(Artikel artikel, int menge)
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
