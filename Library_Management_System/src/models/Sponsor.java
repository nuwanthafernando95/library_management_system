/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import common.AppStrings;
import components.Components;
import components.Message;
import db_connection.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Sponsor {
   private String id;
   private String name;
   private String contact;
   private String email;
   private Date dob;
   private String address;
   
   public Sponsor(String id, String name, String contact, String email, Date dob, String address){
       this.id = id;
       this.name = name;
       this.contact = contact;
       this.email = email;
       this.dob = dob;
       this.address = address;
   }
   
   public Sponsor(String name, String contact, String email, Date dob, String address){
       this.id = Components.generateUUID("Sponsor");
       this.name = name;
       this.contact = contact;
       this.email = email;
       this.dob = dob;
       this.address = address;
   }
   
    public Sponsor(){
       this.id = Components.generateUUID("Sponsor");
   }
    
    public Sponsor(String name, String contact, String email){
       this.name = name;
       this.contact = contact;
       this.email = email;
   }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
   public boolean validateValues(){
       if (this.name != null && !this.name.equals("") && this.contact != null && !this.contact.equals("") && this.email != null && !this.email.equals("")){
           return true;
       }
       return false;
   }
   
   public void insertSponsor(){
       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat(common.Common.DATE_FORMAT);
           String joindate = dateFormat.format(this.dob);
        DBConnect.insertDB("INSERT INTO sponsor (id,name,contact,email,dob,address,status) VALUES ('"+this.id+"','"+this.name+"','"+this.contact+"','"+this.email+"','"+joindate+"','"+this.address+"', '"+AppStrings.ACTIVE+"')");
        
       Message.showInfoMessage(AppStrings.SUCCESS_INSERT, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       } 
   }
   
   public void updateSponsor(){
       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat(common.Common.DATE_FORMAT);
            String joindate = dateFormat.format(this.dob);
        DBConnect.updateDB("UPDATE sponsor SET name = '"+this.name+"', contact = '"+this.contact+"', email = '"+this.email+"', dob = '"+joindate+"', address = '"+this.address+"' WHERE id = '"+this.id+"'");
         
       Message.showInfoMessage(AppStrings.SUCCESS_UPDATE, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       } 
   }
   
   public void deleteSponsor(){
       try {
        DBConnect.updateDB("UPDATE sponsor SET status = '"+AppStrings.INACTIVE+"' WHERE id = '"+this.id+"'");
       Message.showInfoMessage(AppStrings.SUCCESS_DELETE, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       }
   }
   
   public void findUserIdFromNameEmailContact(){
       String qry = "SELECT id from sponsor where name = '"+this.name+"' and contact = '"+this.contact+"' and email ='"+this.email+"'  ORDER BY ROWID ASC LIMIT 1"; 
       ResultSet resultSet = DBConnect.selectDB(qry);
       
       try {
           while(resultSet.next()){
               this.id = resultSet.getString("id");
           }
           
       } catch (SQLException ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public void findSponsorDetailsFromId(){
       String qry = "SELECT * from sponsor where id = '"+this.id+"'";
       ResultSet resultSet = DBConnect.selectDB(qry);
       
       try {
           while(resultSet.next()){
               this.name = resultSet.getString("name");
               this.contact = resultSet.getString("contact");
               this.email = resultSet.getString("email");
               this.dob = new SimpleDateFormat(common.Common.DATE_FORMAT).parse(resultSet.getString("dob"));
               this.address = resultSet.getString("address");
           }
           
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }
   
}
