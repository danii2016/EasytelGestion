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
import java.util.List;

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
            ps.close();
            con.close();
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
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return exist;
    }

    public static boolean saveAgent(int id, Agent agent) {
        boolean saved = false;
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement checks = con.prepareStatement("SELECT * FROM agent WHERE ag_id != ? and (ag_nom = ? or ag_numero = ?)");
            checks.setInt(1, id);
            checks.setString(2, agent.getAg_nom());
            checks.setString(3, agent.getAg_numero());
            ResultSet res = checks.executeQuery();
            if(!res.next()) {
                PreparedStatement ps = con.prepareStatement("UPDATE agent SET ag_id = ?, ag_nom = ?, ag_adresse = ?, ag_numero = ? , ag_caisseinitial = ?, ag_uvinitial = ? WHERE ag_id = ?");
                ps.setInt(1, agent.getAg_id());
                ps.setString(2, agent.getAg_nom());
                ps.setString(3, agent.getAg_adresse());
                ps.setString(4, agent.getAg_numero());
                ps.setDouble(5, agent.getAg_caisseinitial());
                ps.setDouble(6, agent.getAg_uvinitial());
                ps.setInt(7, id);
                saved = ps.execute();
                ps.close();
                con.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return saved;
    }

    public static boolean deleteAgent(Agent agent) {
        boolean deleted = false;
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM agent WHERE ag_id = ?");
            ps.setInt(1, agent.getAg_id());
            deleted = ps.execute();
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return deleted;
    }
    

    public static List<String> getListNomLike(String query) {
        ArrayList<String> liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("select ag_nom from agent where ag_nom like ?");
            ps.setString(1, "%"+query+"%");
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                liste.add(res.getString("ag_nom"));
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
}
