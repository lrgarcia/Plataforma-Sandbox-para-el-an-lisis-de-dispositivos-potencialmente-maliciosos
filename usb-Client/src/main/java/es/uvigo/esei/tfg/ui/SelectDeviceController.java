/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.ui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import es.uvigo.esei.tfg.usb.AudioDevice;
import es.uvigo.esei.tfg.usb.HUDDevice;
import es.uvigo.esei.tfg.usb.ProcessManager;
import es.uvigo.esei.tfg.usb.StorageDevice;
import es.uvigo.esei.tfg.usb.USBDevice;
//import logic.ProcessController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * /**
 *
 * @author Luis
 */
public class SelectDeviceController {

    protected static Stage primaryStage;
    @FXML
    private Button cancelButton;

    @FXML
    private Button selectButton;
    @FXML
    private Label devicesLoadingLabel;
    @FXML
    private ListView<USBDevice> deviceListView;
    

    public static String getFXMLLocation() {
        return "fxml/selectDevice.fxml";
    }

    public void perform(Stage primaryStage) throws ClassNotFoundException,IOException {
        this.primaryStage = primaryStage;
        System.out.println("Inicializando SELECT DEVICE CONTROLLER");
        primaryStage.setTitle("Select Device");
        

        Platform.runLater(new Runnable() {
            @Override
            //ARREGLAR ESA MIERDA
            public void run() {
                try {
                    System.out.println("CARGANDO!");
                    ProcessManager processManager = ProcessManager.getIntance();
                    System.out.println(processManager.toString());
                    Map<String, USBDevice> devicesHash = processManager.getDevicesFromServer();
                    System.out.println(devicesHash);
                    if(devicesHash.isEmpty()) {
                    	displayInformationPopup("No hay dispositivos");
                    	try {
							Launcher.goBackToHome();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }else {
                    ArrayList<USBDevice> devices = new ArrayList<USBDevice>(devicesHash.values());
                    devicesLoadingLabel.setVisible(true);
                    deviceListView.setCellFactory(lv -> {
                        TextFieldListCell<USBDevice> cell = new TextFieldListCell<>();
                        
                        cell.setConverter(new DeviceStringConverter(devicesHash));
                        return cell;
                    });
                    ObservableList<USBDevice> devicesObservables = FXCollections.observableArrayList();
                    devicesObservables.addAll(devices);
                    devicesLoadingLabel.setVisible(false);
                    deviceListView.setItems(devicesObservables);
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                } catch (NullPointerException n) {
                    System.out.println(n);
                }
            }
        });
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Launcher.goBackToHome();
    }

    @FXML
    void onSelectDevice(ActionEvent event) {
        System.out.println("Seleccionado dispositivo, ver acciones");
        USBDevice device = deviceListView.getSelectionModel().getSelectedItem();
        if (device != null) {
            System.out.println(device.toString());
            try{
                
            Launcher.showActionDevice(device);
            }catch(IOException e){
                System.out.println(e);
            }
        } else {
            displayInformationPopup("No ha seleccionado dispositivo");
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
    
    
    
    
    
    
    

    private static class DeviceStringConverter extends StringConverter<USBDevice> {

        private final Map<String, USBDevice> devices;

        public DeviceStringConverter(Map<String, USBDevice> devices) {
            this.devices = devices;
        }

        @Override
        /*Ojo a esto que lo estoy haciendo como si fuese un hashmap*/
        public USBDevice fromString(String name) {
            if (name == null || name == "" || !name.contains(":")) {
                return null;
            }
            USBDevice toret = devices.get(name.split(":", 1)[0]);
            return toret;
        }

        @Override
        public String toString(USBDevice device) {
            if (device == null) {
                return "";
            }
            Image IMAGE_RUBY  = new Image(SelectDeviceController.class.getResource("/icons/info.png").toString(), 64, 64, false, false);
            ImageView imageView = new ImageView();
            imageView.setImage(IMAGE_RUBY);
            
            //double bytesInGB = device.getSize() / 1073741824.0;
            /*double bytesInGB =0.0;
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            return device.getDeviceName() + ": " + device.getType() + " [" + df.format(bytesInGB) + "GB]";*/
            if(StorageDevice.class.isInstance(device)) {
            	return "[Storage Device]"+device.getDeviceName();
            }else if(HUDDevice.class.isInstance(device)) {
            	return "[HUD Device]"+device.getDeviceName();
            }else if(AudioDevice.class.isInstance(device)) {
            	return "[Audio Device]"+device.getDeviceName();
            }else {
            	return "";
            }
            
            
        }
    }

}