package main.java;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class AppOptions {
    private final String APPCONFIGPATH = Thread.currentThread().getContextClassLoader().getResource("main/resources/config.properties").getPath().replace("/", "\\").replace("%20", " ").substring(1);
    private final String OPERATING_SYSTEM = System.getProperty("os.name");
    private String defaultOutputDirectory = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + File.separator + "Allure Reports";
    Properties appProps = new Properties();
    private String version;
    private boolean firstRun;

    public void checkFirstRun(){

        try {
		    appProps.load(new FileInputStream(APPCONFIGPATH));
//		    appProps.load(new FileInputStream("C:\\Users\\samek\\IdeaProjects\\Allure test\\out\\production\\Allure test\\main\\resources\\config.properties"));
	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }

        if(appProps.getProperty("firstRun").equals("true")){

            appProps.setProperty("os", OPERATING_SYSTEM);
            appProps.setProperty("defaultOutputDirectory", defaultOutputDirectory);
            appProps.setProperty("firstRun", "false");

            saveConfig("Config file created");
            System.out.println("\nThe default Output directory is: " + defaultOutputDirectory);

        }else{
            defaultOutputDirectory = appProps.getProperty("defaultOutputDirectory");
            System.out.println("\nThe current default Output directory is: " + defaultOutputDirectory);
        }
    }

    /**
     *Saves data to config file
     * @param message String for the comment of the process completed
     */
    public void saveConfig(String message){
        try {
            appProps.store(new FileWriter(APPCONFIGPATH), message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                System.out.println("\n\nPlease enter a valid Directory\n");
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
        appProps.setProperty("defaultOutputDirectory", defaultOutputDirectory);
        saveConfig("default output directory has been updated");
    }

    /**
     * returns the current value of defaultDirectory
     * @return String value of defaultDirectory
     */
    public String getDefaultDirectory(){
        return defaultOutputDirectory;
    }
}
