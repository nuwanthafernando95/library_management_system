/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import common.AppStrings;
import components.Message;
import db_connection.DBConnect;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class User {
    private String username;
    private String password;
    
     public User(String username ,String password){
        this.username = username;
        this.password = password;
    }
    
    public User(String password){
        this.username = generateUsername();
        this.password = password;
    }
    
    public User(){
        this.username = generateUsername();
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
    
    private String generateUsername(){
      String generatedUsername = "user";
        ResultSet resultSet = DBConnect.selectDB("SELECT COUNT(*) as row_count from authentication");
        try {
           while(resultSet.next()){
              generatedUsername = generatedUsername + (Integer.parseInt(resultSet.getString("row_count")) + 1);  
           }
           
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
       }
      return generatedUsername;
      
    }
    
    public boolean validatePassword(String password, String re_enterPassword){
        
        //Verify both passwords are correct or not
        if (!password.equals(re_enterPassword)){
            return false;
        }
       
        //Check password lengths
        if (password.length() < common.Common.MIN_PASS_LENGTH || password.length() > common.Common.MAX_PASS_LENGTH){
            return false;
        }
        if (re_enterPassword.length() < common.Common.MIN_PASS_LENGTH || re_enterPassword.length() > common.Common.MAX_PASS_LENGTH){
            return false;
        }
        
        return true;
    }
    
    public void insertUser(){
       try {
       DBConnect.insertDB("INSERT INTO authentication (username, password, is_admin) VALUES ('"+this.username+"', '"+this.password+"', 'false')");
       Message.showInfoMessage(AppStrings.SUCCESS_INSERT, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       } 
   }
    
}
