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
        product.setCategoryId(new Category());
        product.setUserId(new User());
    }

    @PostConstruct
    public void init() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (user != null) {
            product.getUserId().setId(user.getId());
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
        product = new Product();
        return "/index";
    }
}
