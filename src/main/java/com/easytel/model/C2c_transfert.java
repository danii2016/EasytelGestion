/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.model;

import com.easytel.util.FonctionUtils;
import java.math.BigDecimal;

/**
 *
 * @author andri
 */
public class C2c_transfert {
    private int c2c_id;
    private int fic_id;
    private String c2c_heure;
    private String c2c_type;
    private String c2c_numero;
    private double c2c_montant;
    
    public C2c_transfert(int c2c_id, int fic_id, String c2c_heure, String c2c_numero, double c2c_montant) {
        this.c2c_id = c2c_id;
        this.fic_id = fic_id;
        this.c2c_heure = c2c_heure;
        this.c2c_numero = c2c_numero;
        this.c2c_montant = c2c_montant;
    }

    public int getC2c_id() {
        return c2c_id;
    }

    public void setC2c_id(int c2c_id) {
        this.c2c_id = c2c_id;
    }

    public int getFic_id() {
        return fic_id;
    }

    public void setFic_id(int fic_id) {
        this.fic_id = fic_id;
    }

    public String getC2c_heure() {
        return c2c_heure;
    }

    public void setC2c_heure(String c2c_heure) {
        this.c2c_heure = c2c_heure;
    }

    public String getC2c_type() {
        return c2c_type;
    }

    public void setC2c_type(String c2c_type) {
        this.c2c_type = c2c_type;
    }

    public String getC2c_numero() {
        return c2c_numero;
    }

    public void setC2c_numero(String c2c_numero) {
        this.c2c_numero = c2c_numero;
    }

    public String getC2c_montant() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(c2c_montant));
    }

    public void setC2c_montant(double c2c_montant) {
        this.c2c_montant = c2c_montant;
    }
}
