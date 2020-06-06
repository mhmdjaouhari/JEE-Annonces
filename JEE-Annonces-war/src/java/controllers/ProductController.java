/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Product;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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

    public String insert() {
        productFacade.create(product);
        product = new Product();
        return "index";
    }
}
