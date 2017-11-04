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
                Agent agent = new Agent(res.getInt("ag_id"), res.getString("ag_nom"), res.getString("ag_numero"), res.getString("ag_adresse"),res.getDouble("ag_uvinitial"), res.getDouble("caisse_initial"));
                liste.add(agent);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
}
