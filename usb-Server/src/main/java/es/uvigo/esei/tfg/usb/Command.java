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
//Clase para la interfaz Comando
//He de pensar que entra, que sale, quiero solamente que devuelva Strings? Que parametros de entrada debe aceptar?
//Tienen que ser objetos?


/*Para ListAllDewvice:
Entrada: ObjectOutputStream out
Salida: Objeto DeviceList list = new DeviceList();
               result = LibUsb.getDeviceList(context, list);*/
import java.net.*; 
import java.io.*; 
import java.util.*;   
public interface Command <T>{
    
    abstract String run(T args, ObjectOutputStream  out);
    abstract String getCommandName();
    abstract String info();
    
    
}
