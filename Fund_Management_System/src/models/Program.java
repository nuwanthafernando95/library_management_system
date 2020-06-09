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
public class Program {
   private String id;
   private String venue;
   private String contact;
   private String time;
   private Date date;
   private Double budget;
   
    public Program(String id, String venue, String contact, String time, Date date, Double budget){
       this.id = id;
       this.venue = venue;
       this.contact = contact;
       this.time = time;
       this.date = date;
       this.budget = budget;
   }
   
   public Program(String venue, String contact, String time, Date date, Double budget){
       this.id = Components.generateUUID("Book");
       this.venue = venue;
       this.contact = contact;
       this.time = time;
       this.date = date;
       this.budget = budget;
   }
   
    public Program(){
       this.id = Components.generateUUID("Book");
   }
    
    public Program(String venue, String contact, Date date){
       this.venue = venue;
       this.contact = contact;
       this.date = date;
   }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
    
       public boolean validateValues(){
       if (this.venue != null && !this.venue.equals("") && this.contact != null && !this.contact.equals("") && this.time != null && !this.time.equals("")){
           return true;
       }
       return false;
   }
       
      public void insertProgram(){
       try {
        SimpleDateFormat dateFormat = new SimpleDateFormat(common.Common.DATE_FORMAT);
        String program_date = dateFormat.format(this.date);
        DBConnect.insertDB("INSERT INTO program (id,venue,contact,time,date,budget,status) VALUES ('"+this.id+"','"+this.venue+"','"+this.contact+"','"+this.time+"','"+program_date+"','"+this.budget+"', '"+AppStrings.ACTIVE+"')");
        
       Message.showInfoMessage(AppStrings.SUCCESS_INSERT, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       } 
   }
      
      public void updateProgram(){
       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat(common.Common.DATE_FORMAT);
            String program_date = dateFormat.format(this.date);
        DBConnect.updateDB("UPDATE program SET venue = '"+this.venue+"', contact = '"+this.contact+"', time = '"+this.time+"', date = '"+program_date+"', budget = '"+this.budget+"' WHERE id = '"+this.id+"'");
         
       Message.showInfoMessage(AppStrings.SUCCESS_UPDATE, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       } 
   }
      
    public void deleteProgram(){
       try {
        DBConnect.updateDB("UPDATE program SET status = '"+AppStrings.INACTIVE+"' WHERE id = '"+this.id+"'");
       Message.showInfoMessage(AppStrings.SUCCESS_DELETE, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       }
   }
    
       public void findProgramDetailsFromId(){
         
       String qry = "SELECT * from program where id = '"+this.id+"'";
       ResultSet resultSet = DBConnect.selectDB(qry);
       
       try {
           while(resultSet.next()){
               this.venue = resultSet.getString("venue");
               this.contact = resultSet.getString("contact");
               this.time = resultSet.getString("time");
               this.date = new SimpleDateFormat(common.Common.DATE_FORMAT).parse(resultSet.getString("date"));
               this.budget = Double.parseDouble(resultSet.getString("budget"));
              
           }
           
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
          public void findProgramIdFromVenueContactTime(){
       String qry = "SELECT id from program where venue = '"+this.venue+"' and contact = '"+this.contact+"'  ORDER BY ROWID ASC LIMIT 1"; 
       ResultSet resultSet = DBConnect.selectDB(qry);
       
       try {
           while(resultSet.next()){
               this.id = resultSet.getString("id");
           }
           
       } catch (SQLException ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
