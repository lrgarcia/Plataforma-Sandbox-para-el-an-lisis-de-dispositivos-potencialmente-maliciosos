/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

/**
 *
 * @author Luis
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.profesorfalken.wmi4java.WMI4Java;
import com.profesorfalken.wmi4java.WMIClass;

import es.uvigo.esei.tfg.usb.SimpleTest.Pair;

import static es.uvigo.esei.tfg.usb.Tester.scanFile;
import javax.swing.filechooser.FileSystemView;

public class ListAllDevicesCommand<T extends String> implements Command<T> {

    public static final String COMMAND_NAME = "listAllDevices";

    private static final Map<Byte, String> CLASS_NAMES
            = new HashMap<Byte, String>();

    /*
    static {
        CLASS_NAMES.put(LibUsb.CLASS_PER_INTERFACE, "Per Interface");
        CLASS_NAMES.put(LibUsb.CLASS_AUDIO, "Audio");
        CLASS_NAMES.put(LibUsb.CLASS_COMM, "Communications");
        CLASS_NAMES.put(LibUsb.CLASS_HID, "HID");
        CLASS_NAMES.put(LibUsb.CLASS_PHYSICAL, "Physical");
        CLASS_NAMES.put(LibUsb.CLASS_IMAGE, "Imaging");
        CLASS_NAMES.put(LibUsb.CLASS_PRINTER, "Printer");
        CLASS_NAMES.put(LibUsb.CLASS_MASS_STORAGE, "Mass Storage");
        CLASS_NAMES.put(LibUsb.CLASS_HUB, "Hub");
        CLASS_NAMES.put(LibUsb.CLASS_DATA, "Data");
        CLASS_NAMES.put(LibUsb.CLASS_SMART_CARD, "Smart Card");
        CLASS_NAMES.put(LibUsb.CLASS_CONTENT_SECURITY, "Content Security");
        CLASS_NAMES.put(LibUsb.CLASS_VIDEO, "Video");
        CLASS_NAMES.put(LibUsb.CLASS_PERSONAL_HEALTHCARE, "Personal Healthcare");
        CLASS_NAMES.put(LibUsb.CLASS_DIAGNOSTIC_DEVICE, "Diagnostic Device");
        CLASS_NAMES.put(LibUsb.CLASS_WIRELESS, "Wireless");
        CLASS_NAMES.put(LibUsb.CLASS_APPLICATION, "Application");
        CLASS_NAMES.put(LibUsb.CLASS_VENDOR_SPEC, "Vendor-specific");
    }*/

    // @Override
    /*OUTPUT STYLE
    
Name: \\.\PHYSICALDRIVE1
SystemName: DESKTOP-40JBD9V
SerialNumber: 50026B766800CBFF
Model: KINGSTON SUV400S37120G
Size: 120031511040
TotalHeads: 255
TotalCylinders: 14593
TotalTracks: 3721215
TotalSectors: 234436545
TracksPerCylinder: 255
SectorsPerTrack: 63
BytesPerSector: 512
PNPDeviceID: SCSI\DISK&VEN_&PROD_KINGSTON_SUV400S\4&1BA11CFF&0&040000
MediaType: Fixed hard disk media*/
 /*VOLUME OUTPUT
    Name: \\?\Volume{13e369fa-8b6c-4655-923e-445fedfcec40}\
FileSystem: NTFS
Label: Recuperaci¢n
FreeSpace: 457289728
Capacity: 471855104
DriveType: 3
SerialNumber: 1787838030
DriveLetter: C:
Name: C:\
FileSystem: NTFS
Label: 
FreeSpace: 24246304768
Capacity: 118472077312
DriveType: 3
SerialNumber: 278214258
DriveLetter: 
Name: \\?\Volume{bc08f68e-3906-4e09-9218-2d4f2ee6889e}\
FileSystem: NTFS
Label: 
FreeSpace: 483504128
Capacity: 966782976
DriveType: 3
SerialNumber: 449911088
DriveLetter: 
Name: \\?\Volume{d808bc23-43ea-4c21-a25d-7c50bc3a7979}\
FileSystem: FAT32
Label: 
FreeSpace: 72989696
Capacity: 99614720
DriveType: 3
SerialNumber: -1970207659
     */
//    public String run(String[] args, DataOutputStream out) {
//        Context context = new Context();
//        String toret = "";
//
//        // Initialize the libusb context
//        int result = LibUsb.init(context);
//        if (result < 0) {
//
//            throw new LibUsbException("Unable to initialize libusb", result);
//
//        }
//
//        // Read the USB device list
//        DeviceList list = new DeviceList();
//        result = LibUsb.getDeviceList(context, list);
//        if (result < 0) {
//            throw new LibUsbException("Unable to get device list", result);
//        }
//
//        try {
//            // Iterate over all devices and list them
//            
////            for (Device device : list) {
////                int address = LibUsb.getDeviceAddress(device);
////                int busNumber = LibUsb.getBusNumber(device);
////                DeviceDescriptor descriptor = new DeviceDescriptor();
//////                String dump=descriptor.toString();
//////                System.out.println(dump);
////
////                result = LibUsb.getDeviceDescriptor(device, descriptor);
////                if (result < 0) {
////                    throw new LibUsbException(
////                            "Unable to read device descriptor", result);
////                }
////                /*System.out.format(
////                        "Bus %03d, Device %03d: Vendor %04x, Product %04x%n",
////                        busNumber, address, descriptor.idVendor(),
////                        descriptor.idProduct());*/
////                String decodedUsbClass = DescriptorUtils.decodeBCD(descriptor.bcdUSB());
////                // System.out.println( DescriptorUtils.dump(descriptor));
////
////                final String name = CLASS_NAMES.get(descriptor.bcdUSB());
////                //toret+="Bus "+busNumber+" bcdUSB "+decodedUsbClass+" Device "+address+" Vendor "+descriptor.idVendor()+" Product "+descriptor.idProduct()+"\n";
////                toret += getWin32DiskDrives();
////                //System.out.println(toret);
////            }
//               toret += getWin32DiskDrives();
//               toret +=scanForVolumes();
//            out.writeUTF(toret);
//            return toret;
//        } catch (IOException i) {
//            System.out.println(i);
//        } finally {
//            // Ensure the allocated device list is freed
//            LibUsb.freeDeviceList(list, true);
//        }
//
//        // Deinitialize the libusb context
//        LibUsb.exit(context);
//        return toret;
//    }
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
	
	
    public String run(T args, ObjectOutputStream out) {
    	String s;
		Process p;
		ArrayList<USBDevice>USBDevices=new ArrayList<>();
		 Map<String, USBDevice> devicesHash = new HashMap<>();
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
					devicesHash.put(deviceName,storageDevice);
					
					
				}if(nameInterfaceClass.equals("Human Interface Device")) {
					String idVendor= idVendorHex.substring(2);
					String idProduct= idProductHex.substring(2);
					
					HUDDevice hudDevice= new HUDDevice(bus, device, deviceName,idVendor,idProduct,"");
					USBDevices.add(hudDevice);
					devicesHash.put(deviceName,hudDevice);
					
				}
				
				 
				System.out.println("///////////////////////////////////////////////");
			}

			//System.out.println("SAlida del blucle");

			// https://superuser.com/questions/1114911/mount-location-by-vidpid-of-a-device

			//https://www.keil.com/pack/doc/mw/USB/html/_u_s_b__endpoint__descriptor.html
			//https://www.beyondlogic.org/usbnutshell/usb5.shtml
			
			p.waitFor();
			System.out.println("exit: " + p.exitValue());
			p.destroy();
			try{
                System.out.println("Intentando enviar respuesta al cliente");  
         
                for (Map.Entry<String, USBDevice> entry : devicesHash.entrySet()) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                }
                out.writeObject(devicesHash);
                out.flush();
                }catch(IOException i){
                    System.out.println(i);
                    
                }
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";}
	
	
    	
    
    
    
    
 
    
    
    
    
    
    
    
    
    
    
    
   // public String run(T args, ObjectOutputStream out) {
    	/*
    	FileSystemView fsv = FileSystemView.getFileSystemView();
        //List<Device> devices = new ArrayList<Device>();
        Map<String, Device> devices = new HashMap<>();
       
        *//*
        File[] drives = File.listRoots();
        String toret="Dispositivos conectados actualmente: \n";
       *//*
        if (drives != null && drives.length > 0) {
            for (File aDrive : drives) {
                System.out.println("Name: " + fsv.getSystemDisplayName(aDrive));
                //System.out.println("No esta vacio: " + aDrive != null);
                System.out.println("Es un directorio: " + aDrive.isDirectory());
                System.out.println("Drive Letter: " + aDrive);
                System.out.println("\tType: " + fsv.getSystemTypeDescription(aDrive));
                System.out.println("\tTotal space: " + aDrive.getTotalSpace());
                System.out.println("\tFree space: " + aDrive.getFreeSpace());
                System.out.println("\tCan Read: " + aDrive.canRead());
                System.out.println();
                if(aDrive != null && aDrive.isDirectory()){
                
                Device device = new Device(aDrive,aDrive.getPath(),fsv.getSystemTypeDescription(aDrive),aDrive.getFreeSpace());
                devices.put(fsv.getSystemDisplayName(aDrive),device);
                toret+=device.toString();
                }*/
                /*
                if (aDrive.canRead()) {
                    File fileName = new File(aDrive.getPath());
                    File[] fileList = fileName.listFiles();
                    try {
                        for (File file : fileList) {

                            System.out.println(file);
                        }
                    } catch (Exception e) {
                        System.out.println("No se puede leer");
                    }
                }*/
                
           /* }
        }
        try{
                System.out.println("Intentando enviar respuesta al cliente");    
                out.writeObject(devices);
                out.flush();
                }catch(IOException i){
                    System.out.println(i);
                    
                }

        return toret;
    }*/

    protected String getWin32DiskDrives() {
        String queryResult;
        queryResult = WMI4Java.get().VBSEngine()
                .properties(Arrays.asList("Name", "DeviceID", "SystemName", "SerialNumber", "Model", "Size", "TotalHeads",
                        "TotalCylinders", "TotalTracks", "TotalSectors", "TracksPerCylinder", "SectorsPerTrack",
                        "BytesPerSector", "PNPDeviceID", "MediaType"))
                .getRawWMIObjectOutput(WMIClass.WIN32_DISKDRIVE);
        return queryResult;
    }

    /*
    private List<Volume> scanForVolumes() {
        List<Volume> volumes = new ArrayList<>();
        String queryResult = WMI4Java.get().VBSEngine().properties(Arrays.asList("DriveLetter", "Name", "FileSystem",
                "Label", "FreeSpace", "Capacity", "DriveType", "SerialNumber")).getRawWMIObjectOutput("Win32_Volume");

        for (String result : queryResult.split("DriveLetter")) {
            if (result.length() > 0) {
                Volume volume = new Volume(result);
                volumes.add(volume);
            }

        }
        return volumes;
    }*/
    protected String scanForVolumes() {

        String queryResult = WMI4Java.get().VBSEngine().properties(Arrays.asList("DriveLetter", "Name", "FileSystem",
                "Label", "FreeSpace", "Capacity", "DriveType", "SerialNumber")).getRawWMIObjectOutput("Win32_Volume");
        return queryResult;

    }

    protected String scanForDevicesUSB() {

        String queryResult = WMI4Java.get().VBSEngine().properties(Arrays.asList("Name", "SystemName", "Description",
                "DeviceId")).getRawWMIObjectOutput("Win32_USBController");
        return queryResult;

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String info() {
        return "Devuelve weas";
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
