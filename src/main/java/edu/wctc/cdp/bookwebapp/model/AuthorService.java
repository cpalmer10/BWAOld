/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Palmer
 */
public class AuthorService {
    private AuthorDaoInterface dao;

    public AuthorService(AuthorDaoInterface dao) {
        this.dao = dao;
    }
                                   
    public List<Author> getAllAuthors(String tableName) throws Exception {
        return dao.getAuthorList(tableName);        
    }
    
     public void updateAuthor(String authorName, Integer authorID) throws ClassNotFoundException, SQLException, Exception {
      dao.updateAuthor(authorName, authorID);
    }
    
    public void deleteAuthor(Integer authorID) throws ClassNotFoundException, Exception {
        dao.deleteAuthor(authorID);
    }
    
    public void addAuthor(String name) throws Exception {
        dao.insertAuthor(name);        
    }
    
    
    public AuthorDaoInterface getDao() {
        return dao;
    }

    public void setDao(AuthorDaoInterface dao) {
        this.dao = dao;
    }    
}
