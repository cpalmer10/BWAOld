/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.model;



import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;
import edu.wctc.cdp.bookwebapp.db.accessor.DBAccessor;


/**
 *
 * @author Chris
 */
public class ConnPoolAuthorDao implements AuthorDaoInterface {
   private DataSource ds;
   private DBAccessor db;
  

    public ConnPoolAuthorDao(DataSource ds, DBAccessor db) {
        this.ds = ds;
        this.db = db;
    }
    
    @Override
    public List<Author> getAuthorList(String tableName) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        
        List<Author> authorList = new ArrayList<>();
        db.openConnection(ds);
        
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
        db.openConnection(ds);                     
        db.deleteRecords("author", "author_id", authorID);
        db.closeConnection();
    }

//    @Override
//    public void updateAuthor(String tableName, List<String> columnNames, List columnValues, String whereField, Object whereValue) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
//        db.openConnection(driverClass, url, username, password);        
//        db.updateRecords(tableName, columnNames, columnValues, whereField, whereValue);
//        db.closeConnection();
//        
//    }
    
    @Override
    public void updateAuthor(String authorName, Integer authorID) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        db.openConnection(ds);    
        db.updateRecords("author", Arrays.asList("author_name"), Arrays.asList(authorName),"author_id", authorID);
        db.closeConnection();
        
    }

    @Override
    public void insertAuthor(String name) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        db.openConnection(ds);        
        db.insertRecord("author", Arrays.asList("author_name", "date_added"), Arrays.asList(name, new java.util.Date()));
        db.closeConnection();
    }
    
    

    @Override
    public DBAccessor getDb() {
        return null;
    }

    @Override
    public void setDb(DBAccessor db) {
        //this.db = db;
    }

    @Override
    public String getDriverClass() {
        return null;
    }

    @Override
    public void setDriverClass(String driverClass) {
        //this.driverClass = driverClass;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void setUrl(String url) {
        //this.url = url;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void setUsername(String username) {
        //this.username = username;
    }

    @Override
    public String getPassword() {
        return null;
        //return password;
    }

    @Override
    public void setPassword(String password) {
        //this.password = password;
    }

    // Test harness - not used in production
    // Uses a ad-hoc connection pool and DataSource object to test the code
    public static void main(String[] args) throws Exception {
        
        // Sets up the connection pool and assigns it a JNDI name
        NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder() {

            @Override
            public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment) throws NamingException {
                return new InitialContextFactory() {

                    @Override
                    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
                        return new InitialContext(){

                            private Hashtable<String, DataSource> dataSources = new Hashtable<>();

                            @Override
                            public Object lookup(String name) throws NamingException {

                                if (dataSources.isEmpty()) { //init datasources
                                    MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
                                    ds.setURL("jdbc:mysql://localhost:3306/book");
                                    ds.setUser("root");
                                    ds.setPassword("admin");
                                    // Association a JNDI name with the DataSource for our Database
                                    dataSources.put("jdbc/book", ds);

                                    //add more datasources to the list as necessary
                                }

                                if (dataSources.containsKey(name)) {
                                    return dataSources.get(name);
                                }

                                throw new NamingException("Unable to find datasource: "+name);
                            }
                        };
                    }

                };
            }

        });
        
        // Find the connection pool and create the DataSource     
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/book");

        //AuthorDao dao = new ConnPoolAuthorDao(ds, new MySqlDBAccessor());

        //List<Author> authors = dao.getAllAuthors();
        //for (Author a : authors) {
            //System.out.println(a);
        //}
    }
}