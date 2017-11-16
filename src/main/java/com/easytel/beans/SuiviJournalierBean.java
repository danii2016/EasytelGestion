/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.beans;

import java.io.Serializable;

import com.easytel.dao.AgentDAO;
import com.easytel.dao.SuiviJourDAO;
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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author andri
 */
@ManagedBean
@ViewScoped
public class SuiviJournalierBean {
    private static final long serialVersionUID = 1094801825325386363L;
    
    private boolean changed = false;
    private String nom_agent;
    private Date dateJour;
    private Date dateMax;
    private String datefr;
    private Caisse_journalier caisse;
    private List<Cash_in> listeCashIn;
    private List<Cash_out> listeCashOut;
    private List<C2c_transfert> listeTransfert;
    private List<Appro_caisse> listeApproCaisse;
    private Appro_caisse newAppro = new Appro_caisse();
    private Appro_caisse currAppro;

    /**
     * Creates a new instance of SuiviJournalierBean
     */
    public SuiviJournalierBean() {
        Date date = new Date();
        this.dateJour = date;
        this.dateMax= date;
    }
    
    public void changeAgent() {
        this.changed = false;
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
        DateFormat.SHORT,
        DateFormat.SHORT, new Locale("FR","fr"));
        this.datefr = shortDateFormat.format(dateJour).split(" ")[0];
        initData();
    }
    
    public List<String> completeText(String query) {
        List<String> results = AgentDAO.getListNomLike(query);
        return results;
    }

    public String getDatefr() {
        return datefr;
    }

    public void setDatefr(String datefr) {
        this.datefr = datefr;
    }
    

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getNom_agent() {
        return nom_agent;
    }

    public void setNom_agent(String nom_agent) {
        this.nom_agent = nom_agent;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
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

    public Appro_caisse getNewAppro() {
        return newAppro;
    }

    public void setNewAppro(Appro_caisse newAppro) {
        this.newAppro = newAppro;
    }

    public Appro_caisse getCurrAppro() {
        return currAppro;
    }

    public void setCurrAppro(Appro_caisse currAppro) {
        this.currAppro = currAppro;
    }
    
    public void saveNewAppro() {
        newAppro.setCj_id(caisse.getCj_id());
        newAppro.setApc_date(caisse.getCj_date());
        boolean saved = SuiviJourDAO.saveAppro(newAppro, "insert");
    }
    
    public void approSelect() {
        currAppro = null;
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String source[] = request.getParameter("javax.faces.source").split(":");
        String id = request.getParameter(source[0]+":"+source[1]+":appro_caisses_selection");
        System.out.print("Séléction "+id);
        currAppro = SuiviJourDAO.get_appro(id);
    }
    
    public void supprAppro() {
        boolean saved = SuiviJourDAO.deleteAppro(currAppro);
    }
    
    public void saveCurrAppro() {
        boolean saved = SuiviJourDAO.saveAppro(currAppro, "update");
    }
    
    public void saveDegagement() {
        boolean res = SuiviJourDAO.saveDegagement(caisse);
    }
    
    private void initData() {
        this.caisse = SuiviJourDAO.getCaisseDuJour(nom_agent, datefr);
        if(caisse != null) {
            listeApproCaisse = SuiviJourDAO.getListeAppro(caisse.getCj_id());
            listeCashIn = SuiviJourDAO.getListeCashIn(caisse.getFic_id());
            listeCashOut = SuiviJourDAO.getListeCashOut(caisse.getFic_id());
            listeTransfert = SuiviJourDAO.getListeTransfert(caisse.getFic_id());
            changed = true;
        } else {
            changed = false;
        }
    }
}
