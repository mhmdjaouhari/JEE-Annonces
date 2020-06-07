/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import models.UserFacade;

/**
 *
 * @author jaouhari
 */
@Named(value = "login")
@RequestScoped
public class Login implements Serializable {

    /**
     * Creates a new instance of Login
     */
    public Login() {
    }

    @EJB
    private UserFacade userFacade;

    private String password;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String login() {
        User user;
        if (email != null) {
            user = userFacade.findAllByEmail(email);
            if (user.getPassword().equals(password)) {
                // LOGIN SUCCESS
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
                return "index";
            } else {
                // LOGIN FAILURE
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Incorrect Username and Passowrd",
                                "Please enter correct username and Password"));
                return "login";
            }
        }
        return "login";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

}
