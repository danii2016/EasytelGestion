/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.model;

import java.util.Date;

/**
 *
 * @author andri
 */
public class Appro_caisse {
    private int apc_id;
    private int cj_id;
    private String apc_heure;
    private String apc_date;
    private double apc_montant;
    private Date heure;
    
    public Appro_caisse() {
        this.apc_id = 0;
        this.cj_id = 0;
    }
    
    public Appro_caisse(int apc_id, int cj_id, String apc_heure, String apc_date, double apc_montant) {
        this.apc_id = apc_id;
        this.cj_id = cj_id;
        this.apc_heure = apc_heure;
        this.apc_date = apc_date;
        this.apc_montant = apc_montant;
        Date d = new Date();
        String tmphr[] = apc_heure.split(":");
        d.setHours(Integer.parseInt(tmphr[0]));
        d.setMinutes(Integer.parseInt(tmphr[1]));
        this.heure = d;
    }

    public int getApc_id() {
        return apc_id;
    }

    public void setApc_id(int apc_id) {
        this.apc_id = apc_id;
    }

    public String getApc_date() {
        return apc_date;
    }

    public void setApc_date(String apc_date) {
        this.apc_date = apc_date;
    }

    public double getApc_montant() {
        return apc_montant;
    }

    public void setApc_montant(double apc_montant) {
        this.apc_montant = apc_montant;
    }

    public int getCj_id() {
        return cj_id;
    }

    public void setCj_id(int cj_id) {
        this.cj_id = cj_id;
    }

    public String getApc_heure() {
        return apc_heure;
    }

    public void setApc_heure(String apc_heure) {
        this.apc_heure = apc_heure;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.apc_heure = heure.getHours()+":"+heure.getMinutes()+":"+heure.getSeconds();
        this.heure = heure;
    }
}
