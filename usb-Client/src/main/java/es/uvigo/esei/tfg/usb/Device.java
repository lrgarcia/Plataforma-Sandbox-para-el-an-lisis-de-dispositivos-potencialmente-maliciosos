
package es.uvigo.esei.tfg.usb;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.*;
import java.io.*;
import java.util.*;


import java.io.File;
import javax.swing.filechooser.FileSystemView;


public class Device implements Serializable {
	private final File rootDirectory;
	private final String deviceName;
	private final String type;
        private final long size;
	public Device(final File rootDirectory, String deviceName, String type, Long size){
		if(rootDirectory == null || !rootDirectory.isDirectory()){
			throw new IllegalArgumentException("Invalid root file!");
		}
		
		this.rootDirectory = rootDirectory;
        
        if(deviceName == null || deviceName.isEmpty()) {
            deviceName = rootDirectory.getName();
        }
        
        this.deviceName = deviceName;
        this.type=type;
                this.size=size;
	}
/*
    public Device(final File rootDirectory){
        this(rootDirectory, null);
	}*/
    
	public File getRootDirectory() {
		return rootDirectory;
	}

    /**
     * 
     * @return the name of the  USB storage device
     */
	public String getDeviceName() {
	
    	return deviceName;
	}
        public String getType() {
	
    	return type;
	}
        public Long getSize() {
	
    	return size;
	}
    
   
    public boolean canRead() {
        return rootDirectory.canRead();
    }
    
  
    public boolean canWrite() {
        return rootDirectory.canWrite();
    }
    
 
    public boolean canExecute() {
        return rootDirectory.canExecute();
    }
    
  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.rootDirectory != null ? this.rootDirectory.hashCode() : 0);
        hash = 89 * hash + (this.deviceName != null ? this.deviceName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Device other = (Device) obj;
        return this.rootDirectory == other.rootDirectory || (this.rootDirectory != null && this.rootDirectory.equals(other.rootDirectory));
    }

	@Override
	public String toString() {
		return "RemovableDevice [Root=" + rootDirectory + ", Device Name=" + deviceName
				+ "]";
	}	
}