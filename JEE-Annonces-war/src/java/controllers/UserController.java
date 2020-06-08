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

}
