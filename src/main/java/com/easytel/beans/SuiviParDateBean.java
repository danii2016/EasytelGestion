/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.beans;

import com.easytel.dao.AgentDAO;
import com.easytel.dao.SuiviDateDAO;
import com.easytel.model.Appro_caisse;
import com.easytel.model.C2c_transfert;
import com.easytel.model.Caisse_journalier;
import com.easytel.model.Cash_in;
import com.easytel.model.Cash_out;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author andri
 */
@ManagedBean
@ViewScoped
public class SuiviParDateBean {
    private static final long serialVersionUID = 1094845825325386363L;
    
    DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("FR","fr"));
    private boolean changed = false;
    private String nom_agent;
    private Date dateJourDebut;
    private Date dateJourFin;
    private Date dateMax;
    private String datefrdebut;
    private String datefrfin;
    private Caisse_journalier caisse;
    private List<Cash_in> listeCashIn;
    private List<Cash_out> listeCashOut;
    private List<C2c_transfert> listeTransfert;
    private List<Appro_caisse> listeApproCaisse;
    /**
     * Creates a new instance of SuiviParDateBean
     */
    public SuiviParDateBean() {
        Date date = new Date();
        this.dateJourDebut = date;
        this.dateJourFin = date;
        this.dateMax= date;
    }
    
    public List<String> completeText(String query) {
        List<String> results = AgentDAO.getListNomLike(query);
        return results;
    }

    public DateFormat getShortDateFormat() {
        return shortDateFormat;
    }

    public void setShortDateFormat(DateFormat shortDateFormat) {
        this.shortDateFormat = shortDateFormat;
    }

    public String getNom_agent() {
        return nom_agent;
    }

    public void setNom_agent(String nom_agent) {
        this.nom_agent = nom_agent;
    }

    public Date getDateJourDebut() {
        return dateJourDebut;
    }

    public void setDateJourDebut(Date dateJourDebut) {
        this.dateJourDebut = dateJourDebut;
    }

    public Date getDateJourFin() {
        return dateJourFin;
    }

    public void setDateJourFin(Date dateJourFin) {
        this.dateJourFin = dateJourFin;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }

    public String getDatefrdebut() {
        return datefrdebut;
    }

    public void setDatefrdebut(String datefrdebut) {
        this.datefrdebut = datefrdebut;
    }

    public String getDatefrfin() {
        return datefrfin;
    }

    public void setDatefrfin(String datefrfin) {
        this.datefrfin = datefrfin;
    }

    public Caisse_journalier getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse_journalier caisse) {
        this.caisse = caisse;
    }

    public List<Cash_in> getListeCashIn() {
        return listeCashIn;
    }

    public void setListeCashIn(List<Cash_in> listeCashIn) {
        this.listeCashIn = listeCashIn;
    }

    public List<Cash_out> getListeCashOut() {
        return listeCashOut;
    }

    public void setListeCashOut(List<Cash_out> listeCashOut) {
        this.listeCashOut = listeCashOut;
    }

    public List<C2c_transfert> getListeTransfert() {
        return listeTransfert;
    }

    public void setListeTransfert(List<C2c_transfert> listeTransfert) {
        this.listeTransfert = listeTransfert;
    }

    public List<Appro_caisse> getListeApproCaisse() {
        return listeApproCaisse;
    }

    public void setListeApproCaisse(List<Appro_caisse> listeApproCaisse) {
        this.listeApproCaisse = listeApproCaisse;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    public void changeAgent(SelectEvent event) {
        this.changed = false;
        this.datefrdebut = shortDateFormat.format(dateJourDebut).split(" ")[0];
        this.datefrfin = shortDateFormat.format(dateJourFin).split(" ")[0];
        initData();
    }

    private void initData() {
        this.caisse = SuiviDateDAO.getCaisse(nom_agent, datefrdebut, datefrfin);
        if(caisse != null) {
            this.listeApproCaisse = SuiviDateDAO.getListeAppro(nom_agent, datefrdebut, datefrfin);
            this.listeCashIn= SuiviDateDAO.getListeCashIn(nom_agent, datefrdebut, datefrfin);
            this.listeCashOut= SuiviDateDAO.getListeCashOut(nom_agent, datefrdebut, datefrfin);
            this.listeTransfert = SuiviDateDAO.getListeTransfert(nom_agent, datefrdebut, datefrfin);
            this.changed = true;
        }
    }
}
