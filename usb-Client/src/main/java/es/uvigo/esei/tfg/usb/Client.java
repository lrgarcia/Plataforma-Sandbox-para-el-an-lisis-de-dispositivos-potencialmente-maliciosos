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
// A Java program for a Client 
import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

    // initialize socket and input output streams 
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    private ObjectInputStream inputObject = null;
    private ObjectOutputStream outObject = null;

    // constructor to put ip address and port 
    public Client(String address, int port) throws ClassNotFoundException {
        // establish a connection 
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal 
            String line = "";
            String toret = "";
            //System.out.println(line);
            //input = new DataInputStream(socket.getInputStream());

            // sends output to the socket 
            //out = new DataOutputStream(socket.getOutputStream());
            outObject = new ObjectOutputStream(socket.getOutputStream());
            String command = "listAllDevices";
            String args ="";
            
            ServerAction actionServer =new ServerAction(command,args);
            
            System.out.println("Comando recibido: Enviando comando al servidor");
           // outObject.writeUTF(command);
           outObject.writeObject(actionServer);
            outObject.flush();
            System.out.println("Comando enviado: esperando respuesta del servidor");
            List<Device> devices=new ArrayList<>();
            System.out.println("Inicializando inputObject");
            try{
            inputObject = new ObjectInputStream(socket.getInputStream());
            System.out.println("Intentando leer respuesta del servidor");
            devices = (List<Device>) inputObject.readObject();
            }catch(NullPointerException e){
                System.out.println("No se ha recibido ninguna respuesta");
            }
            System.out.println("Respuesta recibida");
            for(Device device : devices){
            System.out.println(device.toString());
            }
            while (!line.equals("Over")) {

                //System.out.println("Esperando comando");
                Scanner scanner = new Scanner(System.in);
                line = scanner.nextLine();
                //line = input.readLine(); 
                System.out.println(line);
                outObject.writeUTF(line);
                toret = inputObject.readUTF();
                System.out.println(toret);

            }

            // close the connection 
            try {
                System.out.println("Cerrando conexcion");
                input.close();
                out.close();
                socket.close();
            } catch (IOException i) {
                System.out.println("Error al cerrar ");
                System.out.println(i);
            }

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println("Error al conectar con el server");
            System.out.println(i);
        }

        // string to read message from input 
        // keep reading until "Over" is input 
    }
public void sendToServer(Object obj) {
        try {
            outObject.writeObject(obj);
            outObject.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        try{
        Client client = new Client("127.0.0.1", 5000);
        }catch(ClassNotFoundException e){
            System.out.println("Petao");
        }
    }
}
