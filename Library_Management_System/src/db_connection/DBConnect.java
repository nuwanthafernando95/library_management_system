/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DBConnect {
        static Connection connection = null;
        static PreparedStatement preparedStatement = null;
        static ResultSet resultSet = null;
        
        
        //Create connection
        public static Connection connectDB(){
            
            Connection resultConn = null;
            try{
             if (connection == null){
                 Class.forName("org.sqlite.JDBC");
                resultConn = DriverManager.getConnection("jdbc:sqlite:library_management_system_db.db");
             }
                
                return resultConn;
            }catch(Exception e){
                e.printStackTrace();
               JOptionPane.showMessageDialog(null, "Connection Fail");
            }
            return null;
        }
        
        public static void insertDB(String qry){
            if (connection == null){
                connection = connectDB();
            }
        try {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            preparedStatement = connection.prepareStatement(qry);
            preparedStatement.execute();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
        public static void updateDB(String qry){
            if (connection == null){
                connection = connectDB();
            }
            
            try {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            preparedStatement = connection.prepareStatement(qry);
            preparedStatement.execute();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
        public static ResultSet selectDB(String qry){
            if (connection == null){
                connection = connectDB();
            }
        
        try {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            
            if (resultSet != null){
                resultSet.close();
            }
            preparedStatement = connection.prepareStatement(qry);
            resultSet = preparedStatement.executeQuery();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
     
}
