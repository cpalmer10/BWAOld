/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.cdp.bookwebapp.db.accessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    public void openConnection(String driverClass, String url, String user, String password){
        
    }
    
}
