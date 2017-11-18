/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.dao;

import com.easytel.model.Appro_caisse;
import com.easytel.model.C2c_transfert;
import com.easytel.model.Caisse_journalier;
import com.easytel.model.Cash_in;
import com.easytel.model.Cash_out;
import com.easytel.util.FonctionUtils;
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
public class SuiviDateDAO {
    public static Caisse_journalier getCaisse(String nom, String datedebut, String datefin) {
        Caisse_journalier caisseinitial = null;
        Caisse_journalier caissefinal = null;
        double commission = 0, degagement = 0, transfert = 0, cash_in = 0, cash_out = 0, appro_caisse = 0;
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select distinct caisse_journalier.*, fic_id, (select sum(apc_montant) from appro_caisse where cj_id = caisse_journalier.cj_id) as cj_approcaisse from caisse_journalier join agent on caisse_journalier.ag_id = agent.ag_id join fichier on (fichier.ag_id = agent.ag_id and caisse_journalier.cj_date = fichier.fic_debutperiode) where ag_nom = ? and cj_date >= ? and cj_date <= ? group by fic_id order by cj_date asc");
            ps.setString(1, nom);
            ps.setString(2, FonctionUtils.getSQLDateFromDateShort(datedebut));
            ps.setString(3, FonctionUtils.getSQLDateFromDateShort(datefin));
            ResultSet res = ps.executeQuery();
            ArrayList traites = new ArrayList();
            while(res.next()) {
                double csi= Double.parseDouble(res.getString("cj_caisseinitial")),
                        csf = Double.parseDouble(res.getString("cj_caissefinal")),
                        com = Double.parseDouble(res.getString("cj_commissionci")) + Double.parseDouble(res.getString("cj_commissionco")),
                        dgcs = Double.parseDouble(res.getString("cj_degagementcaisse")),
                        uvi = Double.parseDouble(res.getString("cj_uvinitial")),
                        uvf = Double.parseDouble(res.getString("cj_uvfinal")),
                        tott = Double.parseDouble(res.getString("cj_totaltransfert")),
                        totci = Double.parseDouble(res.getString("cj_totalci")+(res.getString("cj_totalci").contains(".") ? "" : ".0")),
                        totco = Double.parseDouble(res.getString("cj_totalco")),
                        apc = res.getDouble("cj_approcaisse");
                String datej = res.getString("cj_date");
                int id = res.getInt("cj_id"), ag = res.getInt("ag_id"), fic = res.getInt("fic_id");
                if(!traites.contains(id)) {
                    commission += com;
                    degagement += dgcs;
                    transfert += tott;
                    cash_in += totci;
                    cash_out += totco;
                    appro_caisse += apc;
                    if(caisseinitial == null) {
                        caissefinal = caisseinitial = new Caisse_journalier(id, ag, fic, datej, csi, csf, com, dgcs, uvi, uvf, tott, totco, totci, apc);
                    } else {
                        caissefinal = new Caisse_journalier(id, ag, fic, datej, csi, csf, com, dgcs, uvi, uvf, tott, totco, totci, apc);
                    }
                    traites.add(id);
                }
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        double caisi =0, caisfi = 0,uvi = 0, uvfi = 0; 
        
        try {
            caisi = Double.parseDouble(caisseinitial.getCj_caisseinitial().replaceAll(".00$", "").replaceAll("\\.", ""));
            caisfi = Double.parseDouble(caissefinal.getCj_caissefinal().replaceAll(".00$", "").replaceAll("\\.", ""));
            uvi = Double.parseDouble(caisseinitial.getCj_uvinitial().replaceAll(".00$", "").replaceAll("\\.", ""));
            uvfi = Double.parseDouble(caissefinal.getCj_uvfinal().replaceAll(".00$", "").replaceAll("\\.", ""));
        } catch(NumberFormatException | NullPointerException e) {
            System.out.print(e);
        }
        return caisseinitial == null ? null : new Caisse_journalier(1, caisseinitial.getAg_id(), 1, caissefinal.getCj_date(), caisi, caisfi, commission, degagement, uvi, uvfi, transfert, cash_out, cash_out, appro_caisse);
    }
    
    public static ArrayList<Cash_in> getListeCashIn(String nomagent, String debut, String fin) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from cash_in join fichier on fichier.fic_id = cash_in.fic_id where ag_id = (select ag_id from agent where ag_nom = ? limit 1) and fic_debutperiode >= ? and fic_debutperiode <= ?");
            ps.setString(1, nomagent);
            ps.setString(2, FonctionUtils.getSQLDateFromDateShort(debut));
            ps.setString(3, FonctionUtils.getSQLDateFromDateShort(fin));
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Cash_in ci = new Cash_in(res.getInt("ci_id"), res.getInt("fic_id"), res.getString("ci_heure"), res.getString("ci_numero"), res.getDouble("ci_montant"));
                ci.setCi_date(FonctionUtils.reverseSqlDate(res.getString("fic_debutperiode")));
                liste.add(ci);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
    public static ArrayList<Cash_out> getListeCashOut(String nomagent, String debut, String fin) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from cash_out join fichier on fichier.fic_id = cash_out.fic_id where ag_id = (select ag_id from agent where ag_nom = ? limit 1) and fic_debutperiode >= ? and fic_debutperiode <= ?");
            ps.setString(1, nomagent);
            ps.setString(2, FonctionUtils.getSQLDateFromDateShort(debut));
            ps.setString(3, FonctionUtils.getSQLDateFromDateShort(fin));
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Cash_out co = new Cash_out(res.getInt("co_id"), res.getInt("fic_id"), res.getString("co_heure"), res.getString("co_numero"), res.getDouble("co_montant"));
                co.setCo_date(FonctionUtils.reverseSqlDate(res.getString("fic_debutperiode")));
                liste.add(co);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
    public static ArrayList<C2c_transfert> getListeTransfert(String nomagent, String debut, String fin) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from c2c_transfer join fichier on fichier.fic_id = c2c_transfer.fic_id where ag_id = (select ag_id from agent where ag_nom = ? limit 1) and fic_debutperiode >= ? and fic_debutperiode <= ?");
            ps.setString(1, nomagent);
            ps.setString(2, FonctionUtils.getSQLDateFromDateShort(debut));
            ps.setString(3, FonctionUtils.getSQLDateFromDateShort(fin));
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                C2c_transfert ct = new C2c_transfert(res.getInt("c2c_id"), res.getInt("fic_id"), res.getString("c2c_heure"), res.getString("c2c_numero"), res.getDouble("c2c_montant"));
                ct.setC2c_date(FonctionUtils.reverseSqlDate(res.getString("fic_debutperiode")));
                liste.add(ct);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
    public static ArrayList<Appro_caisse> getListeAppro(String nomagent, String debut, String fin) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from appro_caisse join caisse_journalier on caisse_journalier.cj_id = appro_caisse.cj_id where ag_id = (select ag_id from agent where ag_nom = ?) and cj_date >= ? and cj_date <= ?");
            ps.setString(1, nomagent);
            ps.setString(2, FonctionUtils.getSQLDateFromDateShort(debut));
            ps.setString(3, FonctionUtils.getSQLDateFromDateShort(fin));
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Appro_caisse ac = new Appro_caisse(res.getInt("apc_id"), res.getInt("cj_id"), res.getString("apc_heure"), res.getString("apc_date"), res.getDouble("apc_montant"));
                liste.add(ac);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
}
