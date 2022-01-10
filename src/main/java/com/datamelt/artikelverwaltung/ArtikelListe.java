package com.datamelt.artikelverwaltung;

import com.datamelt.utilities.Constants;
import org.jdbi.v3.core.Jdbi;

import java.util.Date;
import java.util.List;

public class ArtikelListe
{
    private static Jdbi jdbi;

    public static List<Artikel> getAll(boolean validOnly)
    {
        jdbi = Jdbi.create("jdbc:sqlite:" + Constants.DATABASE_FOLDER + "/" + Constants.DATABASE_NAME);
        return ladeArtikel(validOnly);
    }

    private static List<Artikel> ladeArtikel(boolean validOnly)
    {
        long currentDatetime = new Date().getTime();

        String sql = "select artikel.*," +
            " herkunft.name as herkunft," +
            " ursprung.name as ursprung," +
            " behälter.name as behälter" +
            " from artikel, herkunft, ursprung, behälter " +
            " where artikel.herkunft_id=herkunft.id" +
            " and artikel.ursprung_id=ursprung.id" +
            " and artikel.behälter_id=behälter.id";

        if(validOnly)
        {
            sql = sql +
                " and gültig_von <= " + currentDatetime +
                " and gültig_bis >= " + currentDatetime;
        }
        String finalSql = sql;
        return jdbi.withHandle(handle ->
                handle.createQuery(finalSql)
                        .mapToBean(Artikel.class)
                        .list());
    }

    public static Artikel getArtikelByNumber(List<Artikel> artikelListe, long nummer)
    {
        for(Artikel artikel : artikelListe)
        {
            if(artikel.getNummer()== nummer)
            {
                return artikel;
            }
        }
        return null;
    }
}
