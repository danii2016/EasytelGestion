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
import javax.enterprise.context.Dependent;

/**
 *
 * @author andri
 */
@Named(value = "agentBean")
@Dependent
public class AgentBean {
    public Agent currAgent;

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
    
    public void saveAgent() {
        if(currAgent != null) {
            
        }
    }
    
    public List<Agent> getAgents() {
        ArrayList listeAgent = AgentDAO.getAll();
        return listeAgent;
    }
    
}
