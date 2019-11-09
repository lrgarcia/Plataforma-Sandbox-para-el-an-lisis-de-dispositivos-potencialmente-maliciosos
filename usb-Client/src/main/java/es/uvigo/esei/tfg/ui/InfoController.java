package es.uvigo.esei.tfg.ui;

import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;

import es.uvigo.esei.tfg.usb.USBDevice;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class InfoController {

	  @FXML
	    private static Button closeButton;


	    public void perform() throws ClassNotFoundException {}
	    @FXML
	    void onCancel(ActionEvent event) throws IOException {
	        Launcher.goBackToHome();
	    }
	    
	    
}
