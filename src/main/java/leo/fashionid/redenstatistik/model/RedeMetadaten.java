/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.model;

import java.util.Date;

/**
 *
 * @author odzhara-ongom
 */
public class RedeMetadaten {

    private String redner;
    private String thema;
    private Date datum;
    private long woerter;

    public String getRedner() {
        return redner;
    }

    public void setRedner(String redner) {
        this.redner = redner;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public long getWoerter() {
        return woerter;
    }

    public void setWoerter(long woerter) {
        this.woerter = woerter;
    }

}
