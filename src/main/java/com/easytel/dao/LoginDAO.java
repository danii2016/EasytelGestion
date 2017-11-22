/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.dao;

import com.easytel.model.User;
import com.easytel.util.dataConnect;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
/**
 *
 * @author andri
 */
public class LoginDAO {
    
    public static User validate(User usr) throws ClassNotFoundException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataConnect.getConnection();
			ps = con.prepareStatement("Select * from user where (usr_login = ? or usr_alias = ?) and usr_pass = ?");
			ps.setString(1, usr.getUsr_login());
			ps.setString(2, usr.getUsr_login());
			ps.setString(3, encryptPassword(usr.getUsr_pass()));

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
                            usr.setConnected(true);
                            usr.setUsr_id(rs.getInt("usr_id"));
                            usr.setUsr_alias(rs.getString("usr_alias"));
                            usr.setUsr_login(rs.getString("usr_login"));
                            usr.setUsr_droitupdate(rs.getInt("usr_droitupdate") == 1);
                            return usr;
			}
		} catch (SQLException ex) {
			System.out.println("Login error --> " + ex.getMessage());
			return usr;
		} finally {
			dataConnect.close(con);
		}
		return usr;
    }
    
    public static boolean checkCurrent(User usr) throws ClassNotFoundException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataConnect.getConnection();
			ps = con.prepareStatement("Select * from user where usr_id = ? and usr_pass = ?");
			ps.setInt(1, usr.getUsr_id());
			ps.setString(2, encryptPassword(usr.getUsr_pass()));
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
                            return true;
			}
		} catch (SQLException ex) {
			System.out.println("Check user error --> " + ex.getMessage());
			return false;
		} finally {
			dataConnect.close(con);
		}
		return false;
    }
    
    public static boolean updateUser(User u) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
                con = dataConnect.getConnection();
                ps = con.prepareStatement("update user set usr_login = ?, usr_alias = ?, usr_pass = ?, usr_droitupdate = ? where usr_id = ?");
                ps.setString(1, u.getUsr_login());
                ps.setString(2, u.getUsr_alias());
                ps.setString(3, encryptPassword(u.getUsr_pass()));
                ps.setInt(4, u.isUsr_droitupdate() ? 1 : 0);
                ps.setInt(5, u.getUsr_id());
                if(ps.executeUpdate() > 0) {
                    ps.close();
                    con.close();
                    return true;
                }
                ps.close();
                con.close();
        } catch(ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return false;
    }
    
    public static boolean updateBasicInfoUser(User u) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
                con = dataConnect.getConnection();
                ps = con.prepareStatement("update user set usr_login = ?, usr_alias = ?, usr_droitupdate = ? where usr_id = ?");
                ps.setString(1, u.getUsr_login());
                ps.setString(2, u.getUsr_alias());
                ps.setInt(3, u.isUsr_droitupdate() ? 1 : 0);
                ps.setInt(4, u.getUsr_id());
                if(ps.executeUpdate() > 0) {
                    ps.close();
                    con.close();
                    return true;
                }
                ps.close();
                con.close();
        } catch(ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return false;
    }
    
    public static boolean deleteUser(User u) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
                con = dataConnect.getConnection();
                ps = con.prepareStatement("delete from user where usr_id = ?");
                ps.setInt(5, u.getUsr_id());
                if(ps.executeUpdate() > 0) {
                    ps.close();
                    con.close();
                    return true;
                }
                ps.close();
                con.close();
        } catch(ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return false;
    }
    
    public static ArrayList<User> getAllUsers() {
        ArrayList liste = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataConnect.getConnection();
            ps = con.prepareStatement("Select * from user");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getString("usr_login"), rs.getString("usr_alias"), rs.getString("usr_pass"), rs.getInt("usr_droitupdate") == 1);
                u.setUsr_id(rs.getInt("usr_id"));
                liste.add(u);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erreur de récupérations des utilisateurs --> " + ex.getMessage());
        }
        return liste;
    }
    
    public static boolean addUser(User u) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
                con = dataConnect.getConnection();
                ps = con.prepareStatement("insert into user(usr_login, usr_alias, usr_pass, usr_droitupdate) values(?, ?, ?, ?)");
                ps.setString(1, u.getUsr_login());
                ps.setString(2, u.getUsr_alias());
                ps.setString(3, encryptPassword(u.getUsr_pass()));
                ps.setInt(4, u.isUsr_droitupdate() ? 1 : 0);
                if(ps.executeUpdate() > 0) {
                    ps.close();
                    con.close();
                    return true;
                }
                ps.close();
                con.close();
        } catch(ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return false;
    }
    
    private static String encryptPassword(String password) {
        String sha1 = "";
        password = "*"+password+"+";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.print(e);
        }
        return sha1;
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
