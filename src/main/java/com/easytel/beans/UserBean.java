/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.beans;

/**
 *
 * @author andri
 */


import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.easytel.dao.LoginDAO;
import com.easytel.model.User;
import com.easytel.util.SessionUtils;
import java.util.List;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
        private User utilisateur = new User();
        private User currUser;
	private String msg;
        private boolean droitupdate;
        
        private String login;
        private String alias;
        private String pass;
        private boolean hasdroit;

        public User getUtilisateur() {
            return utilisateur;
        }

        public void setUtilisateur(User utilisateur) {
            this.utilisateur = utilisateur;
        }

        public User getCurrUser() {
            return currUser;
        }

        public void setCurrUser(User currUser) {
            this.currUser = currUser;
        }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

        public boolean isDroitupdate() {
            return droitupdate;
        }

        public void setDroitupdate(boolean droitupdate) {
            this.droitupdate = droitupdate;
        }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isHasdroit() {
        return hasdroit;
    }

    public void setHasdroit(boolean hasdroit) {
        this.hasdroit = hasdroit;
    }

	//validate login
	public String validateUsernamePassword() throws ClassNotFoundException {
		utilisateur = LoginDAO.validate(utilisateur);
		if (utilisateur.isConnected()) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("iduser", utilisateur.getUsr_id());
			session.setAttribute("username", utilisateur.getUsr_login());
			session.setAttribute("useralias", utilisateur.getUsr_login());
			session.setAttribute("update_usr", utilisateur.isUsr_droitupdate());
			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Nom d'utilisateur et/ou mot de passe incorrect",
							"Entrez un nom d'utilisateur et mot de passe valide"));
			return "login";
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}
        
        public List<User> getListeUser() {
            return LoginDAO.getAllUsers();
        }
        
        public void deleteCurrent() {
            LoginDAO.deleteUser(currUser);
        }
        
        public void updateCurrent() {
            if(currUser != null) {
                if(currUser.getUsr_newpass().equals("")) {
                    LoginDAO.updateBasicInfoUser(currUser);
                } else {
                    LoginDAO.updateUser(currUser);
                }
            }
        }
        
        public void addNew() {
            User u = new User(login, alias, pass, hasdroit);
            LoginDAO.addUser(u);
        }
        
        public void saveCurrentData() throws ClassNotFoundException {
            HttpSession session = SessionUtils.getSession();
            utilisateur.setUsr_id((int) session.getAttribute("iduser"));
            boolean ok = true;
            if("".equals(utilisateur.getUsr_pass())) {
                ok = false;
                FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Mot de passe vide",
                                "Entrez un mot de passe SVP"));
            } else if(!LoginDAO.checkCurrent(utilisateur)) {
                ok = false;
                FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Mot de passe incorrect",
                                "Entrez un mot mot de passe valide SVP"));
            } else if(!utilisateur.getUsr_newpass().equals("")) {
                if(utilisateur.getUsr_newpass().equals(utilisateur.getUsr_confirmpass())) {
                    utilisateur.setUsr_pass(utilisateur.getUsr_newpass());
                } else {
                    ok = false;
                    FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Mot de passe de confirmation incohérent",
                                    "Entrez un mot de passe de confirmation qui correspont"));
                }
            }
            if(ok) {
                if(LoginDAO.updateUser(utilisateur)) {
                    FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Enregistré",
                                    "Informations mis à jour avec succès"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Echec de l'enregistrement",
                                    "Les informations n'ont pas pu être enregistrées"));
                }
            }
        }
}
