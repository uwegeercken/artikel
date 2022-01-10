package com.datamelt.artikelverwaltung;

public interface BestellungDAO
{
        @SqlUpdate("insert into bestellung (id, bestell_datum) values (?, ?)")
        void insert(long id, String bestell_datum);
    }
}
