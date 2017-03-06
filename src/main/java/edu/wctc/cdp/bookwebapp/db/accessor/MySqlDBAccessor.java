/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.db.accessor;

import edu.wctc.cdp.bookwebapp.constants.ExceptionConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.sql.DataSource;

/**
 *
 * @author Chris
 */
public class MySqlDBAccessor implements DBAccessor {
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private PreparedStatement prepStatement;
    
    public MySqlDBAccessor() {
    }
    
    //consider creating custom exception handlers
    @Override
    public void openConnection(String driverClass, String url, String username, String password) throws ClassNotFoundException, SQLException{
        //add in validation
        driverClass = (driverClass == null) ? ExceptionConstants.DRIVER_NULL_ERR : driverClass;
        url = (url == null || url.length() == 0) ? ExceptionConstants.URL_NULL_ERR : url;
        username = (username == null) ? ExceptionConstants.USER_NULL_ERR : username;
        password = (password == null) ? ExceptionConstants.PASSWORD_NULL_ERR : password;
        
        Class.forName(driverClass);
        connection = DriverManager.getConnection(url, username, password);
    }
    
     /**
     * Open a connection using a connection pool configured on server.
     *
     * @param ds - a reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
     * @throws SQLException - if ds cannot be established
     */
    @Override
    public final void openConnection(DataSource ds) throws SQLException {
        connection = ds.getConnection();
    }
    
    @Override
    public void closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
        }        
    }
    
    @Override
    public List<Map<String,Object>> findRecordsFor(String tableName, int maxRecords) throws SQLException{
        
        String sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        
        List<Map<String,Object>> records = new ArrayList<>();  
        metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while (resultSet.next()) {
            Map<String, Object> record = new LinkedHashMap<>();
            for (int i = 0; i < columnCount; i++){
                String columnName = metaData.getColumnName(i+1);
                Object columnData = resultSet.getObject(columnName);
                record.put(columnName, columnData);                
            }
            records.add(record);
        }        
        return records;
    }
    
    @Override
    public List<Map<String,Object>> findAllRecords(String tableName) throws SQLException {       
        
        String sql = "SELECT * FROM " + tableName;        
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        
        List<Map<String,Object>> records = new ArrayList<>();  
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        
        while (resultSet.next()) {
            Map<String, Object> record = new LinkedHashMap<>();
            for (int i = 0; i < columnCount; i++){
                String columnName = rsmd.getColumnName(i+1);
                Object columnData = resultSet.getObject(columnName);
                record.put(columnName, columnData);                
            }
            records.add(record);
        }        
        return records;
    }
    
    @Override
    public List findRecords(String sqlString) throws SQLException, Exception {
       statement = null;
       resultSet = null;
       metaData = null;     
       final List recordsList = new ArrayList();
       Map record = null;
       
       try {
           statement = connection.createStatement();
           resultSet = statement.executeQuery(sqlString);
           metaData = resultSet.getMetaData();
           final int fields = metaData.getColumnCount();
           
           while (resultSet.next()) {
               record = new HashMap();
               for(int i = 1; i <= fields; i++){
                   try {
                       record.put(metaData.getColumnName(i), resultSet.getObject(i));
                   } catch (NullPointerException npe) {
                       // ITS JUST A NULL POINTER. ITS NBD.
                   }
               } 
           }
           recordsList.add(record);
       } catch (SQLException e) {
           throw e;          
       } catch (Exception e) {
           throw e;
       } 
       return recordsList;
    }
    
    @Override
    public Map getRecordByID(String tableName, String primaryKeyField, Object keyValue) throws SQLException, Exception {
        statement = null;
        resultSet = null;
        metaData = null;
        final Map record = new HashMap();
        
        try {
            statement = connection.createStatement();
            String sqlKV;
            
            if (keyValue instanceof String) {
                sqlKV = "= '" + keyValue + "'";
            } else {
                sqlKV = "=" + keyValue;
            }
            
            final String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyField + sqlKV;
            resultSet = statement.executeQuery(sql);
            metaData = resultSet.getMetaData();
            metaData.getColumnCount();
            final int fields = metaData.getColumnCount();
            
            while (resultSet.next()) {
                for (int i = 1; i <= fields; i++) {
                    record.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
            }                                    
        } catch (SQLException e) {
           throw e;          
        } catch (Exception e) {
           throw e;
        } 
        return record;        
    }

    @Override
    public boolean insertRecord(String tableName, List<String> columnNames, List columnValues) throws SQLException, Exception {
        prepStatement = null;
        int recordsUpdated = 0;
        
        try {
            prepStatement = buildInsertStatement(connection, tableName, columnNames);
            
            final Iterator i = columnValues.iterator();
            int index = 1;
            
            while(i.hasNext()) {
                final Object object = i.next();
                prepStatement.setObject(index++, object);                
            }
            recordsUpdated = prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }         
        if (recordsUpdated == 1) {
            return true;
        } else {
            return false;
        }                        
    }
    
    @Override
    public int updateRecords(String tableName, List<String> columnNames, List columnValues, String whereField, Object whereValue) throws SQLException, Exception {
        prepStatement = null;
        int recordsUpdated = 0;
        
        try {
            prepStatement = buildUpdateStatement(connection, tableName, columnNames, whereField);
            
            final Iterator i = columnValues.iterator();
            int index = 1;
            Object object = null;
            
            while (i.hasNext()) {
                object = i.next();
                prepStatement.setObject(index, object);
                index++;                
            }
            prepStatement.setObject(index, whereValue);
            
            recordsUpdated = prepStatement.executeUpdate();
            
        }catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } 
        return recordsUpdated;
    }
    
    @Override
    public int deleteRecords(String tableName, String whereField, Object whereValue) throws SQLException, Exception {
        prepStatement = null;
        int recordsDeleted = 0;        
        try {
            prepStatement = buildDeleteStatement(connection, tableName, whereField);

            if (whereField != null) {
                prepStatement.setObject(1, whereValue);
            }
            
            recordsDeleted = prepStatement.executeUpdate();
            
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } 
        return recordsDeleted;
    }
    
    private PreparedStatement buildInsertStatement(Connection connection, String tableName, List columnNames) throws SQLException {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        sql.append(tableName).append(" ");
        
        StringJoiner stringJoin = new StringJoiner(",","(",")");
        final Iterator i = columnNames.iterator();
        
        while(i.hasNext()) {
            stringJoin.add((String) i.next());            
        }
        sql.append(stringJoin.toString());
        
        sql.append(" VALUES ");
        stringJoin = new StringJoiner(",","(",")");
        for (Object col : columnNames) {
            stringJoin.add("?");            
        }
        sql.append(stringJoin.toString());
        
        
        return connection.prepareStatement(sql.toString());        
    }
    
    private PreparedStatement buildUpdateStatement(Connection connection, String tableName, List columnNames, String whereField) throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        
        final Iterator i = columnNames.iterator();
        while(i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ? ");
        final String finalSQL = sql.toString();
        
        
        return connection.prepareStatement(finalSQL);        
    }
    
    private PreparedStatement buildDeleteStatement(Connection connection, String tableName, String whereField) throws SQLException {
        final StringBuffer sql = new StringBuffer("DELETE FROM ");
        sql.append(tableName);
        
        if(whereField != null) {
            sql.append(" WHERE ");
            (sql.append(whereField)).append(" = ? ");
        }
        final String finalSQL = sql.toString();
        
        return connection.prepareStatement(finalSQL);
    }
    
    public static void main(String[] args) throws Exception {
        DBAccessor db = new MySqlDBAccessor();
        
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");                    
        List<Map<String,Object>> records = db.findAllRecords("author");
        db.closeConnection();
        
        for(Map<String,Object> record : records) {
            System.out.println(record);
        }
    }    
}
