/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Category;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import models.CategoryFacade;

/**
 *
 * @author jaouhari
 */
@Named(value = "categoryController")
@SessionScoped
public class CategoryController implements Serializable {

    /**
     * Creates a new instance of CategoryController
     */
    public CategoryController() {
        category = new Category();
    }

    public void onload() {
        category = new Category();
        // pre-load form default values if id given in url (edit form)
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (id != null) {
            category = findById(Integer.parseInt(id));
        }
    }

    @EJB
    private CategoryFacade categoryFacade;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category findById(int id) {
        return categoryFacade.findById(id);
    }

    public Category findById() {
        return findById(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
    }

    public List<Category> findAll() {
        return categoryFacade.findAll();
    }

    public String insert() {
        categoryFacade.create(category);
        int id = category.getId();
        return "categories?faces-redirect=true";
    }

    public String update() {
        categoryFacade.edit(category);
        int id = category.getId();
        return "categories?faces-redirect=true";
    }
    
    public String delete(Category category){
        categoryFacade.remove(category);
        // TODO: show error alert in case of reference error in db
        return "categories?faces-redirect=true";
    }
}
