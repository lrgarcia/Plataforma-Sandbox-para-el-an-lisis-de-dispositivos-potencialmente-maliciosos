package es.uvigo.esei.tfg.usb;

import java.io.File;

public class HUDDevice extends USBDevice {
	private  String HUDType;
	 public HUDDevice(String bus, String device,String deviceName, String idVendor,String idProduct, String HUDType){
		 super(bus,device,deviceName, idVendor, idProduct);
		 this.HUDType=HUDType;
	 }
	 
	 public String getType() {
		 return "HUDDEvice";
	 }
	 public String toString() {
		 String toret ="";
		 toret+="HUDDevice: \n";
		 toret+="Bus: "+bus+", Device:"+device+", deviceName:"+deviceName+"idVendor: "+idVendor+", idProduct: "+idProduct+"";
		 return toret;
	 }
}

