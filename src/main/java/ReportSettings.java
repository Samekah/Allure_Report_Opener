package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
//import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Scanner;


public class ReportSettings {
	//[] todo: Store default location somewhere and load it if not first time using application
	//[] todo: do OS specific calls to open report
	//[] todo: add a class/ constructor to store data of program
	//[] todo: Commenting

	private final Unzipper uz = new Unzipper();
	private final AppOptions ao = new AppOptions();

	public void reportMenu(Scanner i){
		String response;
		boolean moreReports = true;
		boolean keepReportsOpen = true;

		allureReportSetup(i);

		while(moreReports){
			System.out.println("Would you like to open another report? [yes/Y] or [no/N]");
			response = i.nextLine();

			if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")){
				allureReportSetup(i);
			}
			else if(response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")){
				while(keepReportsOpen) {
					System.out.println("would you like to keep the Allure report server running? [yes/Y] or [no/N]");
					response = i.nextLine();

					if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
						moreReports = false;
						keepReportsOpen = false;
					} else if (response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")) {
						try {
							new ProcessBuilder("taskkill", "/F", "/IM", "WindowsTerminal.exe").start().waitFor();
							moreReports = false;
							keepReportsOpen = false;
						} catch (InterruptedException | IOException e) {
							throw new RuntimeException(e);
						}
					} else {
						System.out.println("Please enter a valid response");
					}
				}
				System.out.println("thank you for using this service!");
			}
			else{
				System.out.println("Please enter a valid response");
			}

		}
	}

	public void allureReportSetup(Scanner input){

		String zipFileLocation;
		String tenant;
		String testStage;
		String newDirectoryName;
		String currentTime;

//		setDirectory(input);
		zipFileLocation = askForPath(input);
		tenant = askForTenant(input);
		testStage = askForTestStage(input);

		currentTime = String.valueOf(System.currentTimeMillis());
		newDirectoryName = tenant + "-" + testStage + "_" + currentTime;
		System.out.println("File name created is: " + newDirectoryName);

		try {
			openReport(zipFileLocation,newDirectoryName);
		} catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

//	public void setDirectory(Scanner i){
//		boolean validDirectory = true;
//		String response;
//		while(validDirectory) {
//			System.out.println("Would you like to change the default directory? It is currently: \n" + uz.getDefaultDirectory() +"\n");
//			response = i.nextLine();
//			if (response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")) {
//				System.out.println("The current directory is set to: " + uz.getDefaultDirectory());
//				validDirectory = false;
//			} else if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
//				System.out.println("\nPlease enter the new directory, where unzipped files will be saved");
//				response = i.nextLine();
//				if(Files.exists(Paths.get(response), LinkOption.NOFOLLOW_LINKS)) {
//					uz.setDefaultDirectory(response);
//					System.out.println("Default directory has been updated to: \n" +uz.getDefaultDirectory());
//					validDirectory = false;
//				}
//				else{
//					System.out.println("\nPlease enter a valid Directory\n");
//				}
//
//			} else {
//				System.out.println("Please enter a valid response");
//			}
//		}
//	}

	public String askForPath(Scanner i){
		boolean validPath = true;
		String zipFileLocation = "";

		while(validPath) {
			System.out.println("please enter the directory of the reports zip folder:");
			zipFileLocation = i.nextLine().replace("\"", "");
			if (zipFileLocation.isBlank() || !Files.exists(Paths.get(zipFileLocation))) {
				System.out.println("Please enter a valid zip folder directory");
			}
			else{
				validPath = false;
			}
		}
		return zipFileLocation;
	}

	public String askForTenant(Scanner i){

		String tenant = "";
		boolean isNumber;
		boolean response = true;

		while(response){
			System.out.println("\nOut of the following, please select your chosen tenant:\n1) BQUK\n2) TPUK\n3) BQIE\n4) CASTOFR\n5) CASTOPL");
			tenant = i.nextLine();

			isNumber = isNumber(tenant);

			if(isNumber){
				switch(tenant){
					case "1":
						tenant = "BQUK";
						response = false;
						break;
					case "2":
						tenant = "TPUK";
						response = false;
						break;
					case "3":
						tenant = "BQIE";
						response = false;
						break;
					case "4":
						tenant = "CASTOFR";
						response = false;
						break;
					case "5":
						tenant = "CASTOPL";
						response = false;
						break;
					default:
						System.out.println("Please enter a valid tenant");
						break;
				}
			}
			else{
				if(tenant.equalsIgnoreCase("BQUK") || tenant.equalsIgnoreCase("TPUK") || tenant.equalsIgnoreCase("BQIE") || tenant.equalsIgnoreCase("CASTOFR") || tenant.equalsIgnoreCase("CASTOPL")){
					tenant = tenant.toUpperCase();
					response = false;
				}
				else{
					System.out.println("Please enter a valid tenant");
				}
			}
		}

		return tenant;
	}

	public String askForTestStage(Scanner i){

		String testStage = "";
		boolean isNumber;
		boolean response = true;

		while(response){
			System.out.println("\nOut of the following, please select your chosen test stage:\n1) DEV\n2) CI\n3) STAGE");
			testStage = i.nextLine();

			isNumber = isNumber(testStage);

			if(isNumber){
				switch(testStage){
					case "1":
						testStage = "DEV";
						response = false;
						break;
					case "2":
						testStage = "CI";
						response = false;
						break;
					case "3":
						testStage = "STAGE";
						response = false;
						break;
					default:
						System.out.println("Please enter a valid test stage");
						break;
				}
			}
			else{
				if(testStage.equalsIgnoreCase("DEV") || testStage.equalsIgnoreCase("CI") || testStage.equalsIgnoreCase("STAGE")){
					testStage = testStage.toUpperCase();
					response = false;
				}
				else{
					System.out.println("Please enter a valid test stage");
				}
			}
		}

		return testStage;
	}

	private void openReport(String zipFilePath,String directoryName) throws IOException, InterruptedException{
		String command = "\""+ ao.getDefaultDirectory() + File.separator + directoryName + File.separator + "target\\site\\allure-maven-plugin\"";
		boolean isRunning;
		ProcessBuilder pb;

		uz.unzipFile(zipFilePath, ao.getDefaultDirectory(), directoryName);
		isRunning = terminalRunning();

		if(!isRunning){

//			pb = new ProcessBuilder("wt","-w -1", "-d .", "-p", "Command Prompt","cmd", "/k", "allure", "open", command);
			pb = new ProcessBuilder("wt", "-w -1", "-d .", "-p", "Command Prompt");
		}
		else{
//			pb = new ProcessBuilder("wt","-d .", "-p", "Command Prompt","cmd", "/k", "allure", "open", File.separator, uz.getDefaultDirectory(), File.separator, directoryName, File.separator, "\"target\\site\\allure-maven-plugin\"");
//			 pb = new ProcessBuilder("wt", "-w 0", "nt", "-p", "Command Prompt","cmd", "/k", "allure", "open", command);
			 pb = new ProcessBuilder("wt", "-w 0", "nt", "-p", "Command Prompt");
		}

		pb.start().waitFor();

	}

	private boolean isNumber(String n){
		try{
			Integer.parseInt(n);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	//todo: update with OS specific commands
	private boolean terminalRunning(){
		//todo: pass the result of the OS check
		boolean isRunning = false;
		try {
			ProcessBuilder taskList = new ProcessBuilder("cmd.exe", "/c", "tasklist");
			Process p = taskList.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.toLowerCase().contains("windowsterminal.exe")) {
					isRunning = true;
				}
			}
			return isRunning;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
