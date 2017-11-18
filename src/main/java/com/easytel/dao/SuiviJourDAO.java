/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.dao;

/**
 *
 * @author andri
 */
import com.easytel.model.*;
import com.easytel.util.FonctionUtils;
import com.easytel.util.dataConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class SuiviJourDAO {
    
    public static Caisse_journalier getCaisseDuJour(String nom, String date) {
        Caisse_journalier caisse = null;
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select caisse_journalier.*, fic_id, (select sum(apc_montant) from appro_caisse where cj_id = caisse_journalier.cj_id) as cj_approcaisse from caisse_journalier join agent on caisse_journalier.ag_id = agent.ag_id join fichier on (fichier.ag_id = agent.ag_id and caisse_journalier.cj_date = fichier.fic_debutperiode) where ag_nom = ? and cj_date = ?");
            ps.setString(1, nom);
            ps.setString(2, FonctionUtils.getSQLDateFromDateShort(date));
            ResultSet res = ps.executeQuery();
            if(res.next()) {
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
                System.out.print(Double.parseDouble("100")+" "+res.getString("cj_totalci") +" "+res.getDouble("cj_commissionci") +" "+ res.getDouble("cj_commissionco"));
                String datej = res.getString("cj_date");
                int id = res.getInt("cj_id"), ag = res.getInt("ag_id"), fic = res.getInt("fic_id");
                caisse = new Caisse_journalier(id, ag, fic, datej, csi, csf, com, dgcs, uvi, uvf, tott, totco, totci, apc);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return caisse;
    }
    
    public static ArrayList<Cash_in> getListeCashIn(int fic_id) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from cash_in where fic_id = ?");
            ps.setInt(1, fic_id);
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Cash_in ci = new Cash_in(res.getInt("ci_id"), res.getInt("fic_id"), res.getString("ci_heure"), res.getString("ci_numero"), res.getDouble("ci_montant"));
                liste.add(ci);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
    public static ArrayList<Cash_out> getListeCashOut(int fic_id) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from cash_out where fic_id = ?");
            ps.setInt(1, fic_id);
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Cash_out co = new Cash_out(res.getInt("co_id"), res.getInt("fic_id"), res.getString("co_heure"), res.getString("co_numero"), res.getDouble("co_montant"));
                liste.add(co);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
    public static ArrayList<C2c_transfert> getListeTransfert(int fic_id) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from c2c_transfer where fic_id = ?");
            ps.setInt(1, fic_id);
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                C2c_transfert ct = new C2c_transfert(res.getInt("c2c_id"), res.getInt("fic_id"), res.getString("c2c_heure"), res.getString("c2c_numero"), res.getDouble("c2c_montant"));
                liste.add(ct);
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }
    
    public static ArrayList<Appro_caisse> getListeAppro(int cj_id) {
        ArrayList liste = new ArrayList();
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from appro_caisse where cj_id = ?");
            ps.setInt(1, cj_id);
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
    
    public static boolean saveDegagement(Caisse_journalier cs) {
        try{
            Connection con = dataConnect.getConnection();
            double caisse_final;
            PreparedStatement ps;
            ps = con.prepareStatement("SELECT * from caisse_journalier where cj_id = ?");
            ps.setInt(1, cs.getCj_id());
            ResultSet res = ps.executeQuery();
            if(res.next()) {
                double csfinal = res.getDouble("cj_caissefinal");
                double degcs = res.getDouble("cj_degagementcaisse");
                
                csfinal = csfinal + degcs;
                double csfin = csfinal - cs.getCj_degagementcaisse2();
                System.out.print(degcs+" "+csfinal+" - "+cs.getCj_degagementcaisse2()+" = "+csfin);
                res.close();
                ps.close();
                ps = con.prepareStatement("UPDATE caisse_journalier set cj_degagementcaisse = ?, cj_caissefinal = ? where cj_id = ?");
                ps.setDouble(1, cs.getCj_degagementcaisse2());
                ps.setDouble(2, csfin);
                ps.setInt(3, cs.getCj_id());
                if(ps.executeUpdate() > 0) {
                    return true;
                }
            }
            
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return false;
    }
    
    public static Appro_caisse get_appro(String id) {
        Appro_caisse ap = null;
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from appro_caisse where apc_id = ?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet res = ps.executeQuery();
            if(res.next()) {
               ap = new Appro_caisse(res.getInt("apc_id"), res.getInt("cj_id"), res.getString("apc_heure"), res.getString("apc_date"), res.getDouble("apc_montant"));
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return ap;
    }
    
    public static boolean saveAppro(Appro_caisse appro, String mode) {
        try{
            Connection con = dataConnect.getConnection();
            double last_appro = 0;
            PreparedStatement ps;
            if("insert".equals(mode)) {
                ps = con.prepareStatement("insert into appro_caisse(cj_id, apc_date, apc_heure, apc_montant) values(?, ?, ?, ?)");
                ps.setInt(1, appro.getCj_id());
                ps.setString(2, appro.getApc_date());
                ps.setString(3, appro.getApc_heure());
                ps.setDouble(4, appro.getApc_montant());
            } else {
                ps = con.prepareStatement("select apc_montant from appro_caisse where apc_id = ?");
                ps.setInt(1, appro.getApc_id());
                ResultSet res = ps.executeQuery();
                if(res.next()) {
                    last_appro = res.getDouble("apc_montant");
                }
                res.close();
                ps.close();
                ps = con.prepareStatement("update appro_caisse set apc_heure = ?, apc_montant = ? where apc_id = ?");
                ps.setString(1, appro.getApc_heure());
                ps.setDouble(2, appro.getApc_montant());
                ps.setInt(3, appro.getApc_id());
            }
            
           if(ps.executeUpdate() > 0) {
                ps.close();
                ps = con.prepareStatement("update caisse_journalier set cj_caissefinal = (cj_caissefinal - ? + ?) where cj_id = ?");
                ps.setDouble(1, last_appro);
                ps.setDouble(2, appro.getApc_montant());
                ps.setInt(3, appro.getCj_id());
                ps.execute();
                ps.close();
                con.close();
               return true;
           }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return false;
    }

    public static boolean deleteAppro(Appro_caisse currAppro) {
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps;
            ps = con.prepareStatement("delete from appro_caisse where apc_id = ?");
            ps.setInt(1, currAppro.getApc_id());
            if(ps.executeUpdate() > 0) {
                ps.close();
                ps = con.prepareStatement("update caisse_journalier set cj_caissefinal = (cj_caissefinal - ?) where cj_id = ?");
                ps.setDouble(1, currAppro.getApc_montant());
                ps.setInt(2, currAppro.getCj_id());
                if(ps.executeUpdate() > 0) {
                    ps.close();
                    con.close();
                    return true;
                }
            }
            ps.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return false;
    }
    
    public static ArrayList<Agent> getAgentWithStats(Date date) {
        ArrayList liste = new ArrayList();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
        DateFormat.SHORT,
        DateFormat.SHORT, new Locale("FR","fr"));
        String datej = FonctionUtils.getSQLDateFromDateShort(shortDateFormat.format(date).split(" ")[0]);
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement("Select agent.*, (select cj_commissionci+cj_commissionco from caisse_journalier where ag_id = agent.ag_id and cj_date = ?) as ag_commission,"+
                                                        "(select cj_uvfinal from caisse_journalier where ag_id = agent.ag_id and cj_date = ?) as ag_uv,"+
                                                        "(select cj_caissefinal from caisse_journalier where ag_id = agent.ag_id and cj_date = ?) as ag_caisse"+
                                                        " from agent");
            ps.setString(1, datej);
            ps.setString(2, datej);
            ps.setString(3, datej);
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Agent agent = new Agent(res.getInt("ag_id"), res.getString("ag_nom"), res.getString("ag_numero"), res.getString("ag_adresse"),res.getDouble("ag_uvinitial"), res.getDouble("ag_caisseinitial"));
                agent.setAg_uv(res.getDouble("ag_uv"));
                agent.setAg_caisse(res.getDouble("ag_caisse"));
                agent.setAg_commission(res.getDouble("ag_commission"));
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
