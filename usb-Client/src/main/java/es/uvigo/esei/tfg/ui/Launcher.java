/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.ui;
import es.uvigo.esei.tfg.usb.USBDevice;
import es.uvigo.esei.tfg.usb.ProcessManager;
import es.uvigo.esei.tfg.usb.Client;
import javafx.application.Application;
import java.net.*;
import java.io.*;
import java.util.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.paint.Color;

/**
 *
 * @author Luis
 */
public class Launcher extends Application {

    private static Parent mainLayout;
    private static Stage primaryStage;
    private static Stage previousStage;

    public void start(Stage primaryStage) throws Exception {
        
        //Client client = new Client("127.0.0.1", 5000);
        this.primaryStage= primaryStage;
        primaryStage.getIcons().add(new Image(Launcher.class.getResourceAsStream("/icons/iconApp.png")));
       // Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        FXMLLoader loader = new FXMLLoader();
        System.out.println(Launcher.class.getResource("/fxml/home.fxml"));
        loader.setLocation(Launcher.class.getResource("/fxml/home.fxml"));
        mainLayout = (Parent) loader.load();
        primaryStage.setTitle("Home");
        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
        primaryStage.setScene(scene);

        //primaryStage.getStylesheets().add(GUILauncher.class.getResource("css/style.css").toString());
        primaryStage.show();
        HomeScreenController controller = loader.getController();
       
        controller.perform();
        
    }

    public static void showSelectDevice() throws IOException {
        System.out.println("Se ha seleccionado mostrar todos los dispositivos");
        FXMLLoader loader = new FXMLLoader();
        System.out.println(Launcher.class.getResource("/fxml/selectDevice.fxml"));
        loader.setLocation(Launcher.class.getResource("/fxml/selectDevice.fxml"));
        System.out.println("Cargando vista fxml");
        mainLayout = (Parent) loader.load();
        previousStage=primaryStage;
        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(Launcher.class.getResource("/css/style.css").toString());
        //Scene scene = new Scene(mainLayout, 540, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        System.out.println("Mostrando vista");
        primaryStage.show();
        SelectDeviceController controller = loader.getController();
        try{
        controller.perform(primaryStage);
        }catch(ClassNotFoundException e ){
                    System.out.println(e);
        }
        
    }
    
    
    public static void showActionDevice(USBDevice device) throws IOException {
        System.out.println("Se ha seleccionado todas las acciones");
        FXMLLoader loader = new FXMLLoader();
        System.out.println(Launcher.class.getResource("/fxml/selectAction.fxml"));
        loader.setLocation(Launcher.class.getResource("/fxml/selectAction.fxml"));
        System.out.println("Cargando vista fxml selectAction");
        mainLayout = (Parent) loader.load();
        previousStage=primaryStage;
        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(Launcher.class.getResource("/css/style.css").toString());
        //Scene scene = new Scene(mainLayout, 540, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        System.out.println("Mostrando vista");
        primaryStage.show();
        SelectActionController controller = loader.getController();
        try{
        controller.perform(primaryStage,device);
        }catch(ClassNotFoundException e ){
                    System.out.println(e);
        }catch(LoadException a) {
        	System.out.println(a);
        }
        
    }
    
    
    
    public static void analizeSelectedDevice(USBDevice device) throws IOException{
        System.out.println("Se ha seleccionado analizar el dispositivo: "+device.toString());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Launcher.class.getResource("/fxml/analizeDevice.fxml"));
        System.out.println("Cargando vista fxml del analize device");
        mainLayout = (Parent) loader.load();
        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(Launcher.class.getResource("/css/style.css").toString());
        previousStage=primaryStage;
        //Scene scene = new Scene(mainLayout, 540, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        System.out.println("Mostrando vista");
        primaryStage.show();
        
        AnalizeDeviceController controller = loader.getController();
        try{
        controller.perform(device,scene);
        }catch(ClassNotFoundException e ){
                    System.out.println(e);
        }
        
    }

    public static void InfoSelectedDevice(USBDevice device) throws IOException{
        System.out.println("Se ha seleccionado obtener info del dispositivo "+device.toString());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Launcher.class.getResource("/fxml/infoDevice.fxml"));
        System.out.println("Cargando vista fxml del info device");
        mainLayout = (Parent) loader.load();
        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(Launcher.class.getResource("/css/style.css").toString());
        previousStage=primaryStage;
        //Scene scene = new Scene(mainLayout, 540, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        System.out.println("Mostrando vista");
        primaryStage.show();
        
        InfoDeviceController controller = loader.getController();
        try{
        controller.perform(device,scene);
        }catch(ClassNotFoundException e ){
                    System.out.println(e);
        }
        
    }
    
    
    public static void goBackToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Launcher.class.getResource("/fxml/home.fxml"));
        System.out.println("Cargando vista fxml");
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Launcher.class.getResource("/css/style.css").toString());
        primaryStage.setTitle("Home");
        //primaryStage.setScene(new Scene(root, 540, 400));
        primaryStage.setScene(scene);
        primaryStage.show();
        HomeScreenController controller = loader.getController();
       
        controller.perform();
    }
    
    
    public static void goConfiguration() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Launcher.class.getResource("/fxml/configuration.fxml"));
        System.out.println("Cargando vista configuration fxml");
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Launcher.class.getResource("/css/style.css").toString());
        primaryStage.setTitle("Configuration");
        //primaryStage.setScene(new Scene(root, 540, 400));
        primaryStage.setScene(scene);
        primaryStage.show();
        ConfigurationController controller = loader.getController();
       
        try{
        controller.perform(primaryStage);
        }catch(ClassNotFoundException e){
            System.out.println("SE ha liado");
                    System.out.println(e);
        }
    }
    
   
    public static void goInfoApp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Launcher.class.getResource("/fxml/info.fxml"));
        System.out.println("Cargando vista info fxml");
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Launcher.class.getResource("/css/style.css").toString());
        primaryStage.setTitle("Info");
        //primaryStage.setScene(new Scene(root, 540, 400));
        primaryStage.setScene(scene);
        primaryStage.show();
        InfoController controller = loader.getController();
       
        try{
        controller.perform();
        }catch(ClassNotFoundException e){
            System.out.println("SE ha liado");
                    System.out.println(e);
        }
    }
    
    

    public static void main(String[] args) {
    	
        launch(args);
    }
}