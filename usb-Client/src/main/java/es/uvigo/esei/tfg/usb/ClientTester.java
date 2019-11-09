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

public class ClientTester {

    private Socket socket = null;
  //  private DataInputStream input = null;
  //  private DataOutputStream out = null;

    private ObjectInputStream inputObject = null;
    private ObjectOutputStream outObject = null;

    public ClientTester(String address, int port) {
        
    }
public void sendToServer(Object obj) {
        try {
            ServerAction serverAction = (ServerAction) obj;
            System.out.println(serverAction.toString());
            if(this.outObject==null){
            this.outObject = new ObjectOutputStream(socket.getOutputStream());
            }
            
            System.out.println("Escribiendo objecto server action");
            outObject.writeObject(obj);
            outObject.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public Object readToServer(){
    
    Object toret= null;
    try {
        if(this.inputObject==null){
        inputObject = new ObjectInputStream(socket.getInputStream());
        }
                    while(toret==null){
                  //  System.out.println("Intentando leer objeto");
                    //inputObject = new ObjectInputStream(socket.getInputStream());
                    // sends output to the socket 
                   // outObject = new ObjectOutputStream(socket.getOutputStream());
                  //  System.out.println("Leyendo object");
                  toret = (Object) inputObject.readObject();
                    //System.out.println(toret);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
    return toret;
}

public void closeConnection(){
    try {
                System.out.println("Cerrando conexcion");
                inputObject.close();
                outObject.close();
                socket.close();
            } catch (IOException i) {
                System.out.println("Error al cerrar ");
                System.out.println(i);
            }
    
} 

public boolean isConnected(){
    
    return socket.isConnected();
    
}

/*
    public static void main(String args[]) throws ClassNotFoundException {
        System.out.println("Inicializando processManager");
        
        //ProcessManager processManager = new ProcessManager();
        ProcessManager processManager = ProcessManager.getIntance();
        System.out.println(processManager.toString());
       Map<String, Device> devices =  processManager.getDevicesFromServer();
         /*for(Device device : devices){
            System.out.println(device.toString());
            }*/
         
             // Map<String, Device> devices =  processManager.getDevicesFromServer();
      /*   for(Map.Entry<String, Device> entry : devices.entrySet()) {
            String key = entry.getKey();
             Device device = entry.getValue();
              System.out.println(device.toString());
 
                }  */
         /*
        System.out.println("Comenzando Analize Device Command"); 
        File f = new File("D:/");
        Device device = new Device(f, "Disco duro", "Disco duro", 0x7fff_ffff_ffff_ffffL);
        String toret =  processManager.getAnalysisDevice(device);*/
       // ClientTester client = new ClientTester("127.0.0.1", 5000);
    }


