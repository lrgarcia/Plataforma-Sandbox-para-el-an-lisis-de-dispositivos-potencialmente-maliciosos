/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

import es.uvigo.esei.tfg.ui.AnalizeDeviceController;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

/**
 *
 * @author lrgarcia
 */

/*Clase usada como controladora y que maneja la comunicación entre la interfaz de usuario
 *y el cliente que se comunica con el servidor
 *
 * 
 * 
 * */
public class ProcessManager {

   // public static ClientTester client;
    public static final int DEFAULT_PORT=5000;
   //public static final String DEFAULT_ADDRESS="192.168.0.17";
   public static final String DEFAULT_ADDRESS="127.0.0.1";
    private static int port;
    private static String address;
     public static ClientTesterThread client;
    public static Object clientResponse;
    private static ProcessManager processManager;
    public static Map<String, USBDevice> devices;
    public static boolean workingOnInterface = false;
    public final SimpleBooleanProperty isConnected;
     private boolean running = false;
     private final Thread checkThread;
     //Constructor de la clase sin parámetros
    public ProcessManager() throws ClassNotFoundException {

        /*this.isConnected = new SimpleBooleanProperty(client.isConnected());
        client = new ClientTester("127.0.0.1", 5000);
        */
    	this.setPort(DEFAULT_PORT);
    	this.setAddress(DEFAULT_ADDRESS);
         this.client = new ClientTesterThread(DEFAULT_ADDRESS,DEFAULT_PORT ); 
         running = true;
         client.start(); 
         this.isConnected = new SimpleBooleanProperty(client.isConnected());
         checkThread = new Thread(new CheckerConnection(), "CheckConnection");
         checkThread.start();
        //this.clientResponse = null;
    }
    
    //Constructor de la clase con los parámetros de la direccion y puerto
    public ProcessManager(String address, int port) throws ClassNotFoundException {
    	  

        /*this.isConnected = new SimpleBooleanProperty(client.isConnected());
        client = new ClientTester("127.0.0.1", 5000);
        */
    	this.setAddress(address);
    	this.setPort(port);
    	
         this.client = new ClientTesterThread(address,port); 
         running = true;
         client.start(); 
         this.isConnected = new SimpleBooleanProperty(client.isConnected());
         checkThread = new Thread(new CheckerConnection(), "CheckConnection");
         checkThread.start();
        //this.clientResponse = null;
    }
/*Actualiza la instancia de esta clase con nuevos atributos de dirección y puerto
 y reinicia el hilo que establece la conexión con el servidor*/  
    public void updateProcessManager(String address, int port) {
    	ProcessManager.client.setRunning(false);
    	this.running=false;
    	try {
			processManager = new ProcessManager(address,port);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
/*Implementación Singleton que obtiene la instancia de processManager en caso de ser
 * de estar creada y si no lo está la crea*/
    public static synchronized ProcessManager getIntance() throws ClassNotFoundException {
        if (processManager == null) {
            System.out.println("Instanciando ProcessManager");
            processManager = new ProcessManager();
        } else {
            System.out.println("ProcessManager ya instanciado");
        }
        return processManager;
    }
/*Función envía la petición de listar los dispositivos al servidor y
 *  devuelve un mapa de String y objetos USBDevice
 * que son los dispositivos conectados en el servidor*/
    public Map<String, USBDevice> getDevicesFromServer() {
        String command = "listAllDevices";
        String args = "";
        Object obj = null;
       
        ServerAction<String> actionServer= new ServerAction<String>(command,args);
        //ServerAction<String> actionServer = new ServerAction(command, args);
        client.sendToServer(actionServer);

        while (obj == null) {

            obj = client.readToServer();
        }

        // List<Device> devicesList = (( List<Device>) obj);
        Map<String, USBDevice> devicesList = ((Map<String, USBDevice>) obj);
       /* for (Map.Entry<String, USBDevice> entry : devicesList.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }*/
        this.devices = devicesList;
        return devicesList;
    }
/*Función que envía la petición al servidor de analizar el dispositivo enviado como
como parámetro de entrada y establece un hilo que va actualizando el textArea de la vista*/  
    public String getAnalysisDevice(USBDevice device) {
        System.out.println("EMPEZANDO ANALISIS DEL DISPOSITIVO");
       Thread runAnalysis = new Thread(new RunAnalysis(device), "RunAnalysis");
         runAnalysis.start();
        
       String command = "AnalizerDevice";
       USBDevice args = device;
        String obj = "";

        ServerAction actionServer = new ServerAction(command, args);
        client.sendToServer(actionServer);
        
        /*while (!obj.equals("Analisis terminado!")) {

            
            obj = (String) client.readToServer();

            System.out.println(obj);
            
            
            AnalizeDeviceController.displayIntoTextArea(obj);
             try {
                Thread.sleep(800);
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt(); 
                 System.out.println("aaa");    
            }

        }*/
        System.out.println("Se acabo el análisis");
        return "Finished";
       
    }
    
    public static boolean isConnected(){
        return client.isConnected();
    }
    
    public void addIsConnectedListener(ChangeListener<Boolean> listener) {
      //  System.out.println("Añadiendo listener");
        isConnected.addListener(listener);
    }

    public void executeCommand(String command,USBDevice device) {
    	if(command.equals("info")) {
    		
    	}else if(command.equals("AnalyzeDevice")) {
    		getAnalysisDevice(device);
    	}
    }
    

    public String toString() {
        return "processManager inicializado";
    }
    
    
/*     public void registerOutputListener(ListChangeListener<String> listener) {
        outputStream.addListener(listener);
    }*/
    
    
    public static String getAddress() {
		return address;
	}


	public static void setAddress(String address) {
		ProcessManager.address = address;
	}


	public static int getPort() {
		return port;
	}


	public static void setPort(int port) {
		ProcessManager.port = port;
	}


	class CheckerConnection implements Runnable {

        @Override
        public void run() {
          
            System.out.println("Checker Connection iniciado");
            while (running) {
              
                    try {
                        
                        Thread.sleep(2500);
                  
                        boolean currentStatus = client.isConnected();
                        if (currentStatus != isConnected.get()) {
                            //System.out.println("Cambio de isConnected status OJO!");
                            isConnected.set(currentStatus);
                        }
                    }
                    catch (InterruptedException e) {
                        // Don't care.
                    }
                }
               
        

    }

}
    
       class RunAnalysis implements Runnable {
        public USBDevice device;
       public RunAnalysis(USBDevice device)    
       {
       this.device=device;
       }
           
        @Override
        public void run() {
            String command = "AnalizerDevice";
            USBDevice args = device;
        String obj = "";

        ServerAction actionServer = new ServerAction(command, args);
        client.sendToServer(actionServer);
            System.out.println("Run Analysis iniciado");
            while (!obj.equals("Analisis terminado!")) {

            
            obj = (String) client.readToServer();

            System.out.println(obj);
            
            
            AnalizeDeviceController.displayIntoTextArea(obj);
             try {
                Thread.sleep(800);
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt(); 
                 System.out.println("aaa");    
            }

        }
               
        

    }

}
    
    
}