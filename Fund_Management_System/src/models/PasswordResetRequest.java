/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import common.AppStrings;
import components.Message;
import db_connection.DBConnect;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class PasswordResetRequest {
    String id;
    String name;
    
    public PasswordResetRequest(String id, String name){
        this.id = id;
        this.name = name;
    }
    
    public void updateResetStatus(){
        try {
            DBConnect.updateDB("UPDATE reset_requests SET is_reset = 'true' where id = '"+ this.id+"'");
            Message.showInfoMessage(AppStrings.SUCCESS_UPDATE, AppStrings.SUCCESS);
        } catch (Exception e) {
             Logger.getLogger(Sponsor.class.getName()).log(Level.SEVERE, null, e);
           Message.showError(AppStrings.SOMETHING_WRONG, AppStrings.ERROR);
        }
    }
}
