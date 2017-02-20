/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.model;

import edu.wctc.cdp.bookwebapp.db.accessor.DBAccessor;
import edu.wctc.cdp.bookwebapp.db.accessor.MySqlDBAccessor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class AuthorDao implements AuthorDaoInterface {
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

    @Override
    public List<Author> getAuthorList(String tableName) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        
        List<Author> authorList = new ArrayList<>();
        db.openConnection(driverClass, url, username, password);
        
        List<Map<String, Object>> rawData = db.findAllRecords(tableName);
        db.closeConnection();
        for(Map<String, Object> recData : rawData) {
            Author author = new Author();
            Object objAuthorId = recData.get("author_id");
            author.setAuthorId((Integer)recData.get("author_id"));
            Object objName = recData.get("author_name");
            String name = objName != null ? objName.toString() : "";
            author.setAuthorName(name);
            Object objDate = recData.get("date_added");
            Date dateAdded = objDate != null ? (Date)objDate : null;
            author.setDateAdded(dateAdded);            
            authorList.add(author);
        }
        
        
        return authorList;     
    }
    
    @Override
    public void deleteAuthor(Integer authorID) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {       
        db.openConnection(driverClass, url, username, password);                        
        db.deleteRecords("author", "author_id", authorID);
        db.closeConnection();
    }

    @Override
    public void updateAuthor(String tableName, List<String> columnNames, List columnValues, String whereField, Object whereValue) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        db.openConnection(driverClass, url, username, password);
        
        db.updateRecords(tableName, columnNames, columnValues, whereField, whereValue);
        db.closeConnection();
        
    }

    @Override
    public void insertAuthor(String name) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        db.openConnection(driverClass, url, username, password);        
        db.insertRecord("author", Arrays.asList("author_name", "date_added"), Arrays.asList(name, new java.util.Date()));
        db.closeConnection();
    }
    
    

    @Override
    public DBAccessor getDb() {
        return db;
    }

    @Override
    public void setDb(DBAccessor db) {
        this.db = db;
    }

    @Override
    public String getDriverClass() {
        return driverClass;
    }

    @Override
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        AuthorDaoInterface dao = new AuthorDao(new MySqlDBAccessor(),"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/book", "root","admin");
        
        List<Author> authors = dao.getAuthorList("author");
        
        for(Author a : authors) {
            System.out.println(a);
        }
    }
    
    
}
