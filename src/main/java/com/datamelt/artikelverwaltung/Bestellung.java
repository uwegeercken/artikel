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

    public void put(Artikel artikel, int menge)
    {
        if(artikel!=null)
        {
            if(artikelMap.containsKey(artikel))
            {
                increaseBy(artikel, menge);
            }
            else
            {
                artikelMap.put(artikel, menge);
            }
        }
    }

    public void increaseBy(Artikel artikel, int menge)
    {
        int bestehendeMenge = artikelMap.get(artikel);
        artikelMap.put(artikel,bestehendeMenge + menge);
    }

    public void decreaseBy(Artikel artikel, int menge)
    {
        int bestehendeMenge = artikelMap.get(artikel);
        if(bestehendeMenge - menge > 0)
        {
            artikelMap.put(artikel, bestehendeMenge - menge);
        }
        else
        {
            artikelMap.put(artikel, 0);
        }
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
