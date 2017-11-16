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
    private String fic_debutperiode;
    private String ag_nom = "";
    private String ag_numero = "";
    private String fic_nom;
    private String fic_date;
    private String fic_dateimport;
    
    public Fichier(int fic_id, int ag_id, String fic_debutperiode, String ag_nom, String ag_numero, String fic_nom, String fic_date, String fic_dateimport)  {
        this.fic_id = fic_id;
        this.ag_id = ag_id;
        this.ag_nom = ag_nom;
        this.ag_numero = ag_numero;
        this.fic_nom = fic_nom;
        this.fic_date = fic_date;
        this.fic_dateimport = fic_dateimport;
        this.fic_debutperiode = fic_debutperiode;
    }

    public String getAg_numero() {
        return ag_numero;
    }

    public void setAg_numero(String ag_numero) {
        this.ag_numero = ag_numero;
    }

    public String getAg_nom() {
        return ag_nom;
    }

    public void setAg_nom(String ag_nom) {
        this.ag_nom = ag_nom;
    }

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

    public String getFic_debutperiode() {
        return fic_debutperiode;
    }

    public void setFic_debutperiode(String fic_debutperiode) {
        this.fic_debutperiode = fic_debutperiode;
    }
    
}
