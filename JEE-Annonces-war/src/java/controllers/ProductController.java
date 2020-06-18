/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Category;
import entities.Product;
import entities.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import models.ProductFacade;

/**
 *
 * @author jaouhari
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable {

    /**
     * Creates a new instance of ProductController
     */
    public ProductController() {
        product = new Product();
    }

    @PostConstruct
    public void init() {
    }

    public void onload() {
        product = new Product();
        product.setCategoryId(new Category());
        product.setUserId(new User());
        // set the id of the user who's logged-in as the userid in the Product to be inserted
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (user != null) {
            product.getUserId().setId(user.getId());
        }
        // pre-load form default values if id given in url (edit form)
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (id != null) {
            product = findById(Integer.parseInt(id));
        }
    }

    @EJB
    private ProductFacade productFacade;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> findAll() {
        return productFacade.findAll();
    }

    public Product findById(int id) {
        return productFacade.findById(id);
    }

    public Product findById() {
        return findById(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
    }

    public String insert() {
        productFacade.create(product);
        int id = product.getId();
        return "/product?faces-redirect=true&id=" + id;
    }

    public String update() {
        productFacade.edit(product);
        int id = product.getId();
        return "/product?faces-redirect=true&id=" + id;
    }
    
    public String delete(Product product){
        productFacade.remove(product);
        return "index?faces-redirect=true";
    }
}
