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
// A Java program for a Server 
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	public static final int DEFAULT_PORT=5000;
	
    //initialize socket and input stream 
    private Socket socket;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor with port 
    public Server(int port) {
        // starts server and waits for a connection 
        try {
            
            server = new ServerSocket(port);
            
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");
            
            socket = server.accept();
            System.out.println("Client accepted");
            System.out.println(socket.isConnected());
            // takes input from the client socket 
            /* in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); */
            System.out.println("Inicializando inputStream OutputStream");
            InputStream entrada = socket.getInputStream();
            OutputStream salida = socket.getOutputStream();
/*
            DataInputStream entradaDatos = new DataInputStream(entrada);
            DataOutputStream salidaDatos = new DataOutputStream(salida);*/
            //ObjectOutputStream outObject = new ObjectOutputStream(salida);
            ObjectInputStream inObject = new ObjectInputStream(entrada);
            ObjectOutputStream outObject = new ObjectOutputStream(salida);

            // DataInputStream entradaDatos = new DataInputStream (entrada);
            //DataOutputStream salidaDatos = new DataOutputStream (salida);
            String commandName = "";
            

            // reads message from client until "Over" is sent 
            while (commandName!="Over") {
                try {
                    //USAR OBJETO SERVER ACTION COMO OBJETO DE ENTRADA 
                    //line = (String) inObject.readUTF();
                    System.out.println("Esperando entrada");
                    ServerAction serverAction = (ServerAction) inObject.readObject();
                    System.out.println("Recibiendo entrada");
       
                    System.out.println(serverAction.toString());
                    CommandManager manager = CommandManager.getIntance();

                   /* String[] commands = CommandUtil.extractTokens(line);
                    String commandName = commands[0];
                    String[] commandArgs = null;*/
                   /*
                   
                    if (commands.length > 1) {
                        commandArgs = Arrays.copyOfRange(commands, 1, commands.length);
                    }*/
                    commandName = serverAction.getCommand();
                    Object commandArgs = serverAction.getArgs();
                    
                    Command command = manager.getCommand(commandName);
                    String toret = command.run(commandArgs, outObject);
                    System.out.println(toret);

                    /*
                    line = entradaDatos.readUTF(); 
                    System.out.println(line); 

                    if(line.equals("PENDRIVE MODULE")){
                        //List<String> supplierNames = Arrays.asList("sup1", "sup2", "sup3");
                       // System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");


                        PendriveModule module= new PendriveModule("name",supplierNames);
                      String toret =  module.execute();
                    }*/
                    
                //Catch first try exception    
                outObject.reset();
                } catch (IOException i) {
                    System.out.println("Error en la lectura/escritura de datos");
                    System.out.println(i);
                }  catch (ClassNotFoundException c){
                System.out.println(c);}
            }
            System.out.println("Cerrando conexion");

            // close connection 
            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println("Error en la conexion");
            System.out.println(i);
            closeConnection();
            Server server = new Server(port);
            
        }
    }

   public void closeConnection(){
    try {
                System.out.println("Cerrando conexcion");
                server.close();
                socket.close();
            } catch (IOException i) {
                System.out.println("Error al cerrar ");
                System.out.println(i);
            }
    
}  
    
    public static void main(String args[]) {

    	int port = DEFAULT_PORT;
    	if ((args.length == 2) && ("-c".equals(args[0])) && (args[1].matches("\\d+"))) {
    		port = Integer.parseInt(args[1]);
    	} 
        Server server = new Server(port);

    }
}
