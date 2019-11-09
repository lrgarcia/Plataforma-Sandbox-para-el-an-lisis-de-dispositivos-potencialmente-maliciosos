package es.uvigo.esei.tfg.ui;

import java.io.IOException;
import java.util.Optional;

import es.uvigo.esei.tfg.ui.SelectDeviceController;
import es.uvigo.esei.tfg.usb.HUDDevice;
import es.uvigo.esei.tfg.usb.ProcessManager;
import es.uvigo.esei.tfg.usb.StorageDevice;
import es.uvigo.esei.tfg.usb.USBDevice;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;

public class SelectActionController {
	 protected static Stage primaryStage;
	    @FXML
	    private Button cancelButton;

	    @FXML
	    private Button selectButton;
	    @FXML
	    private ListView<String> actionListView;
	    ObservableList<String> StorageActions=FXCollections.observableArrayList("AnalyzeDevice","Info");
	    ObservableList<String> HUDActions =FXCollections.observableArrayList("Info");
		
		USBDevice device;

	    public static String getFXMLLocation() {
	        return "fxml/selectDevice.fxml";
	    }
	    public void perform(Stage primaryStage,USBDevice device) throws ClassNotFoundException,IOException {
	    	this.device=device;
	    	if(StorageDevice.class.isInstance(device)) {
	    		actionListView.setItems(StorageActions);
	    	}else if(HUDDevice.class.isInstance(device)) {
	    		actionListView.setItems(HUDActions);
	    	}
	    	/*actionListView.setCellFactory(lv -> {
                TextFieldListCell<String> cell = new TextFieldListCell<>();
                
                cell.setConverter(new DeviceStringConverter(devicesHash));
                return cell;
            });*/
	    	
	    }
	
	    
	    @FXML
	    void onCancel(ActionEvent event) throws IOException {
	        Launcher.goBackToHome();
	    }

	    @FXML
	    void onSelectAction(ActionEvent event) {
	        System.out.println("Seleccionado dispositivo, ver acciones");
	       /*try {
				ProcessManager processManager = ProcessManager.getIntance();
			
	        String action= actionListView.getSelectionModel().getSelectedItem();
	        if (action != null) {
	            System.out.println(action.toString());
	            processManager.executeCommand(action, device);
	            
	        } else {
	            displayInformationPopup("No ha seleccionado accion");
	        }
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	        
	        
	        String action= actionListView.getSelectionModel().getSelectedItem();
	        if (action != null) {
	            System.out.println(action.toString());
	            try {
	           if(action.equals("AnalyzeDevice")) {
	        	   
					Launcher.analizeSelectedDevice(device);
				}else if(action.equals("Info")) {
					System.out.println(action.toString());
					Launcher.InfoSelectedDevice(device);
				}
	           }catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	        } else {
	            displayInformationPopup("No ha seleccionado accion");
	        }
	        
	        
	        
	    }

	    protected static void displayInformationPopup(String messageToDisplay) {
	        displayPopup(AlertType.INFORMATION, messageToDisplay);
	    }

	    private static Optional<ButtonType> displayPopup(AlertType type, String message) {
	        Alert popup = new Alert(type, message);
	        popup.setHeaderText(null);
	        return popup.showAndWait();
	    }
	    
}

