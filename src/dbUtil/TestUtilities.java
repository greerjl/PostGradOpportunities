package dbUtil;
/**
 * This program is used to test the Utilities class
 */
 
// You need to import the java.sql package to use JDBC
import java.sql.*; 
 
import java.util.Scanner;

/**
 * @author Dr. Blaha
 * @author Jayme Greer
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
			case 2: {
				callGetNameSalary();
				break;
			}
			case 3: {
				callMatchName();
				break;
			}
			case 4: {
				callEmployeeByDNO();
				break;
			}
			case 5: {
				openCustom();
				break;
			}
			case 6: {
				callEmployeeOnProjectByDNO();
				break;
			}
			case 7: {
				callProjectDetails();
				break;
			}
			case 8: {
				callWorksWith();
				break;
			}
			case 9: {
				callLazyEmps();
				break;
			}
			case 10: {
				callInsertWorksOn();
				break;
			}
			case 11: {
				callUpdateWorksOn();
				break;
			}
			case 12: {
				callDeleteFromWorksOn();
				break;
			}
			case 13: {
				callAddDeptLoc();
				break;
			}
			case 14: {
				testObj.closeDB(); //close the DB connection 
				break;
			}
			case 15: {
				done = true;
				System.out.println("Good bye");
				break;
			}
			}// switch
		}

	}// main

	static void displaymenu() {
		System.out.println("1)  open default DB");
		System.out.println("2)  call getNameSalary()");
		System.out.println("3)  call matchLastName(String)");
		System.out.println("4)  call employeeByDNO()");
		System.out.println("5)  open DB(String, String)");
		System.out.println("6)  call employeeOnProjectByDNO(int)");
		System.out.println("7)  call projectDetails() ");
		System.out.println("8)  call worksWith");
		System.out.println("9)  call lazyEmps");
		System.out.println("10) call insertWorksOn");
		System.out.println("11) call updateWorksOn");
		System.out.println("12) call deleteFromWorksOn");
		System.out.println("13) call addDeptLoc");
		System.out.println("14) close the DB");
		System.out.println("15) quit");
	}

	static int getChoice() {
		String input;
		int i = 0;
		while (i < 1 || i > 15) {
			try {
				System.out.print("Please enter an integer between 1-15: ");
				input = keyboard.nextLine();
				i = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		return i;
	}

	// open the default database;
	static void openDefault() {
		testObj.openDB();
	}

	// test getNameSalary() method
	static void callGetNameSalary() throws SQLException {
		ResultSet rs;
		System.out.println("Research Department Employees");
		System.out.println("*****************************");
		System.out.printf("LastName, FirstName        Salary\n");
		rs = testObj.getNameSalary();
		while (rs.next()) {
			System.out.printf("%-26s %s \n", rs.getString(1) + ", " + rs.getString(2), 
					                         rs.getString(3));
		}
	}

	// test matchName() method
	static void callMatchName() throws SQLException {
		ResultSet rs;
		String target;
		target = "K";
		System.out.println("\nEmployees with name that starts with " + target);
		System.out.println("***************************************************");
		System.out.printf("%-12s  %s\n", "Dept Number",   "LastName, FirstName");
		rs = testObj.matchLastName(target);
		while (rs.next()) {
			System.out.printf("    %-8s    %s\n", rs.getString(1), 
					rs.getString(2) + ", " + rs.getString(3));
		}
	}
	
	 
	//test employeeByDNO() method 
	static void callEmployeeByDNO() throws SQLException {
		ResultSet rs;
		System.out.print("Please enter a department number: ");
		String input = keyboard.nextLine();
		int dno= Integer.parseInt(input); 
		System.out.println("\nEmployees that work in department " + dno); 
		System.out.println("*******************************************");
		System.out.printf("%-12s   %s\n", "Dept Number",   "LastName, FirstName");
		rs = testObj.employeeByDNO(dno); 
		while(rs.next()){ 
			System.out.printf("    %-8s     %s\n", rs.getString(1), 
					rs.getString(2) + ", " + rs.getString(3));
		}
		
	}
	
	//test openDB overload -- WORKS
	static void openCustom() {
		System.out.println("Using jdbc:mysql://mal.cs.plu.edu:3306/company367_2017");
		System.out.println("Please enter your username: ");
		String username = keyboard.nextLine();
		System.out.println("Pleaes enter your password: ");
		String password = keyboard.nextLine();
		testObj.openDB(username, password);
	}
	
	//test employeeOnProjectByDNO() method -- WORKS PRINT F IS AWFUL
	static void callEmployeeOnProjectByDNO() throws SQLException {
		ResultSet rs;
		System.out.println("Please enter a department number: ");
		String input = keyboard.nextLine();
		int dno= Integer.parseInt(input); 
		System.out.println("\nEmployees that work on a project controlled by department " + dno); 
		System.out.println("****************************************************************");
		System.out.printf("%-12s %s\n","LastName FirstName", "Project Number Hours");
		rs = testObj.employeeOnProjectByDNO(dno);
		while(rs.next()){ 
			System.out.printf("%-1s	%s\n",rs.getString(1),
					rs.getString(2) +"            "+ rs.getString(3)+ "         "+ rs.getString(4));
		}
		
	}
	
	//test projectDetails() method -- WORKS PRINT F IS AWFUL
	static void callProjectDetails() throws SQLException {
		ResultSet rs;
		System.out.println("\nProject details " ); 
		System.out.println("*********************************");
		System.out.printf("%-12s %-8s\n","# Employees","Total Hours     Avg Hours");
		rs = testObj.projectDetails();
		while(rs.next()){ 
			System.out.printf("%s	%s\n",rs.getString(1),
					rs.getString(2) +"            "+ rs.getString(3));
		}
	}

	//test worksWith() method -- WORKING
	static void callWorksWith() throws SQLException {
		ResultSet rs;
		System.out.println("Please enter employee first name: ");
		String fname = keyboard.nextLine();
		System.out.println("Please enter employee last name: ");
		String lname = keyboard.nextLine();
		System.out.println("\nEmployees that work with " + fname + " " + lname);
		System.out.println("****************************************************************");
		rs = testObj.worksWith(fname, lname);
		while(rs.next()){ 
			System.out.printf("%s	%s\n",rs.getString(1),
					rs.getString(2) +"            "+ rs.getString(3) + rs.getString(4));
		}
	}
	
	//test lazyEmps() method -- WORKING
	static void callLazyEmps() throws SQLException {
		ResultSet rs;
		System.out.println("\nEmployees that aren't working on projects " ); 
		System.out.println("*********************************");
		System.out.printf("%-12s %-8s\n","LastName, FirstName","Salary");
		rs = testObj.lazyEmps();
		while(rs.next()){ 
			System.out.printf("%s	%s\n",rs.getString(1), rs.getString(2));
		}
	}
	
	//test insertWorksOn() method -- 
	static void callInsertWorksOn() throws SQLException {
		int rs;
		System.out.println("Please enter essn to add to works on: ");
		String essn = keyboard.nextLine();
		System.out.println("Please enter project number: ");
		String pno = keyboard.nextLine();
		System.out.println("Please enter number of hours: ");
		String hours = keyboard.nextLine();
		
		String [][] data = {{essn},{pno},{hours}};

		rs = testObj.insertWorksOn(data);
		System.out.println(rs);
	}
	
	//test updateWorksOn() method -- 
	static void callUpdateWorksOn() throws SQLException {
		int rs;
		
		System.out.println("Please enter essn to update: ");
		String essn = keyboard.nextLine();
		System.out.println("Please enter pno to update: ");
		String pno = keyboard.nextLine();
		System.out.println("Please enter new hours: ");
		String hours = keyboard.nextLine();
		
		rs = testObj.updateWorksOn(essn, pno, hours);
		System.out.println("rs: "+rs);
	}
	
	//test deleteFromWorksOn() method -- 
	static void callDeleteFromWorksOn() throws SQLException {
		int rs;
		
		System.out.println("Please enter essn of tuple to be deleted: ");
		String essn = keyboard.nextLine();
		System.out.println("Please enter pno of tuple to be deleted: ");
		int pno = keyboard.nextInt();
		
		rs = testObj.deleteFromWorksOn(essn, pno);
		System.out.println("rs: "+rs);
	}
	
	//test addDeptLoc() method -- 
	static void callAddDeptLoc() throws SQLException {
		int rs;
		
		System.out.println("Please enter dnumber to add: ");
		int dnumber = keyboard.nextInt();
		System.out.println("Please enter dlocation to add: ");
		String dlocation = keyboard.nextLine();
	
		rs = testObj.addDeptLoc(dnumber, dlocation);
		System.out.println("rs: "+rs);
	}
		
}//MyUtilitiesTest	