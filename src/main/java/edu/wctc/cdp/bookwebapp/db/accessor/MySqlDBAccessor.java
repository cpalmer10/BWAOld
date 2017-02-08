/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.db.accessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class MySqlDBAccessor {
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    //consider creating custom exception handlers
    public void openConnection(String driverClass, String url, String user, String password) throws ClassNotFoundException, SQLException{
        //add in validation
            //if (driverClass == "" || url == "" || user == "" || password == "")
//        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, user, password);
//        } 
//        catch (ClassNotFoundException | SQLException ex) {}
    }
    
    public void closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
        }        
    }
    
    public List<Map<String,Object>> findRecordsFor(String tableName, int maxRecords) throws SQLException{
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        
        String sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        
        return results;
    }
    
    public List<Map<String,Object>> findAllRecords(String tableName) throws SQLException {
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        
        String sql = "SELECT * FROM " + tableName;
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        
        return results;
    }
    
    public Map getRecordById(String table, String primaryKeyField, Object keyValue, boolean closeConnection) throws SQLException{
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData = null;
        final Map record = new HashMap();
        
        try {
            statement = connection.createStatement();
            String sql2;

            if(keyValue instanceof String){
                sql2 = "= '" + keyValue + "'";}
            else {
                sql2 = "=" + keyValue;}

            final String sql= "SELECT * FROM " + table + " WHERE " + primaryKeyField + sql2;
            resultSet = statement.executeQuery(sql);
            metaData = resultSet.getMetaData();
            metaData.getColumnCount();
            final int fields=metaData.getColumnCount();

            if(resultSet.next() ) {
                for( int i=1; i <= fields; i++ ) {
                    record.put( metaData.getColumnName(i), resultSet.getObject(i) );
                }
            }

            } catch (SQLException sqle) {
                    throw sqle;
            } catch (Exception e) {
                    throw e;
            } finally {
                    try {
                        statement.close();
                            if(closeConnection) connection.close();
                    } catch(SQLException e) {
                            throw e;
                    } // end try
            } // end finally
        
            return record;
    }

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
