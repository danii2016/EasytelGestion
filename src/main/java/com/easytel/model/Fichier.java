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
public class Fichier {
    private int fic_id;
    private int ag_id;
    private String fic_nom;
    private String fic_date;
    private String fic_dateimport;

    public int getFic_id() {
        return fic_id;
    }

    public void setFic_id(int fic_id) {
        this.fic_id = fic_id;
    }

    public int getAg_id() {
        return ag_id;
    }

    public void setAg_id(int ag_id) {
        this.ag_id = ag_id;
    }

    public String getFic_nom() {
        return fic_nom;
    }

    public void setFic_nom(String fic_nom) {
        this.fic_nom = fic_nom;
    }

    public String getFic_date() {
        return fic_date;
    }

    public void setFic_date(String fic_date) {
        this.fic_date = fic_date;
    }

    public String getFic_dateimport() {
        return fic_dateimport;
    }

    public void setFic_dateimport(String fic_dateimport) {
        this.fic_dateimport = fic_dateimport;
    }
    
}
