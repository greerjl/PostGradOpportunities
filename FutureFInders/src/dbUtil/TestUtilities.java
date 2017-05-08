package dbUtil;
/**
 * This program is used to test the Utilities class
 */
 
// You need to import the java.sql package to use JDBC
import java.sql.*; 
 
import java.util.Scanner;

/**
 * @author Dr. Blaha
 * @author FutureFinders367
 */
public class TestUtilities {

	// Global variables
	static Utilities testObj = new Utilities(); 		// Utilities object for testing
	static Scanner keyboard = new Scanner(System.in); 	// standard input

	public static void main(String[] args) throws SQLException {

		// variables needed for menu
		int choice;
		boolean done = false;

		while (!done) {
			System.out.println();
			displaymenu();
			choice = getChoice();
			switch (choice) {
			case 1: {
				openDefault();
				break;
			}
			/*USE CASE 1*/
			case 2: {
				callAddJob();
				break;
			}
			/*USE CASE 2*/
			case 3: {
				callGetAllFullTimeJobs();
				break;
			}
			/*USE CASE 3*/
			case 4: {
				callInsertBookmark();
				break;
			}
			/*USE CASE 4*/
			case 5: {
				callUpdateTuitionCost();
				break;
			}
			/*USE CASE 5*/
			case 6: {
				callGetUserBookmarks();
				break;
			}
			/*USE CASE 6*/
			case 7: {
				callDeleteOpportunity();
				break;
			}
			case 8: {
				break;
			}
			case 9: {
				callGetCompId();
				break;
			}
			case 10: {
				testObj.closeDB(); //close the DB connection 
				break;
			}
			case 11: {
				done = true;
				System.out.println("Good bye");
				break;
			}

			}// switch
		}

	}// main

	static void displaymenu() {
		System.out.println("1)  open default DB");
		System.out.println("2)  call addJob(String, float, String, String, date)");
		System.out.println("3)  call getAllFullTimeJobs()");
		System.out.println("4)  call addBookmark(int, int)");
		System.out.println("5)  call updateTuitionCost(double, int)");
		System.out.println("6)  call getUserBookmarks(int)");
		System.out.println("7)  call deleteJob(int)");
		System.out.println("9)  call getCompId(String name)");
		System.out.println("10) call closeDB()");
		System.out.println("11) quit");
	}

	static int getChoice() {
		String input;
		int i = 0;
		while (i < 1 || i > 11) {
			try {
				System.out.print("Please enter an integer between 1-11: ");
				input = keyboard.nextLine();
				i = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("Integers only.");
			}
		}
		return i;
	}

	// open the default database;
	static void openDefault() {
		testObj.openDB();
	}

	/**
	 * Helper method that creates an opportunity any time Job,Internship, or Grad_school is created
	 * @param type The Enum type to determine the Table to connect to
	 * @throws SQLException if the company does not exist, or if an incorrect date format
	 */
	static int callInsertOpp(String type) throws SQLException{		
		System.out.print("Please enter the company name: ");
		String compName = keyboard.nextLine();
		
		System.out.print("Please enter expiration date (yyyy-mm-dd): ");
		String expirDate = keyboard.nextLine();
		
		System.out.print("Please enter a description: ");
		String description = keyboard.nextLine();
		//test
		int result = testObj.insertOpportunity(expirDate, compName, description, type);
		return result;
	}
	
	/* test method for Use Case 1 */
	static void callAddJob() throws SQLException {
		int rs;
		
		int oppId = callInsertOpp("Job");
		System.out.print("Please enter a job title: ");
		String job_title = keyboard.nextLine();
		
		System.out.print("Please enter a salary: ");
		String sal = keyboard.nextLine();
		double salary = Double.parseDouble(sal);
		
		System.out.print("Please enter requirements: ");
		String requirements = keyboard.nextLine();
		
		System.out.print("Please enter job type (Full-time or Part-time): ");
		String job_type = keyboard.nextLine();
		
		System.out.print("Please enter start date: ");
		String date = keyboard.nextLine();	
		
		System.out.println("New Job");
		System.out.println("*****************************");
		rs = testObj.addJob(oppId, job_title, salary, requirements, job_type, date);
		System.out.println(rs);
	}	
	
	/*USE CASE 2*/
	static void callGetAllFullTimeJobs() throws SQLException {
		ResultSet rs;
		System.out.println("\nList of all full time jobs " ); 
		System.out.println("*********************************");
		System.out.printf("%-12s %-8s\n","Opp_id" ,"Job title","Start date");
		rs = testObj.getAllFullTimeJobs();
		while(rs.next()){ 
			System.out.printf("%s	%s\n",rs.getString(1), rs.getString(2), rs.getString(3));
		}
	}
	
	/*Helper method for use cases*/
	static void callGetCompId() throws SQLException{
		int rs;
		System.out.print("Please enter a company name: ");
		String comp_name = keyboard.nextLine();
		rs = testObj.retrieveCompId(comp_name);

		System.out.println(comp_name+": "+rs);
	}
	
	//Test case for Use Case 3
	static void callInsertBookmark() throws SQLException {
		int rs;
		System.out.print("Please enter an opportunity id: ");
		String opp = keyboard.nextLine();
		int oppId = Integer.parseInt(opp);
		
		System.out.println("Please enter a user id: ");
		String useid = keyboard.nextLine();
		int userId = Integer.parseInt(useid);
		
		rs = testObj.addBookmark(oppId, userId);
	}
	
	//Test case for Use Case 4
	static void callUpdateTuitionCost() throws SQLException {
		int rs;
		System.out.println("Please enter Graduate oppId: ");
		String opp = keyboard.nextLine();
		int oppId = Integer.parseInt(opp);
		System.out.println("Please enter new tuition: ");
		String tuit = keyboard.nextLine();
		Double tuition = Double.parseDouble(tuit);
		
		rs = testObj.updateTuitionCost(tuition, oppId);
		System.out.println(rs +" updated tuples");
	}
	

	//Test case for Use Case 5
	static void callGetUserBookmarks() throws SQLException{
		ResultSet rs;
		System.out.print("Please enter a userId: ");
		String user = keyboard.nextLine();
		Integer userId = Integer.parseInt(user);
		System.out.println("\nList of User Bookmarks " ); 
		System.out.println("******************************************************************");
		System.out.printf("%-12s %-12s %-12s %-24s %-5s %-12s \n","Post date" ,"Expir date","Comp_ID", "Description", "Opp_id", "Opp_type");
		rs = testObj.getUserBookmarks(userId);
		while(rs.next()){ 
			System.out.printf("%-12s %-12s	%-5s	%-20s	 %s	%s\n",rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
		}
	}
	
	//Test case for Use Case 6
	static void callDeleteOpportunity() throws SQLException{
		int rs;
		System.out.print("Please enter oppId to be deleted: ");
		String id = keyboard.nextLine();
		int oppId = Integer.parseInt(id);
		rs = testObj.deleteJob(oppId);
		System.out.println(rs+" Opportunity was deleted");
	}
	
}//MyUtilitiesTest	
