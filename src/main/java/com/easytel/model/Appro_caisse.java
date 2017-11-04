/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.model;

/**
 *
 * @author andri
 */
public class Appro_caisse {
    private int apc_id;
    private int ag_id;
    private String apc_date;
    private double apc_montant;

    public int getApc_id() {
        return apc_id;
    }

    public void setApc_id(int apc_id) {
        this.apc_id = apc_id;
    }

    public int getAg_id() {
        return ag_id;
    }

    public void setAg_id(int ag_id) {
        this.ag_id = ag_id;
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
}
