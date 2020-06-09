/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.UUID;

/**
 *
 * @author User
 */
public class Components {
    
    public static String generateUUID(String name){
        UUID uuid = UUID.randomUUID();
        return name + " - " + uuid.toString();
    }
}
