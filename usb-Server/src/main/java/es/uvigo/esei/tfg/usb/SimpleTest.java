/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

/**
 *
 * @author Luis
 */
/*
 * COMANDOS INTERESANTES: lsusb -v | grep -Ei '(iProduct)' lsusb -D
 * /dev/bus/usb/003/003 | grep -Ei '(bInterfaceClass )'
 */

public class SimpleTest {
	
	public static ArrayList<String> getMountPoint(String idVendor, String idProduct) {
		String s;
		Process p;
		ArrayList<String> toret=new ArrayList<>();
		 ArrayList<String> devs = new ArrayList<String>();
		try {
			p = Runtime.getRuntime().exec("udevadm trigger -v -n -s block -p ID_VENDOR_ID="+idVendor+" -p ID_MODEL_ID="+idProduct+"");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((s = br.readLine()) != null) {
				
				Pattern endDirectoryPattern = Pattern.compile("[a-zA-Z0-9]*$");
				Matcher mEndDirectory=endDirectoryPattern.matcher(s);
				if(mEndDirectory.find()) {
					System.out.println(mEndDirectory.group());
					devs.add(mEndDirectory.group());
				}
				//System.out.println(s);
			}
			p.waitFor();
			p.destroy();
			for(String dev: devs){
				p = Runtime.getRuntime().exec("findmnt -n -o TARGET /dev/"+dev);
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((s = br.readLine()) != null) {
					System.out.println(s);
					if(!s.equals("")) {
						toret.add(s);
					}
				}
				p.waitFor();
				p.destroy();
			}
			
		return toret;
		} catch (Exception e) {
			System.out.println(e);
			return toret;
			
		}

	}
	public static ArrayList<String> getInterfaceProtocol(String idVendor, String idProduct){
		String s;
		Process p;
		ArrayList<String> toret=new ArrayList<>();
		try {
			p = Runtime.getRuntime().exec("udevadm trigger -v -n -s block -p ID_VENDOR_ID="+idVendor+" -p ID_MODEL_ID="+idProduct+"");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((s = br.readLine()) != null) {
				
				
				//System.out.println(s);
			}
			p.waitFor();
			p.destroy();
			
			
		return toret;
		} catch (Exception e) {
			System.out.println(e);
			return toret;
			
		}
		//return toret;
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		String s;
		Process p;
		ArrayList<USBDevice>USBDevices=new ArrayList<>();
		List<Pair<String, String>> listPair = new ArrayList<>();
		// list.add(new Pair<String,String>("a","a"));
		try {
			p = Runtime.getRuntime().exec("lsusb");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((s = br.readLine()) != null) {
				/// System.out.println("line: " + s);

				String[] parts = s.split(" ");
				String bus = parts[1];

				String device = parts[3];

				device = device.replace(":", "");

				System.out.println("Bus: " + bus + " Device: " + device);
				Pair<String, String> par = new Pair<>(bus, device);
				listPair.add(par);
			} // Fin de lsusb

			p.waitFor();
			// System.out.println ("exit: " + p.exitValue());
			p.destroy();

			for (Pair<String, String> par : listPair) {
				boolean firstClase=false;
				  /*String exec="lsusb -D /dev/bus/usb/"+par.getL()+"/"+par.getR()
				  +" | grep -Eim1 '(iProduct)'";*/
				  
				  
				  //String[] cmd = { "/bin/sh", "-c", exec };
				  //String cmd = "lsusb -v";
				 
				/*par.setL("003");
				par.setR("002");*/
				String cmd = "lsusb -v -s " + par.getL() + ":" + par.getR();

				// https://gist.github.com/naedanger/8998722
				p = Runtime.getRuntime().exec(cmd);
				String bus= par.getL();
				String device=par.getR();
				
				String stringIdVendor = "idVendor\\s+0x\\w+ (.*)";
				String stringIdProduct = "idProduct\\s+0x\\w+ (.*)";
				String deviceName="";
				String stringIProduct = "iProduct\\s+\\w+ (.+)";
				String stringInterfaceClass = "bInterfaceClass\\s+\\w+ (.+)";
				String hex = "0x[0-9A-Fa-f]{1,4}";
				Pattern hexPattern = Pattern.compile(hex);

				Pattern patternIdVendor = Pattern.compile(stringIdVendor);
				Pattern patternIdProduct = Pattern.compile(stringIdProduct);
				//Pattern patternIProduct = Pattern.compile(stringIProduct);
				
				Pattern patternInterfaceClass = Pattern.compile(stringInterfaceClass);
				String idVendorHex = "";
				String idProductHex = "";
				//String iProduct = "";
				String nameIdVendor = "";
				String nameIdProduct = "";
				String nameInterfaceClass="";
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((s = br.readLine()) != null) {
					// System.out.println(s);
					try {
						//System.out.println(s);
						Matcher mIdVendor = patternIdVendor.matcher(s);
						Matcher mIdProduct = patternIdProduct.matcher(s);
					//	Matcher mIProduct = patternIProduct.matcher(s);
						Matcher mInterfaceClass = patternInterfaceClass.matcher(s);
						//System.out.println(mIdProduct.find());
						//System.out.println(mIProduct.find());
						// System.out.println(m.find());
						//////////////////// MATCHER ID VENDOR///////////////////////
						if (mIdVendor.find()) {

							// System.out.println(mIdVendor.group());
							String regexVendor = "((idVendor) *0x[0-9A-Fa-f]{1,4} *)";
							nameIdVendor = mIdVendor.group().replaceAll(regexVendor, "");
							// System.out.println("AAAAAAAAAAAAAAAAAAAAA: "+name);

							Matcher mHexVendor = hexPattern.matcher(mIdVendor.group());
							if (mHexVendor.find()) {

								// System.out.println("Hex idVendor: " + mHexVendor.group());
								idVendorHex = mHexVendor.group();
							}

						}

						//////////////////// MATCHER ID PRODUCT///////////////////////
						if (mIdProduct.find()) {
							String regexProduct = "((idProduct) *0x[0-9A-Fa-f]{1,4} *)";
							nameIdProduct = mIdProduct.group().replaceAll(regexProduct, "");

							// System.out.println(mIdProduct.group());
							Matcher mHexProduct = hexPattern.matcher(mIdProduct.group());
							if (mHexProduct.find()) {

								// System.out.println("Hex idProduct " + mHexProduct.group());
								idProductHex = mHexProduct.group();
							}
						}
						
						
						
						/*if (mIProduct.find()) {
							//System.out.println("aaaaaaaaaaaaaa"+mIProduct.group());
							iProduct= mIProduct.group();
						}*/
						
						
						
						if(mInterfaceClass.find()&&!firstClase) {
							String regexInterfaceClass = "((bInterfaceClass) *[0-9] *)";
							nameInterfaceClass = mInterfaceClass.group().replaceAll(regexInterfaceClass, "");
							System.out.println(mInterfaceClass.group());
							
							firstClase=true;
						}
						
						
					} catch (Exception e) {

						System.out.println("No hay nada");
					}

					// System.out.println(s);
					/*
					 * if(s.contains("Audio")) {
					 * System.out.println("Bus: "+par.getL()+" Device: "+par.getR()+" es un Audio");
					 * }else if(s.contains("Audio")) {
					 * 
					 * }
					 */
				}
				System.out.println("/////////////////////////////////////////////////////");
				p.waitFor();
				// System.out.println ("exit: " + p.exitValue());
				p.destroy();

				System.out.println("////////////RESULTADOS//////////////////////");
				System.out.println("IdVendor= " + idVendorHex);
				System.out.println("IdProduct= " + idProductHex);
				//System.out.println("IProduct= " + iProduct);
				if(nameIdVendor!=""||nameIdProduct!="") {
					
					System.out.println("Nombre: " + nameIdVendor + " " + nameIdProduct);
					 deviceName=nameIdVendor+" "+nameIdProduct;
				}else {
					System.out.println("Nombre: no disponible");
				}
				System.out.println("Tipo de dispositivo= "+nameInterfaceClass);
				
				System.out.println("");
				/*Cositas que se pueden hacer:
				 * Despues de esto se puede crear un array de USBDevice y que tenga diferentes instancias
				 * en funcion del tipo de USB(StorageDEvice, HUDDevice.... etc etc)
				 * 
				 * 
				 * 
				 * 
				 * Bien por otro lado yo devuelvo un Map <String,Device>. No es una buena medida del todo
				 * asi que mejor que pase un Array de USBDevice, habria que decidir algun identificador unico
				 * para la seleccion*/
				if(nameInterfaceClass.equals("Mass Storage")) {
					System.out.println("ES UN MASS STORAGE");
					String idVendor= idVendorHex.substring(2);
					String idProduct= idProductHex.substring(2);
					System.out.println(idVendor);
					ArrayList<String> mountsPoints=getMountPoint(idVendor,idProduct);
					for(String mountPoint:mountsPoints) {
						System.out.println("Punto de montaje: "+ mountPoint);
					}
					
					StorageDevice storageDevice= new StorageDevice(bus, device, deviceName,idVendor, idProduct,mountsPoints);
					USBDevices.add(storageDevice);
					
				}
				
				if(nameInterfaceClass.equals("Human Interface Device")) {
					String idVendor= idVendorHex.substring(2);
					String idProduct= idProductHex.substring(2);
					
					HUDDevice hudDevice= new HUDDevice(bus, device, deviceName,idVendor,idProduct,"");
					USBDevices.add(hudDevice);
					
				}
				
				
					
				System.out.println("///////////////////////////////////////////////");
			}

			System.out.println("SAlida del blucle");

			// https://superuser.com/questions/1114911/mount-location-by-vidpid-of-a-device

			//https://www.keil.com/pack/doc/mw/USB/html/_u_s_b__endpoint__descriptor.html
			//https://www.beyondlogic.org/usbnutshell/usb5.shtml
			
			p.waitFor();
			System.out.println("exit: " + p.exitValue());
			p.destroy();
			for(USBDevice device: USBDevices) {
				System.out.println(device.toString());
			}
			
			
			
			System.out.println("-------------Pruebas de listener keyboard-------");
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static class Pair<L, R> {
		private L l;
		private R r;

		public Pair(L l, R r) {
			this.l = l;
			this.r = r;
		}

		public L getL() {
			return l;
		}

		public R getR() {
			return r;
		}

		public void setL(L l) {
			this.l = l;
		}

		public void setR(R r) {
			this.r = r;
		}
	}

}
