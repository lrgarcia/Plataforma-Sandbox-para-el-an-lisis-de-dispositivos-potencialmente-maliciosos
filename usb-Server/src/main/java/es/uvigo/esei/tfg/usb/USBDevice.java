package es.uvigo.esei.tfg.usb;
import java.io.*;
public abstract class USBDevice implements Serializable{
	protected  String deviceName;
	
	protected String bus;
	protected String device;
	protected  String idVendor;   
	protected  String idProduct;  
     
	public USBDevice(String bus, String device,String deviceName, String idVendor,String idProduct){
 		
   	 this.bus=bus;
        this.device=device;
        this.deviceName = deviceName;
     
        this.idVendor=idVendor;
        this.idProduct=idProduct;
	}
    
    public USBDevice(String bus, String device) {
   	 this.bus=bus;
        this.device=device;
    }
     
     public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}



	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getIdVendor() {
		return idVendor;
	}

	public void setIdVendor(String idVendor) {
		this.idVendor = idVendor;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public abstract String getType() ;
	
	public abstract String toString();
	
}
