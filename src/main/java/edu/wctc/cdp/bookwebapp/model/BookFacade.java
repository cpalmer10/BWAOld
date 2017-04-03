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
 * @author Palmer
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "edu.wctc.cdp_bookWebApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
    public int deleteById(String id){
        Integer iId = Integer.parseInt(id);
        String jpql = "delete from Book b where b.bookId = :id";
        Query q = this.getEntityManager().createQuery(jpql);
        q.setParameter("id", iId);
        return q.executeUpdate();    
    }
    
    public void addNew(String title, String isbn, String authorName){        
        Book b = new Book();        
        b.setIsbn(isbn);
        b.setTitle(title);
        
        Author authorEntity = new Author();
        authorEntity.setAuthorName(authorName);
        Date dateAdded = new Date();
        authorEntity.setDateAdded(dateAdded);
        
                        
        b.setAuthorEntity(authorEntity);
        this.create(b);    
    }
    
    public void update(String id, String title, String isbn, String authorId){
        String jpql = "Update Book b Set b.title = :title, b.isbn = :isbn, b.authorId = :authorId Where b.bookId = :id";
        Query q = this.getEntityManager().createQuery(jpql);
        q.setParameter("id", Integer.parseInt(id));
        q.setParameter("isbn", isbn);
        q.setParameter("title", title);
        q.setParameter("authorId", authorId);
        q.executeUpdate();
    }
    
}
