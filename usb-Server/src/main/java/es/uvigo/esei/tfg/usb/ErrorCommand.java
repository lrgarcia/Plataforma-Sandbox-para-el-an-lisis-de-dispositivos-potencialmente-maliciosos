/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

/**
 *
 * @author Luis
 */

import java.net.*; 
import java.io.*; 
import java.util.*;  
public class ErrorCommand<T extends String> implements Command<T> {
    
    private static final String COMMAND_NAME = "ERROR";
 
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
 
    @Override
    public String run(T args, ObjectOutputStream  out) {
        String message = "Error al invocar el comando";
        return message;
       // write(out, message);
    }
     @Override
    public String info(){
    return "Devuelve información del comando";
}
    
}
