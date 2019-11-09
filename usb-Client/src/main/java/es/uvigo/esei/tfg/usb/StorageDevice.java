package es.uvigo.esei.tfg.usb;

import java.io.File;
import java.util.ArrayList;

public class StorageDevice extends USBDevice {
	private  ArrayList<String> mountPoint;
	 public StorageDevice(String bus, String device,String deviceName, String idVendor,String idProduct, ArrayList<String> mountPoint){
		 super(bus,device,deviceName, idVendor, idProduct);
		 this.setMountPoint(mountPoint);
	 }
	 public String getType() {
		 return "StorageDevice";
	 }
	 public String toString() {
		 String toret ="";
		 StringBuilder str  = new StringBuilder(); 
		 str.append("StorageDevice: ");
		 str.append(System.getProperty("line.separator"));
		 str.append("Bus: "+bus+"");
		 str.append(System.getProperty("line.separator"));
		 str.append("Device: "+device);
		 str.append(System.getProperty("line.separator"));
		 str.append("Device Name: "+deviceName);
		 str.append(System.getProperty("line.separator"));
		 str.append("idVendor: "+idVendor);
		 str.append(System.getProperty("line.separator"));
		 str.append("idProduct: "+idProduct);
		 
		/* toret+="StorageDevice: /n";
		 toret="Bus: "+bus+", Device:"+device+", deviceName:"+deviceName+"idVendor: "+idVendor+", idProduct: "+idProduct+"";*/
		 toret = str.toString();
		 return toret;
	 }
	public ArrayList<String> getMountPoint() {
		return mountPoint;
	}
	public void setMountPoint(ArrayList<String> mountPoint) {
		this.mountPoint = mountPoint;
	}
}

	