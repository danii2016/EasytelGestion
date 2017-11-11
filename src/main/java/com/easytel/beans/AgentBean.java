/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.beans;

import com.easytel.dao.AgentDAO;
import com.easytel.model.Agent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author andri
 */
@Named(value = "agentBean")
@ManagedBean
public class AgentBean {
    public Agent currAgent;
    public String nom_agent;
    public String adresse_agent;
    public String numero_agent;
    public double uv_agent;
    public double caisse_agent;

    public String getNom_agent() {
        return nom_agent;
    }

    public void setNom_agent(String nom_agent) {
        this.nom_agent = nom_agent;
    }

    public String getAdresse_agent() {
        return adresse_agent;
    }

    public void setAdresse_agent(String adresse_agent) {
        this.adresse_agent = adresse_agent;
    }

    public String getNumero_agent() {
        return numero_agent;
    }

    public void setNumero_agent(String numero_agent) {
        this.numero_agent = numero_agent;
    }

    public double getUv_agent() {
        return uv_agent;
    }

    public void setUv_agent(double uv_agent) {
        this.uv_agent = uv_agent;
    }

    public double getCaisse_agent() {
        return caisse_agent;
    }

    public void setCaisse_agent(double caisse_agent) {
        this.caisse_agent = caisse_agent;
    }
    
    public Agent getCurrAgent() {
        return currAgent;
    }

    public void setCurrAgent(Agent currAgent) {
        this.currAgent = currAgent;
    }

    /**
     * Creates a new instance of AgentBean
     */
    public AgentBean() {
    }
    
    public void supprimerAgent() {
        boolean ok = AgentDAO.deleteAgent(currAgent);
       FacesContext.getCurrentInstance().addMessage( null,
                new FacesMessage(ok ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, null, ok ? "Supprimé" : "Echec de la suppression")); 
    }
    
    public void saveCurrent() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nom = request.getParameter("form:inputNomEdit");
        String adresse = request.getParameter("form:inputAdresseEdit");
        String id = request.getParameter("form:inputIdEdit");
        String numero = request.getParameter("form:inputNumeroEdit");
        String uv = request.getParameter("form:inputUVEdit_hinput");
        String caisse = request.getParameter("form:inputCaisseEdit_hinput");
        int idactu = currAgent.getAg_id();
        System.out.print(uv);
        currAgent.setAg_nom(nom);
        currAgent.setAg_adresse(adresse);
        currAgent.setAg_numero(numero);
        currAgent.setAg_id(Integer.parseInt(id));
        currAgent.setAg_uvinitial(Double.parseDouble(uv));
        currAgent.setAg_caisseinitial(Double.parseDouble(caisse));
        boolean ok = AgentDAO.saveAgent(idactu, currAgent);
        FacesContext.getCurrentInstance().addMessage( null,
                new FacesMessage(ok ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, null, ok ? "Enregistré avec succès" : "Echec de l'enregistrement"));
    }
    
    public void addnew() {
        Agent newAgent = new Agent();
        newAgent.setAg_nom(nom_agent);
        newAgent.setAg_adresse(adresse_agent);
        newAgent.setAg_numero(numero_agent);
        newAgent.setAg_caisseinitial(caisse_agent);
        newAgent.setAg_uvinitial(uv_agent);
        boolean ok = AgentDAO.saveNewAgent(newAgent);
        FacesContext.getCurrentInstance().addMessage( null,
                new FacesMessage(ok ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, null, ok ? "Enregistré avec succès" : "Echec de l'enregistrement"));
    }
    
    public List<Agent> getAgents() {
        ArrayList listeAgent = AgentDAO.getAll();
        return listeAgent;
    }
    
}
