import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GoToAllureReport {

	public static void main(String[]args) throws IOException, InterruptedException {

		String response = "";
		boolean moreReports = true;
		Scanner input = new Scanner(System.in);
		ReportSettings rs = new ReportSettings();

		System.out.print("Welcome to this test program, it will do the following things\n1) Take in a zip folder\n2) Create a directory for an unzipped file\n3) Unzip the folder\n4) Save in directory\n5) open file\n\n");
		rs.allureReportSetup(input);

		while(moreReports){
			System.out.println("Would you like to open another report? [yes/Y] or [no/N]");
			response = input.nextLine();

			if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")){
				rs.allureReportSetup(input);
			}
			else if(response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")){
				System.out.println("would you like to keep the Allure report server running? [yes/Y] or [no/N]");
				response = input.nextLine();

				if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")){
					rs.allureReportSetup(input);
					moreReports = false;
				}
				else if(response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")){
					try {
						new ProcessBuilder("taskkill", "/F", "/IM", "cmd.exe").start().waitFor();
						System.out.println("thank you for using this service!");
						input.close();
						moreReports = false;
					}catch (InterruptedException | IOException e) {
						throw new RuntimeException(e);
					}
				}
				else{
					System.out.println("Please enter a valid response");
				}

				System.out.println("thank you for using this service!");
				input.close();
			}
			else{
				System.out.println("Please enter a valid response");
			}

		}

	}
}
