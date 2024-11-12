import java.io.IOException;
import java.util.Scanner;

public class ReportSettings {
	//todo: Look into allowing user to define new default location
	//todo: Store default location somewhere and load it if not first time using application
	//todo: Give user the option to change default location
	//Todo: add method to close terminal window
	//Todo: update choices to allow number options - quicker
	//Todo: add if statement that is not case sensitive

	private final Unzipper uz = new Unzipper();

	public void allureReportSetup(Scanner input){

		String zipFileLocation = "";
		String tenant = "";
		String testStage = "";
		String newDirectoryName = "";
		String currentTime = "";

		System.out.println("please enter the directory of the reports zip folder:");

//		System.out.println(defaultDirectory + File.separator + "Allure Reports");
		zipFileLocation = input.nextLine();

		System.out.println("\nOut of the following, please select your chosen tenant:\n-> BQUK\n-> TPUK\n-> BQIE\n-> CASTOFR\n-> CASTOPL");
		tenant = input.nextLine();

		System.out.println("\nOut of the following, please select your chosen test stage:\n-> dev\n-> ci\n-> stage");
		testStage = input.nextLine();

		currentTime = String.valueOf(System.currentTimeMillis());
		newDirectoryName = tenant + "-" + testStage + "_" + currentTime;
		System.out.println("File name created is: " + newDirectoryName);

		//Todo: Move to separate method and complete opening process of report
		//Todo: add initial check to see if a wt is open, if so add tab if not new window
		//Todo: Check if processbuilder works for mac


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

	private void openReport(String zipFilePath,String directoryName) throws IOException, InterruptedException{
		uz.unzipFile(zipFilePath, uz.getDefaultDirectory(), directoryName);
		new ProcessBuilder("cmd.exe", "/c", "start cmd.exe").start().waitFor();
//		new ProcessBuilder("wt","-p", "Command Prompt","/c start cmd.exe" ).start().waitFor();
	}
}
