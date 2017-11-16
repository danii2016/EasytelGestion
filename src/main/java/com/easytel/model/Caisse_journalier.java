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
public class Caisse_journalier {
    private int cj_id;
    private int ag_id;
    private int fic_id;
    private String cj_date;
    private double cj_approcaisse;
    private double cj_caisseinitial;
    private double cj_caissefinal;
    private double cj_commission;
    private double cj_degagementcaisse;
    private double cj_degagementcaisse2;
    private double cj_uvinitial;
    private double cj_uvfinal;
    private double cj_totaltransfert;
    private double cj_totalcashin;
    private double cj_totalcashout;
    
    public Caisse_journalier(int cj_id, int ag_id, int fic_id, String cj_date, 
                            double cj_caisseinitial,
                            double cj_caissefinal,
                            double cj_commission,
                            double cj_degagementcaisse,
                            double cj_uvinitial,
                            double cj_uvfinal,
                            double cj_totaltransfert,
                            double cj_totalcashin,
                            double cj_totalcashout,
                            double cj_approcaisse) {
        this.cj_id = cj_id;
        this.ag_id = ag_id;
        this.fic_id = fic_id;
        this.cj_date = cj_date;
        this.cj_caisseinitial = cj_caisseinitial;
        this.cj_caissefinal = cj_caissefinal;
        this.cj_commission = cj_commission;
        this.cj_degagementcaisse =  cj_degagementcaisse;
        this.cj_degagementcaisse2 = cj_degagementcaisse;
        this.cj_uvinitial = cj_uvinitial;
        this.cj_uvfinal = cj_uvfinal;
        this.cj_totaltransfert = cj_totaltransfert;
        this.cj_totalcashin = cj_totalcashin;
        this.cj_totalcashout = cj_totalcashout;
        this.cj_approcaisse = cj_approcaisse;
    }

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

    public String getCj_caisseinitial() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_caisseinitial));
    }

    public void setCj_caisseinitial(double cj_caisseinitial) {
        this.cj_caisseinitial = cj_caisseinitial;
    }

    public String getCj_caissefinal() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_caissefinal));
    }

    public void setCj_caissefinal(double cj_caissefinal) {
        this.cj_caissefinal = cj_caissefinal;
    }

    public String getCj_commission() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_commission));
    }

    public void setCj_commission(double cj_commission) {
        this.cj_commission = cj_commission;
    }

    public String getCj_degagementcaisse() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_degagementcaisse));
    }

    public void setCj_degagementcaisse(double cj_degagementcaisse) {
        this.cj_degagementcaisse2 = cj_degagementcaisse;
        this.cj_degagementcaisse = cj_degagementcaisse;
    }

    public String getCj_uvinitial() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_uvinitial));
    }

    public void setCj_uvinitial(double cj_uvinitial) {
        this.cj_uvinitial = cj_uvinitial;
    }

    public String getCj_uvfinal() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_uvfinal));
    }

    public void setCj_uvfinal(double cj_uvfinal) {
        this.cj_uvfinal = cj_uvfinal;
    }

    public int getFic_id() {
        return fic_id;
    }

    public void setFic_id(int fic_id) {
        this.fic_id = fic_id;
    }

    public String getCj_totaltransfert() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_totaltransfert));
    }

    public void setCj_totaltransfert(double cj_totaltransfert) {
        this.cj_totaltransfert = cj_totaltransfert;
    }

    public String getCj_totalcashin() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_totalcashin));
    }

    public void setCj_totalcashin(double cj_totalcashin) {
        this.cj_totalcashin = cj_totalcashin;
    }

    public String getCj_totalcashout() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_totalcashout));
    }

    public void setCj_totalcashout(double cj_totalcashout) {
        this.cj_totalcashout = cj_totalcashout;
    }

    public String getCj_approcaisse() {
        return FonctionUtils.normaliseChiffre(new BigDecimal(cj_approcaisse));
    }

    public void setCj_approcaisse(double cj_approcaisse) {
        this.cj_approcaisse = cj_approcaisse;
    }

    public double getCj_degagementcaisse2() {
        return cj_degagementcaisse2;
    }

    public void setCj_degagementcaisse2(double cj_degagementcaisse2) {
        this.cj_degagementcaisse = cj_degagementcaisse2;
        this.cj_degagementcaisse2 = cj_degagementcaisse2;
    }
    
}
