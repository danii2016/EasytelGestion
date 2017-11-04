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
public class Caisse_journalier {
    private int cj_id;
    private int ag_id;
    private String cj_date;
    private double cj_caisseinitial;
    private double cj_caissefinal;
    private double cj_commission;
    private double cj_degagementcaisse;
    private double cj_uvinitial;
    private double cj_uvfinal;

    public int getCj_id() {
        return cj_id;
    }

    public void setCj_id(int cj_id) {
        this.cj_id = cj_id;
    }

    public int getAg_id() {
        return ag_id;
    }

    public void setAg_id(int ag_id) {
        this.ag_id = ag_id;
    }

    public String getCj_date() {
        return cj_date;
    }

    public void setCj_date(String cj_date) {
        this.cj_date = cj_date;
    }

    public double getCj_caisseinitial() {
        return cj_caisseinitial;
    }

    public void setCj_caisseinitial(double cj_caisseinitial) {
        this.cj_caisseinitial = cj_caisseinitial;
    }

    public double getCj_caissefinal() {
        return cj_caissefinal;
    }

    public void setCj_caissefinal(double cj_caissefinal) {
        this.cj_caissefinal = cj_caissefinal;
    }

    public double getCj_commission() {
        return cj_commission;
    }

    public void setCj_commission(double cj_commission) {
        this.cj_commission = cj_commission;
    }

    public double getCj_degagementcaisse() {
        return cj_degagementcaisse;
    }

    public void setCj_degagementcaisse(double cj_degagementcaisse) {
        this.cj_degagementcaisse = cj_degagementcaisse;
    }

    public double getCj_uvinitial() {
        return cj_uvinitial;
    }

    public void setCj_uvinitial(double cj_uvinitial) {
        this.cj_uvinitial = cj_uvinitial;
    }

    public double getCj_uvfinal() {
        return cj_uvfinal;
    }

    public void setCj_uvfinal(double cj_uvfinal) {
        this.cj_uvfinal = cj_uvfinal;
    }
}
