/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.model;

/**
 *
 * @author andri
 */
public class User {
    private int usr_id;
    private String usr_login;
    private String usr_alias;
    private boolean usr_droitupdate;
    private String usr_pass;
    private boolean connected = false;
    private String usr_newpass;
    private String usr_confirmpass;
    
    public User() {
        this.usr_login = this.usr_pass = "";
    }
    public User(String usr_login, String usr_pass) {
        this.usr_login = usr_login;
        this.usr_pass = usr_pass;
    }
    
    public User(String usr_login, String usr_alias, String usr_pass, boolean usr_droitupdate) {
        this.usr_login = usr_login;
        this.usr_alias = usr_alias;
        this.usr_pass = usr_pass;
        this.usr_droitupdate = usr_droitupdate;
    }

    public int getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(int usr_id) {
        this.usr_id = usr_id;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getUsr_login() {
        return usr_login;
    }

    public void setUsr_login(String usr_login) {
        this.usr_login = usr_login;
    }

    public String getUsr_alias() {
        return usr_alias;
    }

    public void setUsr_alias(String usr_alias) {
        this.usr_alias = usr_alias;
    }

    public boolean isUsr_droitupdate() {
        return usr_droitupdate;
    }

    public void setUsr_droitupdate(boolean usr_droitupdate) {
        this.usr_droitupdate = usr_droitupdate;
    }

    public String getUsr_pass() {
        return usr_pass;
    }

    public void setUsr_pass(String usr_pass) {
        this.usr_pass = usr_pass;
    }

    public String getUsr_newpass() {
        return usr_newpass;
    }

    public void setUsr_newpass(String usr_newpass) {
        this.usr_newpass = usr_newpass;
    }

    public String getUsr_confirmpass() {
        return usr_confirmpass;
    }

    public void setUsr_confirmpass(String usr_confirmpass) {
        this.usr_confirmpass = usr_confirmpass;
    }
    
    @Override
    public String toString() {
        return "Id : "+usr_id+"\t | Login : "+usr_login+" \t| alias : "+usr_alias+
                "\t| password: "+usr_pass+" \t | New password : "+usr_newpass;
    }
}
