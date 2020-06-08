/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jaouhari
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "JEE-Annonces-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User findAllByEmail(String email) {
        return (User)em.createNamedQuery("User.findByEmail")
            .setParameter("email", email)
            .getSingleResult();
    }
    
    public User findById(int id) {
        return (User) em.createNamedQuery("User.findById")
                .setParameter("id", id)
                .getSingleResult();
    }
    
}
