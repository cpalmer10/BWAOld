/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.db.accessor;

import edu.wctc.cdp.bookwebapp.constants.ExceptionConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class MySqlDBAccessor implements DBAccessor {
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    
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
    public List findRecords(String sqlString, boolean closeConnection) throws SQLException, Exception {
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
       } finally {
           try {
               statement.close();
               if (closeConnection) {
                   connection.close();
               }           
           } catch (SQLException e) {
               throw e;
           } 
       }
       return recordsList;
    }
    
    @Override
    public Map getRecordByID(String tableName, String primaryKeyField, Object keyValue, boolean closeConnection) throws SQLException, Exception {
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
        } finally {
            try {
               statement.close();
               if (closeConnection) {
                   connection.close();
               }           
           } catch (SQLException e) {
               throw e;
           } 
        }
        return record;        
    }

//    @Override
//    public boolean insertRecord(String tableName, List colDescriptors, List colValues, boolean closeConnection) throws SQLException, Exception {
//    }
//    @Override
//    public int updateRecords(String tableName, List colDescriptors, List colValues, String whereField, Object whereValue, boolean closeConnection) throws SQLException, Exception {
//    }
//    @Override
//    public int deleteRecords(String tableName, String whereField, Object whereValue, boolean closeConnection) throws SQLException, Exception {
//    }
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }                
    
}
