package es.uvigo.esei.tfg.ui;

import java.io.IOException;

import es.uvigo.esei.tfg.usb.ProcessManager;
import es.uvigo.esei.tfg.usb.USBDevice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigurationController {
	protected static Stage primaryStage;
    @FXML
    private Button backButton;

    @FXML
    private Button applyButton;
    
    @FXML
    private TextField portInput;

    @FXML
    private TextField addressInput;
    
    @FXML
    private Label devicesLoadingLabel;
    
    public static String getFXMLLocation() {
        return "/fxml/configuration.fxml";
    }

    public void perform(Stage primaryStage) throws ClassNotFoundException,IOException {
        System.out.println("Dentro de perform de COnfiguration controller");
    this.primaryStage = primaryStage;
    try {
    	ProcessManager processManager= ProcessManager.getIntance();
     String address = processManager.getAddress();
     int port = processManager.getPort();
     
     portInput.setText(String.valueOf(port));
     addressInput.setText(address);
    }catch(Exception e) {}
    }
    
    
    @FXML
    void onBack(ActionEvent event) throws IOException {
        Launcher.goBackToHome();
    }
    @FXML
    void onApply(ActionEvent event) throws IOException {
        String port= portInput.getText();
        String address= addressInput.getText(); 
        try {
        	ProcessManager processManager= ProcessManager.getIntance();
        	System.out.println(processManager.getPort()+" "+processManager.getAddress());
        	processManager.updateProcessManager(address, Integer.parseInt(port));
        	ProcessManager newProcessManager= ProcessManager.getIntance();
        	System.out.println(newProcessManager.getPort()+" "+newProcessManager.getAddress());
        	Launcher.goBackToHome();
        }catch(Exception e) {
        
        
        
    }
    
    
    
}
    }
