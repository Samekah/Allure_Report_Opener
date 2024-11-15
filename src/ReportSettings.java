import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class ReportSettings {
	//todo: Look into allowing user to define new default location
	//todo: Store default location somewhere and load it if not first time using application
	//todo: Give user the option to change default location
	//todo: add method to close terminal window
	//todo: Commenting

	private final Unzipper uz = new Unzipper();

	public void allureReportSetup(Scanner input){

		String zipFileLocation;
		String tenant;
		String testStage;
		String newDirectoryName;
		String currentTime;

		zipFileLocation = askForPath(input);
		tenant = askForTenant(input);
		testStage = askForTestStage(input);

		currentTime = String.valueOf(System.currentTimeMillis());
		newDirectoryName = tenant + "-" + testStage + "_" + currentTime;
		System.out.println("File name created is: " + newDirectoryName);

		//todo: Move to separate method and complete opening process of report
		//todo: add initial check to see if a wt is open, if so add tab if not new window
		//todo: Check if processbuilder works for mac
		try {
			openReport(zipFileLocation,newDirectoryName);
		} catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}

//		"C:\Users\howars19\OneDrive - Kingfisher PLC\Documents 1\Payments team\Pipeline reports\BQIE-dev_250924_2\kf-ra-gprs-tests\target\site\allure-maven-plugin"
	}

	public String askForPath(Scanner i){
		boolean validPath = true;
		String zipFileLocation = "";

		while(validPath) {
			System.out.println("please enter the directory of the reports zip folder:");
			zipFileLocation = i.nextLine();
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
		uz.unzipFile(zipFilePath, uz.getDefaultDirectory(), directoryName);
		ProcessBuilder pb = new ProcessBuilder();

		if(!terminalRunning()){
	//		pb = new ProcessBuilder("wt","-d .", "-p", "Command Prompt","cmd", "/k", "allure", "open", File.separator, uz.getDefaultDirectory(), File.separator, directoryName, File.separator, "\"target\\site\\allure-maven-plugin\"");
			pb = new ProcessBuilder("wt", "-w -1", "-d .", "-p", "Command Prompt");
		}
		else{
//			pb = new ProcessBuilder("wt","-d .", "-p", "Command Prompt","cmd", "/k", "allure", "open", File.separator, uz.getDefaultDirectory(), File.separator, directoryName, File.separator, "\"target\\site\\allure-maven-plugin\"");
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

	private boolean terminalRunning(){
		boolean isRunning = false;
		try {
			ProcessBuilder taskList = new ProcessBuilder("cmd.exe", "/c", "tasklist");
			Process p = taskList.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				// Check if the line contains 'WindowsTerminal.exe'
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
