/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.dao;

import com.easytel.model.Agent;
import com.easytel.util.dataConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author andri
 */
public class AgentDAO {
    
    public static ArrayList<Agent> getAll() {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from agent");
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Agent agent = new Agent(res.getInt("ag_id"), res.getString("ag_nom"), res.getString("ag_numero"), res.getString("ag_adresse"),res.getDouble("ag_uvinitial"), res.getDouble("ag_caisseinitial"));
                liste.add(agent);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
    public static boolean saveNewAgent(Agent agent) {
        boolean saved = false;
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Insert into agent(ag_nom, ag_adresse, ag_numero, ag_caisseinitial, ag_uvinitial) values(?, ?, ?, ?, ?)");
            ps.setString(1, agent.getAg_nom());
            ps.setString(2, agent.getAg_adresse());
            ps.setString(3, agent.getAg_numero());
            ps.setDouble(4, agent.getAg_caisseinitial());
            ps.setDouble(5, agent.getAg_uvinitial());
            saved = ps.execute();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return saved;
    }

    public static boolean check(String type, String val) {
        boolean exist = false;
        try{
            Connection con = dataConnect.getConnection();
            String query;
            if("nom".equals(type)) {
                query = "SELECT * FROM agent WHERE ag_nom = ? ";
            } else {
                query = "SELECT * FROM agent WHERE ag_numero = ? ";
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, val);
            ResultSet res = ps.executeQuery();
            exist = res.next();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return exist;
    }
    
}
