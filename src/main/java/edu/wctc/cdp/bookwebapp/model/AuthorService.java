/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.model;

import edu.wctc.cdp.bookwebapp.db.accessor.DBAccessor;
import edu.wctc.cdp.bookwebapp.db.accessor.MySqlDBAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Palmer
 */
public class AuthorService {
       
                            
    public List<Map<String, Object>> getAllAuthors() throws Exception {
        DBAccessor db = new MySqlDBAccessor();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");        
        
        return db.findAllRecords("author");                
    }
    
    public void updateAuthor() {
        
    }
    
    public void deleteAuthor() {
        
    }
    
    public void addAuthor() {
        
    }
    
}
