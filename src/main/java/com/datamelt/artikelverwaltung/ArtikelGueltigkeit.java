package com.datamelt.artikelverwaltung;

import java.util.Date;

public class ArtikelGueltigkeit
{
    private Date gueltigVon;
    private Date gueltigBis;

    public ArtikelGueltigkeit(Date gueltigVon, Date gueltigBis)
    {
        this.gueltigVon = gueltigVon;
        this.gueltigBis = gueltigBis;
    }

    public Date getGueltigVon()
    {
        return gueltigVon;
    }

    public Date getGueltigBis()
    {
        return gueltigBis;
    }
}
