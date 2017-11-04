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
public class Cash_out {
    private int co_id;
    private int fic_id;
    private String co_heure;
    private String co_numero;
    private double co_montant;

    public int getCo_id() {
        return co_id;
    }

    public void setCo_id(int co_id) {
        this.co_id = co_id;
    }

    public int getFic_id() {
        return fic_id;
    }

    public void setFic_id(int fic_id) {
        this.fic_id = fic_id;
    }

    public String getCo_heure() {
        return co_heure;
    }

    public void setCo_heure(String co_heure) {
        this.co_heure = co_heure;
    }

    public String getCo_numero() {
        return co_numero;
    }

    public void setCo_numero(String co_numero) {
        this.co_numero = co_numero;
    }

    public double getCo_montant() {
        return co_montant;
    }

    public void setCo_montant(double co_montant) {
        this.co_montant = co_montant;
    }
}
