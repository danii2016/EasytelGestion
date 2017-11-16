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
public class Cash_in {
    private int ci_id;
    private int fic_id;
    private String ci_heure;
    private String ci_numero;
    private double ci_montant;

    public Cash_in(int ci_id, int fic_id, String ci_heure, String ci_numero, double ci_montant) {
        this.ci_id = ci_id;
        this.fic_id = fic_id;
        this.ci_heure = ci_heure;
        this.ci_numero = ci_numero;
        this.ci_montant = ci_montant;
    }
    
    public int getCi_id() {
        return ci_id;
    }

    public void setCi_id(int ci_id) {
        this.ci_id = ci_id;
    }

    public int getFic_id() {
        return fic_id;
    }

    public void setFic_id(int fic_id) {
        this.fic_id = fic_id;
    }

    public String getCi_heure() {
        return ci_heure;
    }

    public void setCi_heure(String ci_heure) {
        this.ci_heure = ci_heure;
    }

    public String getCi_numero() {
        return ci_numero;
    }

    public void setCi_numero(String ci_numero) {
        this.ci_numero = ci_numero;
    }

    public String getCi_montant() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(ci_montant));
    }

    public void setCi_montant(double ci_montant) {
        this.ci_montant = ci_montant;
    }
    
}
