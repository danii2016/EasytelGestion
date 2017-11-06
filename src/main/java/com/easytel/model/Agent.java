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
public class Agent {
    public int ag_id;
    private String ag_nom;
    private String ag_numero;
    private String ag_adresse;
    private double ag_uvinitial;
    private double ag_caisseinitial;

    public Agent() {
        this.ag_id = 0;
        this.ag_adresse = "";
    }

    public String getAg_adresse() {
        return ag_adresse;
    }

    public void setAg_adresse(String ag_adresse) {
        this.ag_adresse = ag_adresse;
    }

    public int getAg_id() {
        return ag_id;
    }

    public void setAg_id(int ag_id) {
        this.ag_id = ag_id;
    }

    public String getAg_nom() {
        return ag_nom;
    }

    public void setAg_nom(String ag_nom) {
        this.ag_nom = ag_nom;
    }

    public String getAg_numero() {
        return ag_numero;
    }

    public void setAg_numero(String ag_numero) {
        this.ag_numero = ag_numero;
    }

    public double getAg_uvinitial() {
        return ag_uvinitial;
    }

    public void setAg_uvinitial(double ag_uvinitial) {
        this.ag_uvinitial = ag_uvinitial;
    }

    public double getAg_caisseinitial() {
        return ag_caisseinitial;
    }

    public void setAg_caisseinitial(double ag_caisseinitial) {
        this.ag_caisseinitial = ag_caisseinitial;
    }
    
    public Agent(int id, String nom, String numero, String adresse, double uv, double caisse) {
        this.ag_id = id;
        this.ag_nom = nom;
        this.ag_numero = numero;
        this.ag_adresse = adresse;
        this.ag_uvinitial = uv;
        this.ag_caisseinitial = caisse;
    }
    
}
