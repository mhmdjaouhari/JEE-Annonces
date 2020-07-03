/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Category;
import entities.Image;
import entities.Product;
import entities.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import models.ImageFacade;
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
        product.setCategory(new Category());
        product.setUser(new User());
        // set the id of the user who's logged-in as the userid in the Product to be inserted
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (user != null) {
            product.getUser().setId(user.getId());
        }
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (id != null) {
            // pre-load form default values if id given in url (edit form)
            product = findById(Integer.parseInt(id));            
        } else {
            // set user location as default location for the product
            product.setLocation(user.getLocation());
        }
    }

    @EJB
    private ProductFacade productFacade;

    @EJB
    private ImageFacade imageFacade;

    private Product product;
    private Part file;
    private HashSet<String> imageFileNames = new HashSet();
    private String searchString;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Part getFile() {
        return null; // important to allow upload of multiple files
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<Product> findAll() {
        List<Product> productList = productFacade.findAll();
        Collections.reverse(productList);
        return productList;
    }

    public Product findById(int id) {
        return productFacade.findById(id);
    }

    public Product findById() {
        return findById(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String insert() {
        // insert product
        productFacade.create(product);
        int id = product.getId();
        // insert product images
        for (String filePath : imageFileNames) {
            Image image = new Image();
            image.setUrl(filePath);
            image.setProduct(product);
            imageFacade.create(image);
        }
        return "/product?faces-redirect=true&id=" + id;
    }

    public String update() {
        product.setCategory(new Category(product.getCategory().getId()));
        productFacade.edit(product);
        int id = product.getId();
        return "/product?faces-redirect=true&id=" + id;
    }

    public String delete(Product product) {
        for (Image image : product.getImageList()) {
            // remove image from db
            imageFacade.remove(image);
        }
        // remove product
        productFacade.remove(product);
        return "/index?faces-redirect=true";
    }

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    public void uploadImages() {
        try {
            for (Part file : getAllParts(file)) {
                // upload the file
                InputStream in = file.getInputStream();
                String fileName = Instant.now().getEpochSecond() + "-" + file.getSubmittedFileName();
                File f = new File("/home/jaouhari/NetBeansProjects/JEE-Annonces/JEE-Annonces-war/web/resources/images/uploaded/"
                        + fileName);
                f.createNewFile();
                FileOutputStream out = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.close();
                in.close();
                // memorize filenames (unique)
                imageFileNames.add(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    public String search(){
        String currentSearchString = searchString;
        searchString = "";
        return "/search?faces-redirect=true&amp;q=" + currentSearchString;
    }

    public String getProductString(Product p) {
        return p.getName() + " " + p.getLocation() + " " + p.getDescription();
    }

    public List<Product> getSearchResults(String searchString) {
        List<Product> searchResults = findAll();
        Collections.sort(searchResults, (Product o1, Product o2) -> {
            int ratio1 = FuzzySearch.tokenSetRatio(getProductString(o1), searchString);
            int ratio2 = FuzzySearch.tokenSetRatio(getProductString(o2), searchString);
            // System.out.println("ratio1 = " + ratio1 + ", ratio2 = " + ratio2);
            return ratio2 - ratio1;
        });
        return searchResults;
    }

}
