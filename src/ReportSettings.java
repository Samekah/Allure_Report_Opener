import java.io.IOException;
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

		//todo: add check to see if it is a valid directory by 12/11
		System.out.println("please enter the directory of the reports zip folder:");
//		System.out.println(defaultDirectory + File.separator + "Allure Reports");
		zipFileLocation = input.nextLine();

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
//			new ProcessBuilder("cmd.exe", "/c", "start cmd.exe").start().waitFor();  // Wait for the process to finish
		} catch(IOException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

//		"C:\Users\howars19\OneDrive - Kingfisher PLC\Documents 1\Payments team\Pipeline reports\BQIE-dev_250924_2\kf-ra-gprs-tests\target\site\allure-maven-plugin"
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
						System.out.println("Please enter a valid response");
						break;
				}
			}
			else{
				if(tenant.equalsIgnoreCase("BQUK") || tenant.equalsIgnoreCase("TPUK") || tenant.equalsIgnoreCase("BQIE") || tenant.equalsIgnoreCase("CASTOFR") || tenant.equalsIgnoreCase("CASTOPL")){
					tenant = tenant.toUpperCase();
					response = false;
				}
				else{
					System.out.println("Please enter a valid response");
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
						System.out.println("Please enter a valid response");
						break;
				}
			}
			else{
				if(testStage.equalsIgnoreCase("DEV") || testStage.equalsIgnoreCase("CI") || testStage.equalsIgnoreCase("STAGE")){
					testStage = testStage.toUpperCase();
					response = false;
				}
				else{
					System.out.println("Please enter a valid response");
				}
			}
		}

		return testStage;
	}

	private void openReport(String zipFilePath,String directoryName) throws IOException, InterruptedException{
		uz.unzipFile(zipFilePath, uz.getDefaultDirectory(), directoryName);
		new ProcessBuilder("cmd.exe", "/c", "start cmd.exe").start().waitFor();
//		new ProcessBuilder("wt","-p", "Command Prompt","/c start cmd.exe" ).start().waitFor();
	}

	private boolean isNumber(String n){
		try{
			Integer.parseInt(n);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
