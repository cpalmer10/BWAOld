/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.model;

import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Chris
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "edu.wctc.cdp_bookWebApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }
    
    public int deletebyId(String id){
        Integer iId = Integer.parseInt(id);
        String jpql = "delete from Author a where a.authorId = :id";
        Query q = this.getEntityManager().createQuery(jpql);
        q.setParameter("iId",id);
        return q.executeUpdate();
        
    }
    
    public void add(String name){
        Author a = new Author();
        a.setAuthorName(name);
        Date createDate = new Date();
        a.setDateAdded(createDate);
        this.create(a);
        
    }
    
    public void update(String id, String name){
        Author a = this.find(Integer.parseInt(id));
        a.setAuthorName(name);
        this.edit(a);
    }
    
    
    
    
}
