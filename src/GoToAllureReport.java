import java.util.Scanner;


public class GoToAllureReport {

	public static void main(String[]args){
		//todo: check which OS is being used
		//todo: check default directory
		String response;
		boolean mainMenu = true;

		Scanner input = new Scanner(System.in);
		ReportSettings rs = new ReportSettings();
		AppOptions ao = new AppOptions();

		System.out.print("Welcome to this test program, it will do the following things\n1) Take in a zip folder\n2) Create a directory for an unzipped file\n3) Unzip the folder\n4) Save in directory\n5) open file\n\n");

		while(mainMenu) {
			System.out.println("======= Menu =======");
			System.out.println("Please select one of the following options [1 / 2]:");
			System.out.println("1) Open a report\n2) change output directory\n3) Exit\n");

			response = input.nextLine();

			if (response.equals("1")) {
				rs.reportMenu(input);
			} else if (response.equals("2")) {
//				ao;
			} else if (response.equals("3") || response.equalsIgnoreCase("exit")) {
				input.close();
				mainMenu = false;
			} else {
				System.out.println("Please enter a valid response");
			}
		}

	}
}
