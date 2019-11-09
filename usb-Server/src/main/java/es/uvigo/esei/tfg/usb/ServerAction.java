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
import java.io.Serializable;
/**
 Objeto comando serializable en caso de necesitar un tipo de comunicación mas complejo que usar
 * datos primitivos
 */

public class ServerAction<T> implements Serializable {



protected String command = null;
protected T args;



public ServerAction(String command) {
    this.command = command;
 
}
public ServerAction(String command, T args){
    this.command = command;
    this.args = args;
}
public String getCommand(){
    return this.command;
}

public T getArgs(){
    return this.args;
}

public void setCommand(String command){
    this.command=command;
    
}

public void setArgs(T args){
    this.args=args;
    
}

public String toString(){
    String toret="";
    toret+="Comando: "+this.command;
    return toret;
}
    
}