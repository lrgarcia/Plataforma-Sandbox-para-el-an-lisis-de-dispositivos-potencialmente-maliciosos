/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.ui;

import es.uvigo.esei.tfg.usb.ProcessManager;
import java.net.*;
import java.io.*;
import java.util.*;
import javafx.application.HostServices;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
//import logic.ProcessController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Luis
 */
public class HomeScreenController implements Initializable {

    protected Parent fxmlRoot;
    protected Scene currentScene;
    protected static Stage primaryStage;
    @FXML
    private Button advancedOptionsButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button configButton;
    @FXML
    private Button analizeAndSelectDevice;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private Label serverStatusLabel;
    @FXML
    private ProgressBar progressBar;
    
    private ChangeListener<Boolean> writeBlockListener;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    void onInfo(ActionEvent event) throws IOException {
        //changeScene(loadFXML(InfoScreenController.class, InfoScreenController.getFXMLLocation()).getScene());
    	Launcher.goInfoApp();
    }
    
    @FXML
    void onConfig(ActionEvent event)  {
        //changeScene(loadFXML(InfoScreenController.class, InfoScreenController.getFXMLLocation()).getScene());
        try{
        Launcher.goConfiguration();
        }catch(IOException e){}
    }

    @FXML
    void onAnalizeAndSelectDeviceClick(ActionEvent event) throws IOException {/*
        // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(SelectDeviceController.class.getResource("/fxml/selectDevice.fxml"));
       Parent root = (Parent) fxmlLoader.load();
       Scene scene = new Scene(root, 400, 560);
       primaryStage.setScene(scene);*/
        Launcher.showSelectDevice();

    }
    
    
    private ChangeListener<Boolean> getStatusListener() {
        writeBlockListener = (observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue) {
                
                System.out.println("HA CAMBIADO EL CHANGE LISTENER OJO");
                System.out.println(newValue);
                analizeAndSelectDevice.setDisable(false);
                serverStatusLabel.setText("ENABLED");
                serverStatusLabel.setTextFill(Color.LIME);
               // readyLabel.setVisible(true);
                serverStatusLabel.autosize();
            }
            else {
            	analizeAndSelectDevice.setDisable(true);
                serverStatusLabel.setText("DISABLED");
                serverStatusLabel.setTextFill(Color.RED);
                //readyLabel.setVisible(false);
                serverStatusLabel.autosize();
            }
        });
        return new WeakChangeListener<>(writeBlockListener);
    }
    
    
    

    public void perform() {
        System.out.println("Ejectuando hilo de lectura de estado de la conexion entre cliente servidor");
        /*Platform.runLater(new Runnable() {
            public void run() {
                try {
                    System.out.println("Inicializando Interfaz");
                    ProcessManager processManager = ProcessManager.getIntance();
                    System.out.println(processManager.isConnected());
                    if(processManager.isConnected()){
                        serverStatusLabel.setText("Conectado");
                        serverStatusLabel.setTextFill(Color.web("#00FF00"));
                    }
                    System.out.println(processManager.toString());
                    //primaryStage.setTitle("Home");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });*/
/*
        Thread thread = new Thread(myRunnable);
        thread.start();*/
        serverStatusLabel.setText("DISABLED");
                serverStatusLabel.setTextFill(Color.RED);
                //readyLabel.setVisible(false);
                serverStatusLabel.autosize();
                analizeAndSelectDevice.setDisable(true);

        try {
        ProcessManager processManager = ProcessManager.getIntance();
            System.out.println(processManager.isConnected());
            if(!processManager.isConnected()){
                 serverStatusLabel.setText("DISABLED");
                serverStatusLabel.setTextFill(Color.RED);
                analizeAndSelectDevice.setDisable(true);
                //readyLabel.setVisible(false);
                serverStatusLabel.autosize();
            }else{
                 serverStatusLabel.setText("ENABLED");
                serverStatusLabel.setTextFill(Color.LIME);
                //readyLabel.setVisible(false);
                analizeAndSelectDevice.setDisable(false);
                serverStatusLabel.autosize();
            }
        processManager.addIsConnectedListener(getStatusListener());
         } catch (ClassNotFoundException ex) {
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }

    

    public Scene getScene() {
        if (currentScene == null) {
            throw new RuntimeException("Not initialized!");
        }
        return currentScene;
    }

}