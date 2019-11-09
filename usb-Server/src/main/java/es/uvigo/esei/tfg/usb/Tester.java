/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

import com.profesorfalken.wmi4java.WMI4Java;
import com.profesorfalken.wmi4java.WMIClass;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileSystemView;
import com.kanishka.net.commons.BasicHTTPRequestImpl;
import com.kanishka.net.commons.HTTPRequest;
import com.kanishka.net.exception.RequestNotComplete;
import com.kanishka.net.model.MultiPartEntity;
import com.kanishka.net.model.RequestMethod;
import com.kanishka.net.model.Response;
import com.kanishka.virustotal.dto.DomainReport;
import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.GeneralResponse;
import com.kanishka.virustotal.dto.IPAddressReport;
import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.dto.*;
import com.kanishka.virustotal.exception.*;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

import com.kanishka.virustotal.exception.QuotaExceededException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;

/**
 *
 * @author Luis
 */
public class Tester {

    private static List<String> joinDriveVolumeString(String queryResult) {
        String TWOLINE_SPLIT_PATTERN = "(\\R)";
        String[] volumeInfoSingle = queryResult.split(TWOLINE_SPLIT_PATTERN);
        List<String> partitionDrivePairs = new ArrayList<>();
        String temp = "";
        for (String volume : volumeInfoSingle) {
            //System.out.println(volume);
            if (temp.length() == 0) {
                temp = volume;
            } else {
                partitionDrivePairs.add(temp + volume);
                temp = "";
            }
        }
        return partitionDrivePairs;
    }

    public static void scanFile(File file) {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("4afcf94d0544956187e78328feb315287cb5b6ec3ccbc596c8a46c05a1236319");
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();
            System.out.println("Analizando: "+file);
            //ScanInfo scanInformation = virusTotalRef.scanFile(new File("C:\\Users\\Luis\\Downloads\\wildfire-test-pe-file.exe"));
            ScanInfo scanInformation = virusTotalRef.scanFile(file);
            /*
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
            System.out.println("done.");*/
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
/*
            System.out.println("MD5 :\t" + report.getMd5());
            System.out.println("Perma link :\t" + report.getPermalink());
            System.out.println("Resourve :\t" + report.getResource());
            //System.out.println("Scan Date :\t" + report.getScan_date());
            //System.out.println("Scan Id :\t" + report.getScan_id());
            System.out.println("SHA1 :\t" + report.getSha1());
            System.out.println("SHA256 :\t" + report.getSha256());
          //  System.out.println("Verbose Msg :\t" + report.getVerbose_msg());
            //System.out.println("Response Code :\t" + report.getResponse_code());*/
            System.out.println("Positives :\t" + report.getPositives());
            System.out.println("Total :\t" + report.getTotal());
/*
            Map<String, VirusScanInfo> scans = report.getScans();
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

    public static void dirTree(File dir) {
        File[] subdirs = dir.listFiles();
        for (File subdir : subdirs) {
            if (subdir.isDirectory()) {
                System.out.println("Accediendo a subdirectorio "+subdir );
                dirTree(subdir);
            } else {
                System.out.println("Accediendo al archivo "+subdir);
                scanFile(subdir);
            }
        }
    }

    public static void analizeFile(File file) {
        //System.out.println(file.getAbsolutePath());
        System.out.println("Analizando archivo: ");
    }

    public static final void main(String[] args) {

        ListAllDevicesCommand command = new ListAllDevicesCommand();
        String diskDrive = command.getWin32DiskDrives();

        //System.out.println(diskDrive);
        String USBDevices = command.scanForDevicesUSB();
        // System.out.println(USBDevices);
        String queryResult = WMI4Java.get().VBSEngine().properties(Arrays.asList("Antecedent", "Dependent"))
                .getRawWMIObjectOutput(WMIClass.WIN32_LOGICALDISKTOPARTITION);
        // System.out.println(queryResult);
        List<String> partitionDrivePairs;
        partitionDrivePairs = joinDriveVolumeString(queryResult);
        for (String path : partitionDrivePairs) {
            //System.out.println(path);
        }

        FileSystemView fsv = FileSystemView.getFileSystemView();

        File[] drives = File.listRoots();
        if (drives != null && drives.length > 0) {
            for (File aDrive : drives) {
                System.out.println("Name: " + fsv.getSystemDisplayName(aDrive));
                System.out.println("Drive Letter: " + aDrive);
                System.out.println("\tType: " + fsv.getSystemTypeDescription(aDrive));
                System.out.println("\tTotal space: " + aDrive.getTotalSpace());
                System.out.println("\tFree space: " + aDrive.getFreeSpace());
                System.out.println("\tCan Read: " + aDrive.canRead());
                System.out.println();
                if (aDrive.canRead() && !"C:\\".equals(aDrive.toString())) {
                    File fileName = new File(aDrive.getPath());
                    System.out.println(fileName);
                    dirTree(fileName);
                    File[] fileList = fileName.listFiles();
                    try {
                        for (File file : fileList) {
                           // dirTree(file);  
                            
                           /* 
                            if (file.isDirectory()) {
                                System.out.println(file+" Es directorio");
                            } else {
                                System.out.println(file+" No es directorio");
                            }
                            */
                        }
                    } catch (Exception e) {
                        System.out.println("No se puede leer");
                    }
                }
                
            }
        }
       // scanFile();

    }

}
