/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.model;

import edu.wctc.cdp.bookwebapp.db.accessor.DBAccessor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class AuthorDao {
    private DBAccessor db;
    private String driverClass;
    private String url;
    private String username;
    private String password;
    
    
    //validate me

    
    public AuthorDao(DBAccessor db, String driverClass, String url, String username, String password){
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Author> getAuthorList(String tableName) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        
        List<Author> authorList = new ArrayList<>();
        db.openConnection(driverClass, url, username, password);
        
        List<Map<String, Object>> rawData = db.findRecords(tableName, true);
        
        for(Map<String, Object> recData : rawData) {
            Author author = new Author();
            Object objAuthorId = recData.get("author_id");
            
        }
        
        
        return authorList;     
    }

    public DBAccessor getDb() {
        return db;
    }

    public void setDb(DBAccessor db) {
        this.db = db;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
