package main.java;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Scanner;

public class AppOptions {
    private final String OPERATING_SYSTEM = System.getProperty("os.name");
    private String defaultOutputDirectory = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + File.separator + "Allure Reports";
    private String version = "1.0a";
    private boolean firstRun;

    //todo: check if this is the applications first run, if yes do the following:
    //  [x] set operatingSystem variable
    //  [] Make sure default directory is outputted to the user
    //  [] Set output directory
    //  [] update first Run

    //todo: if no, do the following:
    // [] load OS and directory data


    public void checkFirstRun(){
        //todo: runs when progam loads, checks if first run then either sets default values and outputs it to file or loads file data

        /** 1) load file and check if firstrun flag equals false
         *  2) if firstrun is true, then set and save variables to config file
         *  3) if firstrun is false, then load variables from config file
         *  */

    }

    public void changeDirectory(Scanner i) {
        String response;
        boolean validDirectory = true;

        while(validDirectory) {
            System.out.println("The current output directory is " + getDefaultDirectory());
            System.out.println("Please enter the new directory, where unzipped files will be saved");
            response = i.nextLine().replace("\"", "");

            if (Files.exists(Paths.get(response), LinkOption.NOFOLLOW_LINKS)) {
                setDefaultDirectory(response);
                System.out.println("Default directory has been updated to: " + getDefaultDirectory());
                validDirectory = false;
            } else {
                System.out.println("\nPlease enter a valid Directory\n");
            }
        }
    }

    /**
     * returns the current value of OS
     * @return String value of defaultDirectory
     */
    public String getOperatingSystem(){
        return OPERATING_SYSTEM;
    }

    /**
     *Sets a new value for the defaultDirectory
     * @param directory String of the directory to be save unzipped files
     */
    public void setDefaultDirectory(String directory){
        defaultOutputDirectory = directory;
    }

    /**
     * returns the current value of defaultDirectory
     * @return String value of defaultDirectory
     */
    public String getDefaultDirectory(){
        return defaultOutputDirectory;
    }

    public boolean loadProperties(){
        return true;
    }

    public boolean saveProperties(){
        return true;
    }
}
