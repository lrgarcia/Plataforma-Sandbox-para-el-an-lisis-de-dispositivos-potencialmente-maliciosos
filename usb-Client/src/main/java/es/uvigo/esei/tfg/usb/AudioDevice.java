package es.uvigo.esei.tfg.usb;

import java.io.File;
import java.util.ArrayList;

public class AudioDevice extends USBDevice {

	 public AudioDevice(String bus, String device,String deviceName, String idVendor,String idProduct){
		 super(bus,device,deviceName, idVendor, idProduct);

	 }
	 public String getType() {
		 return "AudioDevice";
	 }
	 public String toString() {
		 String toret ="";
		 toret+="AudioDevice: /n";
		 toret="Bus: "+bus+", Device:"+device+", deviceName:"+deviceName+"idVendor: "+idVendor+", idProduct: "+idProduct+"";
		 return toret;
	 }
	
}

