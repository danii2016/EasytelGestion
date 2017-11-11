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
import com.easytel.model.Fichier;
import com.easytel.model.LigneTableau;
import com.easytel.util.SessionUtils;
import com.easytel.util.dataConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class FichierDAO {
    private static final HttpSession session = SessionUtils.getSession();
    public static ArrayList<Fichier> getUploaded() {
        ArrayList<Fichier> liste = new ArrayList();
        try (Connection con = dataConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement("Select fichier.*, ag_nom, ag_numero from fichier join agent on agent.ag_id = fichier.ag_id order by fic_dateimport desc");
            ResultSet res = ps.executeQuery();
            while(res.next()) {
                Fichier fichier = new Fichier(res.getInt("fic_id"), res.getInt("ag_id"), res.getString("ag_nom"), res.getString("ag_numero"), res.getString("fic_nom"), res.getString("fic_date"), res.getNString("fic_dateimport"));
                liste.add(fichier);
            }
            ps.close();
        }
         catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return liste;
    }

    public static int getInfo(String datefic, String numero) {
        int ok = 1;
        try{
            Connection con = dataConnect.getConnection();
            PreparedStatement ps;
            ResultSet res;
            ps = con.prepareStatement("Select * from agent where ag_numero = ?");
            ps.setString(1, numero);
            res = ps.executeQuery();
            if(res.next()) {
                session.setAttribute("id_agent", res.getInt("ag_id"));
            } else {
                ok = -2;
            }
            ps.close();
            ps = con.prepareStatement("Select * from fichier join agent on agent.ag_id = fichier.ag_id where fic_date = ? and ag_numero = ?");
            ps.setString(1, datefic);
            ps.setString(2, numero);
            res = ps.executeQuery();
            if(res.next()) {
                ok = 0;
            }
            ps.close();
            PreparedStatement ps2 = con.prepareStatement("Select * from fichier join agent on agent.ag_id = fichier.ag_id where fic_dateimport = NOW() and ag_numero = ?");
            ps2.setString(1, numero);
            res = ps2.executeQuery();
            if(res.next() && ok != 0 && ok != -2) {
                ok = 2;
            }
            ps2.close();
            con.close();
        } catch(ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return ok;
    }
    
    public static boolean saveLines(ArrayList<LigneTableau> lignes) {
        boolean saved = true;
        if(lignes.size() < 11) {
            saved = false;
        } else {
            //Début par l'enregistrement de l'importation
            ArrayList<String> erreurs = new ArrayList();
            double totalci = 0, 
                    totalcommissionci = 0, 
                    totalco = 0, 
                    totalcommissionco = 0, 
                    totaldebittransfert = 0,
                    totalcredittransfert = 0;
            try {
                Connection con = dataConnect.getConnection();
                PreparedStatement ps;
                ResultSet res;
                long idfichier = -1;
                String numeroagent = lignes.get(8).getVal4();
                ps = con.prepareStatement("insert into fichier(ag_id, fic_debutperiode, fic_finperiode, fic_nom, fic_date, fic_dateimport) values(?, ?, ?, ?, ?, now())", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, (String) session.getAttribute("id_agent"));
                ps.setString(2, convertToSql(lignes.get(2).getVal4()));
                ps.setString(3, convertToSql(lignes.get(3).getVal4()));
                ps.setString(1, (String) session.getAttribute("fichier"));
                ps.setString(3, convertToSql(lignes.get(5).getVal4()));
                int affectedRows = ps.executeUpdate();
                if(affectedRows != 0) {
                    res = ps.getGeneratedKeys();
                    if(res.next()) {
                        idfichier = res.getLong(1);
                    } else {
                        saved = false;
                        erreurs.add("Impossible de récupérer l'id du fichier ajouté");
                    }
                } else {
                    saved = false;
                    erreurs.add("Impossible d'enregistrer l'importation dans la base");
                }
                ps.close();
                if(saved) {
                    for(int i = 13; i < lignes.size() ; i++ ) {
                        if(saved) {
                            double numero = -1;
                            LigneTableau lt = lignes.get(i);
                            try{
                                numero = Double.parseDouble(lt.getVal1());
                            } catch (NumberFormatException e) {
                                System.out.print("ligne non traité "+lt.getVal1());
                            }
                            if(numero != -1 && !lt.getVal7().toUpperCase().equals("ECHEC")) {
                                switch(lt.getVal5().toLowerCase()) {
                                    case "CASH IN": 
                                        if(lt.getVal10().equals(numeroagent)) {
                                            double montant = -1;
                                            double commission = -1;
                                            try {
                                                montant = Double.parseDouble(lt.getVal12());
                                                commission = Double.parseDouble(lt.getVal14());
                                            } catch(NumberFormatException e) {
                                                System.out.print("Montant "+lt.getVal12()+" ou commission "+lt.getVal14()+" non traité");
                                            }
                                            if(montant != -1 && commission != -1) {
                                                ps = con.prepareStatement("insert into cash_in(fic_id, ci_heure, ci_numero, ci_montant, ci_commission) values (?, ?, ?, ?)");
                                                ps.setLong(1, idfichier);
                                                ps.setString(2, lt.getVal2());
                                                ps.setString(3, lt.getVal11());
                                                ps.setDouble(4, montant);
                                                ps.setDouble(5, commission);
                                                if(!ps.execute()) {
                                                    saved = false;
                                                    erreurs.add("Impossible d'insérer le cash in dans la base");
                                                    totalci += montant;
                                                    totalcommissionci += commission;
                                                }
                                            } else {
                                                erreurs.add("Echec de traitement du montant ou de la commission du cash in : "+lt.toString());
                                            }
                                        } else {
                                            erreurs.add("Numero agent : "+lt.getVal10()+" différent de celui inscrit au début du fichier");
                                        }
                                        break;
                                    case "CASH OUT": 
                                        if(lt.getVal10().equals(numeroagent)) {
                                            double montant = -1;
                                            double commission = -1;
                                            try {
                                                montant = Double.parseDouble(lt.getVal13());
                                                commission = Double.parseDouble(lt.getVal14());
                                            } catch(NumberFormatException e) {
                                                System.out.print("Montant "+lt.getVal13()+" ou commission "+lt.getVal14()+" non traité");
                                            }
                                            if(montant != -1 && commission != -1) {
                                                ps = con.prepareStatement("insert into cash_out(fic_id, co_heure, co_numero, co_montant, co_commission) values(?, ?, ?, ?)");
                                                ps.setLong(1, idfichier);
                                                ps.setString(2, lt.getVal2());
                                                ps.setString(3, lt.getVal11());
                                                ps.setDouble(4, montant);
                                                ps.setDouble(5, commission);
                                                if(!ps.execute()) {
                                                    saved = false;
                                                    erreurs.add("Impossible d'insérer le cash out dans la base");
                                                    totalco += montant;
                                                    totalcommissionco += commission;
                                                }
                                            } else {
                                                erreurs.add("Echec de traitement du montant ou de la commission du cash out : "+lt.toString());
                                            }
                                        } else {
                                            erreurs.add("Numero agent : "+lt.getVal10()+" différent de celui inscrit au début du fichier");
                                        }
                                        break;
                                    case "C2C TRANSFER":
                                        if(lt.getVal10().equals(numeroagent)) {
                                            double montant = -1;
                                            String type = "Crédit";
                                            try {
                                                montant = Double.parseDouble(type.equals("Crédit") ? lt.getVal13() : lt.getVal12());
                                            } catch(NumberFormatException e) {
                                                System.out.print("Montant "+lt.getVal13()+" ou commission "+lt.getVal14()+" non traité");
                                            }
                                            if(montant != -1) {
                                                ps = con.prepareStatement("insert into c2c_transfert(fic_id, c2c_type, c2c_heure, c2c_numero, c2c_montant) values(?, ?, ?, ?, ?)");
                                                ps.setLong(1, idfichier);
                                                ps.setString(2, type);
                                                ps.setString(3, lt.getVal2());
                                                ps.setString(4, lt.getVal11());
                                                ps.setDouble(5, montant);
                                                if(!ps.execute()) {
                                                    saved = false;
                                                    erreurs.add("Impossible d'insérer le C2C transfert dans la base");
                                                    totalcredittransfert += montant;
                                                }
                                            } else {
                                                erreurs.add("Echec de traitement du montant du C2C transfert : "+lt.toString());
                                            }
                                        } else {
                                            erreurs.add("Numero agent : "+lt.getVal10()+" différent de celui inscrit au début du fichier");
                                        }
                                    default: break;
                                }
                            } else {
                                erreurs.add("Ligne echec non traité : "+lt.toString());
                            }
                        } 
                    }
                }
                if(saved) {
                    String datejour = convertToSql(lignes.get(3).getVal4());
                    ps = con.prepareStatement("select * from caisse_journalier where ag_id = ? and cj_date = ?");
                    ps.setString(1, (String) session.getAttribute("id_agent"));
                    ps.setString(2, datejour);
                    res = ps.executeQuery();
                    if(res.next()) {
                        double csinitial = res.getDouble("cj_caisseinitial");
                        double uvinitial = res.getDouble("cj_uvinitial");
                        double totci = res.getDouble("cj_totalci");
                        double totco = res.getDouble("cj_totalco");
                        double tottrans = res.getDouble("cj_totaltransfert");
                        double comci = res.getDouble("cj_commissionci");
                        double comco = res.getDouble("cj_commissionco");
                        double degcs = res.getDouble("cj_degagementcaisse");
                        double appro = 0;
                        res.close();
                        ps.close();
                        ps = con.prepareStatement("select * from appro_caisse where ag_id = ? and apc_date = ?");
                        ps.setString(1, (String) session.getAttribute("id_agent"));
                        ps.setString(2, datejour);
                        res = ps.executeQuery();
                        while(res.next()) {
                            appro += res.getDouble("apc_montant");
                        }
                        res.close();
                        ps.close();
                        totci = totci + totalci;
                        totco = totco + totalco;
                        tottrans = tottrans + totalcredittransfert;
                        comci = comci + totalcommissionci;
                        comco = comco + totalcommissionco;
                        double csfinal = csinitial + totci + appro - totco - degcs;
                        double uvfinal = uvinitial - totci + totco + tottrans;
                        ps = con.prepareStatement("update caisse_journalier set cj_totalci = ?, cj_totalco = ?, cj_totaltransfert = ?,  cj_commissionci = ?, cj_commissionco = ?, cj_caissefinal = ?, cj_ufinal");
                        ps.setDouble(1, totci);
                        ps.setDouble(2, totco);
                        ps.setDouble(3, tottrans);
                        ps.setDouble(4, comci);
                        ps.setDouble(5, comco);
                        ps.setDouble(6, csfinal);
                        ps.setDouble(7, uvfinal);
                        if(!ps.execute()) {
                            saved = false;
                            erreurs.add("La caisse journalière ne peut pas être mis à jour");
                        }
                    } else {
                        ps.close();
                        ps = con.prepareStatement("select * from caisse_journalier where ag_id = ? and cj_date < ? order by cj_date desc");
                        ps.setString(1, (String) session.getAttribute("id_agent"));
                        ps.setString(2, datejour);
                        res = ps.executeQuery();
                        if(res.next()) {
                            double csinitial = res.getDouble("cj_caissefinal");
                            double uvinitial = res.getDouble("cj_uvfinal");
                            res.close();
                            ps.close();
                            double csfinal = csinitial + totalci - totalco;
                            double uvfinal = uvinitial - totalci + totalco + totalcredittransfert;
                            ps = con.prepareStatement("insert into caisse_journalier(ag_id, cj_date, cj_caisseinital, cj_uvinitial, cj_totalci, cj_totalco, cj_commissionci, cj_commissionco, cj_totaltransfert, cj_caissefinal, cj_uvfinal");
                            ps.setString(1, (String) session.getAttribute("id_agent"));
                            ps.setString(2, datejour);
                            ps.setDouble(3, csinitial);
                            ps.setDouble(4, uvinitial);
                            ps.setDouble(5, totalci);
                            ps.setDouble(6, totalco);
                            ps.setDouble(7, totalcommissionci);
                            ps.setDouble(8, totalcommissionco);
                            ps.setDouble(9, totalcredittransfert);
                            ps.setDouble(10, csfinal);
                            ps.setDouble(11, csfinal);
                            if(!ps.execute()) {
                                saved = false;
                                erreurs.add("Echec de la sauvegarde de la caisse journalier dans la base");
                            }
                            ps.close();
                        } else {
                            ps.close();
                            ps = con.prepareStatement("select * from agent where ag_id = ?");
                            ps.setString(1, (String) session.getAttribute("id_agent"));
                            res = ps.executeQuery();
                            if(res.next()) {
                                double csinitial = res.getDouble("ag_caisseinitial");
                                double uvinitial = res.getDouble("ag_uvinitial");
                                res.close();
                                ps.close();
                                double csfinal = csinitial + totalci - totalco;
                                double uvfinal = uvinitial - totalci + totalco + totalcredittransfert;
                                ps = con.prepareStatement("insert into caisse_journalier(ag_id, cj_date, cj_caisseinital, cj_uvinitial, cj_totalci, cj_totalco, cj_commissionci, cj_commissionco, cj_totaltransfert, cj_caissefinal, cj_uvfinal");
                                ps.setString(1, (String) session.getAttribute("id_agent"));
                                ps.setString(2, datejour);
                                ps.setDouble(3, csinitial);
                                ps.setDouble(4, uvinitial);
                                ps.setDouble(5, totalci);
                                ps.setDouble(6, totalco);
                                ps.setDouble(7, totalcommissionci);
                                ps.setDouble(8, totalcommissionco);
                                ps.setDouble(9, totalcredittransfert);
                                ps.setDouble(10, csfinal);
                                ps.setDouble(11, csfinal);
                                if(!ps.execute()) {
                                    saved = false;
                                    erreurs.add("Echec de la sauvegarde de la caisse journalier dans la base");
                                }
                                ps.close();
                            } else {
                                erreurs.add("Impossible de récupérer les valeurs initiales");
                            }
                        }
                    }
                    
                    
                }
            } catch(ClassNotFoundException | SQLException e) {
                System.out.print(e);
                erreurs.add("Des erreurs ont été rencontré lors des insertions des données dans la base");
            }
            session.setAttribute("erreurs", erreurs);
        }
        return saved;
    }
    
    private static String convertToSql(String date) {
        String tabsplit[] = date.split(" ");
        if(tabsplit.length == 2) {
            String datesplit[] = tabsplit[0].split("/");
            return datesplit[2]+"-"+datesplit[1]+"-"+datesplit[0]+" "+tabsplit[1];
        } else {
            String datesplit[] = tabsplit[0].split("/");
            return datesplit[2]+"-"+datesplit[1]+"-"+datesplit[0];
        }
    }
}
