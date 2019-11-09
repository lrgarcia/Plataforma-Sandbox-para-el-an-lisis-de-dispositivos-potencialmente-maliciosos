/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import es.uvigo.esei.tfg.usb.USBDevice;
import es.uvigo.esei.tfg.usb.ProcessManager;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.net.*;
import java.io.*;
import java.util.*;
import javafx.application.HostServices;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.util.StringConverter;
//import logic.ProcessController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.ButtonType;
import javafx.collections.ListChangeListener;

/**
 *
 * @author Luis
 */
public class AnalizeDeviceController {

    @FXML
    private static TextArea outputTextArea;
    @FXML
    private static Button cancelButton;

    private ListChangeListener<String> outputListener;
    USBDevice device;
    public void perform(USBDevice device, Scene scene) throws ClassNotFoundException {
       this.device=device;
    	AnalizeDeviceController.outputTextArea = (TextArea) scene.lookup("#outputTextArea");;
        outputTextArea.setText("Empezando análisis \n");
        ProcessManager processManager = ProcessManager.getIntance();

        System.out.println("Invocando analisis desde la interfaz");
        processManager.getAnalysisDevice(device);

    }
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Launcher.showActionDevice(device);
    }
    public static void displayIntoTextArea(String output) {
        System.out.println("Displayando cosas al textarea");
        String textArea = outputTextArea.getText();

        StringBuilder fieldContent = new StringBuilder(textArea + "\n");
        
        fieldContent.append(output);
       outputTextArea.appendText(output+"\n");
        /* Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    outputTextArea.appendText(output+"\n");
                }
            });*/
    }
    

}

