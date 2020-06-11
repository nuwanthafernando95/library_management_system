/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import common.AppStrings;
import common.Common;
import common.StaticAttributes;
import components.Components;
import components.Message;
import db_connection.DBConnect;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class BookBorrow {

    private String borrowId;
    private Program book;
    private String borrowDate;
    private String returnDate;
    private User borrower;

    public BookBorrow() {
        setBorrowId();
        getCurrentDateAndReturnDate();
        borrower = StaticAttributes.currentUser;

    }
    
    public BookBorrow(String bookId){
        Program program = new Program();
        program.setId(bookId);
        book = program;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public Program getBook() {
        return book;
    }

    public void setBook(Program book) {
        this.book = book;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    private void getCurrentDateAndReturnDate() {
        DateFormat df = new SimpleDateFormat(new Common().DATE_FORMAT);
        Date date = new Date();
        this.borrowDate = df.format(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DATE, new Common().BOOK_RETURN_DATES);
        Date returnDate = c.getTime();

        this.returnDate = df.format(returnDate);
    }

    private void setBorrowId() {
        this.borrowId = Components.generateUUID("Borrow");
    }

    public void insertBorrowBook() {
        try {
            DBConnect.insertDB("INSERT INTO borrow (borrowId,bookId,borrow_date,return_date, borrower, status) VALUES ('" + this.borrowId + "','" + this.book.getId() + "','" + this.borrowDate + "','" + this.returnDate + "', '" + this.borrower.getUsername() + "', '" + AppStrings.ACTIVE + "')");

            Message.showInfoMessage(AppStrings.SUCCESS_INSERT, AppStrings.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
            Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
        }
    }
    
       public void updateProgram(){
       try {
           System.out.println(this.borrowId);
        DBConnect.updateDB("UPDATE borrow SET status = '"+AppStrings.INACTIVE+"' WHERE borrowId = '"+this.borrowId+"'");
         
       Message.showInfoMessage(AppStrings.SUCCESS_UPDATE, AppStrings.SUCCESS);
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
       } 
   }
     
    
      public void findBorrowDetailsFromBookId(){
         
       String qry = "SELECT * from borrow where bookId = '"+this.book.getId()+"'";
       ResultSet resultSet = DBConnect.selectDB(qry);
       
       try {
           while(resultSet.next()){
               this.borrowId = resultSet.getString("borrowId");
               this.borrowDate = resultSet.getString("borrow_date");
               this.returnDate = resultSet.getString("return_date");
               String borrower = resultSet.getString("borrower");
               
               User user = new User();
               user.setUsername(borrower);
               this.borrower = user;
              
           }
           
       } catch (Exception ex) {
           Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

}
