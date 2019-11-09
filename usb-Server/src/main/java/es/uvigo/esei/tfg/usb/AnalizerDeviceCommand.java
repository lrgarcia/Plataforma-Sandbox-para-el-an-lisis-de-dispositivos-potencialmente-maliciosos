    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

import com.github.vatbub.VirusTotalMojo;

import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.dto.VirusScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Luis
 */
public class AnalizerDeviceCommand <T extends USBDevice> implements Command<T> {

    public static final String COMMAND_NAME = "analizerDevice";
    public static ObjectOutputStream out;
    private static final Map<Byte, String> CLASS_NAMES
            = new HashMap<Byte, String>();
    
    
        public String run(T device, ObjectOutputStream out) {
         this.out=out;
         StorageDevice storageDevice = (StorageDevice) device;
         ArrayList<String>mountPoints= storageDevice.getMountPoint();
         for(String mountPoint: mountPoints) {
        	 File file = new File (mountPoint);
        	 dirTree(file);
         }
       
        
        sentToClient("Analisis terminado!");
         return "";
        }
        
        
        
         public static void dirTree(File dir) {
        File[] subdirs = dir.listFiles();
        for (File subdir : subdirs) {
            if (subdir.isDirectory()) {
                System.out.println("Accediendo a subdirectorio "+subdir );
                sentToClient("Accediendo a subdirectorio "+subdir);
                dirTree(subdir);
            } else {
                System.out.println("Accediendo al archivo "+subdir);
                sentToClient("Accediendo al archivo "+subdir);
                
                scanFile(subdir);
            }
        }
    }
     
         
         public static void sentToClient(String output){
             
             try{
              //  System.out.println("Intentando enviar respuesta al cliente");    
                out.writeObject(output);
                out.flush();
                }catch(IOException i){
                    System.out.println(i);
                    
                }

             
         }
     public static void scanFile(File file) {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("4afcf94d0544956187e78328feb315287cb5b6ec3ccbc596c8a46c05a1236319");
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            ScanInfo scanInformation = virusTotalRef.scanFile(file);
            
            System.out.println("___SCAN INFORMATION___");
            System.out.println("MD5 :\t" + scanInformation.getMd5());
            System.out.println("Perma Link :\t" + scanInformation.getPermalink());
            System.out.println("Resource :\t" + scanInformation.getResource());
            System.out.println("Scan Date :\t" + scanInformation.getScanDate());
            System.out.println("Scan Id :\t" + scanInformation.getScanId());
            System.out.println("SHA1 :\t" + scanInformation.getSha1());
            System.out.println("SHA256 :\t" + scanInformation.getSha256());
            System.out.println("Verbose Msg :\t" + scanInformation.getVerboseMessage());
            System.out.println("Response Code :\t" + scanInformation.getResponseCode());
            System.out.println("done.");
            getFileScanReport(scanInformation.getResource());
            
        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        }
    }
      public static void getFileScanReport(String resource) {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("4afcf94d0544956187e78328feb315287cb5b6ec3ccbc596c8a46c05a1236319");
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

           // String resource="275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f";
            FileScanReport report = virusTotalRef.getScanReport(resource);

            System.out.println("MD5 :\t" + report.getMd5());
            System.out.println("Perma link :\t" + report.getPermalink());
            System.out.println("Resourve :\t" + report.getResource());
            //System.out.println("Scan Date :\t" + report.getScan_date());
            //System.out.println("Scan Id :\t" + report.getScan_id());
            System.out.println("SHA1 :\t" + report.getSha1());
            System.out.println("SHA256 :\t" + report.getSha256());
            //System.out.println("Verbose Msg :\t" + report.getVerbose_msg());
            //System.out.println("Response Code :\t" + report.getResponse_code());
      //------------------------------------------------------------------------------     
            System.out.println("Positives :\t" + report.getPositives());
            sentToClient("Positives :\t" + report.getPositives());
           
            System.out.println("Total :\t" + report.getTotal());
            sentToClient("Total :\t" + report.getTotal());
       //------------------------------------------------------------------------------          
         /*   Map<String, VirusScanInfo> scans = report.getScans();
            for (String key : scans.keySet()) {
                VirusScanInfo virusInfo = scans.get(key);
                System.out.println("Scanner : " + key);
                System.out.println("\t\t Resut : " + virusInfo.getResult());
                System.out.println("\t\t Update : " + virusInfo.getUpdate());
                System.out.println("\t\t Version :" + virusInfo.getVersion());
            }*/

        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        }
    }
        
        
        
        
      @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String info() {
        return "Devuelve weas";
    }     
}