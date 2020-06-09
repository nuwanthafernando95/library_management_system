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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author User
 */

@Getter
@Setter
public class Book {
   private String id;
   private String author;
   private String publisher;
   private String time;
   private Date date;
   private Double budget;
   
    public Book(String id, String venue, String publisher, String time, Date date, Double budget){
       this.id = id;
       this.author = venue;
       this.publisher = publisher;
       this.time = time;
       this.date = date;
       this.budget = budget;
   }
   
   public Book(String venue, String publisher, String time, Date date, Double budget){
       this.id = Components.generateUUID("Program");
       this.author = venue;
       this.publisher = publisher;
       this.time = time;
       this.date = date;
       this.budget = budget;
   }
   
    public Book(){
       this.id = Components.generateUUID("Program");
   }
    
    public Book(String venue, String publisher, Date date){
       this.author = venue;
       this.publisher = publisher;
       this.date = date;
   }

    
       public boolean validateValues(){
       if (this.author != null && !this.author.equals("") && this.publisher != null && !this.publisher.equals("") && this.time != null && !this.time.equals("")){
           return true;
       }
       return false;
   }
       
      public void insertProgram(){
       try {
        SimpleDateFormat dateFormat = new SimpleDateFormat(common.Common.DATE_FORMAT);
        String program_date = dateFormat.format(this.date);
        DBConnect.insertDB("INSERT INTO program (id,venue,publisher,time,date,budget,status) VALUES ('"+this.id+"','"+this.author+"','"+this.publisher+"','"+this.time+"','"+program_date+"','"+this.budget+"', '"+AppStrings.ACTIVE+"')");
        
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
        DBConnect.updateDB("UPDATE program SET venue = '"+this.author+"', publisher = '"+this.publisher+"', time = '"+this.time+"', date = '"+program_date+"', budget = '"+this.budget+"' WHERE id = '"+this.id+"'");
         
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
               this.author = resultSet.getString("venue");
               this.publisher = resultSet.getString("publisher");
               this.time = resultSet.getString("time");
               this.date = new SimpleDateFormat(common.Common.DATE_FORMAT).parse(resultSet.getString("date"));
               this.budget = Double.parseDouble(resultSet.getString("budget"));
              
           }
           
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
          public void findProgramIdFromVenuepublisherTime(){
       String qry = "SELECT id from program where venue = '"+this.author+"' and publisher = '"+this.publisher+"'  ORDER BY ROWID ASC LIMIT 1"; 
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
