/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Category;
import entities.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import models.UserFacade;

/**
 *
 * @author jaouhari
 */
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
        user = new User();
    }
    
    public void onload(){
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (id != null) {
            // pre-load form default values if id given in url (edit form)
            user = findById(Integer.parseInt(id));
        }
    }

    public User findById(int id) {
        return userFacade.findById(id);
    }

    public User findById() {
        return findById(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
    }

    @EJB
    private UserFacade userFacade;

    private User user;

    public String insert() {
        userFacade.create(user);
        user = new User();
        return "index";
    }

    public String update() {
        userFacade.edit(user);
        int id = user.getId();
        return "/user?faces-redirect=true&id=" + id;
    }

    public String grantAdmin() {
        user.setRole(1);
        return update();
    }

    public String revokeAdmin() {
        user.setRole(0);
        return update();
    }

}
