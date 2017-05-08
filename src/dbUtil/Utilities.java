package dbUtil;
/**
 * This class provides some basic methods for accessing a MySQL DB.
 * It uses Java JDBC and MySQL JDBC driver, mysql-connector-java-5.1.18-bin.jar
 * to open an modify the DB.
 * 
 */

// You need to import the java.sql package to use JDBC methods and classes
import java.sql.*;

/**
 * @author Dr. Blaha
 * @author Jayme Greer
 * 
 */
public class Utilities {

	private Connection conn = null; // Connection object

	/**
	 * The default constructor will load and register the MySQL driver
	 */
	public Utilities() {
		super();
		// Load the MySQL JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Class.forName("com.mysql.jdbc.Driver").newInstance();

		} catch (ClassNotFoundException e) {
			System.out.println("Unable to load driver.");
		}
	}

	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * Open a MySQL DB connection where user name and password are predefined
	 * (hardwired) within the method.
	 */
	public void openDB() {

		// Connect to the database
		String url = "jdbc:mysql://mal.cs.plu.edu:3306/company367_2017";
		String username = "ff367";
		String password = "ff367";

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Error connecting to database: " + e.toString());
		}

	}// openDB

	/**
	 * Close the connection to the DB
	 */
	public void closeDB() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			System.err.println("Failed to close database connection: " + e);
		}
	}// closeDB

	/**
	 * This method creates an SQL statement to list fname, lname, salary of all
	 * employees that work in the department with dname='Research'
	 * 
	 * @return ResultSet that contains three columns lname, fname, salary of all
	 *         employees that work in the research department
	 */
	public ResultSet getNameSalary() {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT lname, fname, salary FROM employee, department " + "WHERE dno=dnumber and dname='Research' "
					+ "ORDER BY lname, fname";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}// getNameSalary

	/**
	 * This method creates an SQL statement to list fname, lname, and department
	 * number of all employees that have a last name that starts with the String
	 * target
	 * 
	 * @param target the string used to match beginning of employee's last name
	 * @return ResultSet that contains lname, fname, and department number of
	 *         all employees that have a first name that starts with target.
	 */
	public ResultSet matchLastName(String target) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT dno, lname, fname FROM employee " + "WHERE lname like '" + target + "%' " + "ORDER BY dno";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;

	}// matchLastName

	/**
	 * This method creates an SQL statement to list fname, lname, and department
	 * number of all employees that work in the department with number dno
	 * 
	 * @param dno the department number
	 * @return ResultSet that contains lname, fname, and department number of
	 *         all employees that work in the department number dno
	 */
	// EXAMPLE OF USING A PreparedStatement AND SETTING Parameters
	public ResultSet employeeByDNO(int dno) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			sql = "SELECT dno, lname, fname FROM employee " + "WHERE dno = ? " + "ORDER BY dno";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setInt(1, dno); // set the 1 parameter

			rset = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}// matchLastName2
	/**  
	 * 1 Write and Test
	 * Overload the open method that opens a MySQL DB with the user name 
	 * and password given as input.
	 * 
	 * @param username is a String that is the DB account username
	 * @param password is a String that is the password the account
	 */
	public void openDB(String username, String password) {
		// Connect to the database
		String url = "jdbc:mysql://mal.cs.plu.edu:3306/company367_2017";
		
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Error connecting to database: " + e.toString());
		}

	}// openDB

	
	/**
	 * 2 Write and Test 
	 * Write a method that returns lname, fname, project number and hours of all 
	 * employees that work on a project controlled by department, deptNum. Here 
	 * deptNum is given as input from the client
	 * 
	 * @param deptNum is the controlling department number
	 * @return ResultSet with lname, fname, project number and hours of all
	 *         employees that work on a project controlled by department dno
	 */
	public ResultSet employeeOnProjectByDNO(int dno) {
		ResultSet rset = null;
		String sql = null;
		
		try {
			sql = "SELECT lname, fname, pno, hours FROM employee, works_on " +
					"WHERE dno = ? and essn=ssn";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setInt(1, dno);
			rset = pstmt.executeQuery();
		} catch(SQLException e) {
			System.out.println("Error connecting to database: " + e.toString());
			System.out.println("SQL: " + sql);
		}
		
		return rset;
	}

	/**
	 * 3 Write and Test
	 * Write a method that returns for each project the number of employees 
	 * that work on the project, the total number of hours they have all worked 
	 * on the project, and the average number of hours each employee has worked 
	 * on the project.
	 * 
	 * @return ResultSet that has for each project the number of employees that
	 *         work on the project, the total number of hours they have all
	 *         worked on the project, and the average number of hours each
	 *         employee has worked on project
	 */
	public ResultSet projectDetails() {
		ResultSet rset = null;
		String sql = null;
		
		try {
			Statement stmt = conn.createStatement();
			sql = "SELECT COUNT(*), sum(hours), avg(hours) FROM works_on GROUP BY pno";	
			rset=stmt.executeQuery(sql);
		}  catch(SQLException e) {
			System.out.println("Error connecting to database: " + e.toString());
			System.out.println("SQL: " + sql);
		}
		
		return rset;
	}

	/**
	 * 4 Write and Test
	 * Write a method that returns fname, lname, salary, and dno for each employee 
	 * that works on a project with the employee specified by input values empFname, empLname
	 * 
	 * @param empFname is the first name of the employee
	 * @param empLname is the last name of the employee
	 * @return ResultSet that has fname, lname, salary, and dno for each
	 *         employee that works on a project with the employee empFname,
	 *         empLname
	 */
		public ResultSet worksWith(String empFname, String empLname) {
		ResultSet rset = null;
		String sql = null;
		
		try {
			sql = "SELECT DISTINCT E.fname, E.lname, E.salary, E.dno FROM employee AS E, works_on AS W" +
					" WHERE W.essn = E.ssn and EXISTS (SELECT W.pno FROM works_on AS WInput, employee AS Input" +
			 	 	" WHERE	WInput.essn = Input.ssn and Input.fname=? and Input.lname = ? and W.pno = pno)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setString(1, empFname);
			pstmt.setString(2, empLname);
			rset = pstmt.executeQuery();
		} catch(SQLException e) {
			System.out.println("Error connecting to database: " + e.toString());
			System.out.println("SQL: " + sql);
		}
		
		return rset;
	}

	/**
	 * 5 Write and Test
	 * Retrieve the names of employees who do not work on any project and their 
	 * salary Names must be in the format "lname, fname" i.e., the last name and 
	 * first name must be concatenated.
	 * 
	 * @return ResultSet that has employee name and salary of all employees that
	 *         do not work on any project.
	 */
		public ResultSet lazyEmps() {
			ResultSet rset = null;
			String sql = null;
			
			try {
				Statement stmt = conn.createStatement();
				sql = "SELECT concat(lname, ', ', fname) name, salary FROM employee AS E" +
						" WHERE NOT EXISTS (SELECT * FROM works_on WHERE essn = E.ssn);";	
				rset=stmt.executeQuery(sql);
			}  catch(SQLException e) {
				System.out.println("Error connecting to database: " + e.toString());
				System.out.println("SQL: " + sql);
			}
			
			return rset;
		}

	/**
	 * 6 Write and Test   ==> YOU MUST USE A PreparedStatement <==
	 * This method will use a PreparedStatement and the
	 * information in data to update the works_on table. Each row of the 2-dim
	 * array, data, contains the 3 attributes for one tuple in the works_on
	 * table. The 2-dim array is a nx3 array and the column format is 
	 * (essn, pno, hours) The method returns the number of tuples successfully
	 * inserted.
	 * 
	 * @param data is a nx3 table of Strings where each row has the format 
	 *        (essn, pno, hours)
	 * @return number of tuples successfully inserted into works_on
	 */
		public int insertWorksOn(String[][] data) {
			String sql = null;
			int total = 0;
			
			   for(int i = 0; i < data.length; i++)
			   {
			      for(int j = 0; j < data[i].length; j++)
			      {
			         System.out.printf("%5s ", data[i][j]);
			      }
			      System.out.println();
			   }

			
			try {
				sql = "INSERT INTO works_on(essn, pno, hours) VALUES (?, ?, ?);";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for( int i = 0; i<data.length; i++) {
					pstmt.clearParameters();
					pstmt.setString(1, data[i].toString());
					pstmt.setString(2, data[i].toString());
					pstmt.setString(3, data[i].toString());

				}
						
				total = pstmt.executeUpdate();
			} catch(SQLException e) {
				System.out.println("createStatement: " + e.getMessage());
				System.out.println("SQL: " + sql);
			}
			
			return total;
		}
		
	/**
	 * This method will create a SQL statement to update the hours of a works_on tuple.
	 * @param essn The given employee's ssn
	 * @param pno The given employee's project number to be updated
	 * @param hours The value to be updated.
	 * @return number of tuples successfully updated
	 */
		public int updateWorksOn(String essn, String pno, String hours){
			int total = 0;
			String sql = null;
			
			try{
				sql = "UPDATE works_on SET hours = ? WHERE essn = ? and pno = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, hours);
				pstmt.setString(2, essn);
				pstmt.setString(3, pno);
				
				total = pstmt.executeUpdate();
			} catch(SQLException e) {
				System.out.println("createStatment " + e.getMessage());
				System.out.println("SQL: " + sql);
			}
			
			
			return total;
		}
		
	/**
	 * This method will delete a works_on tuple. 
	 * @param essn The ssn of the tuple to be deleted
	 * @param pno The project number of the tuple to be deleted
	 * @return the number of tuples deleted
	 */
	public int deleteFromWorksOn(String essn, int pno) {
		int total = 0;
		String sql = null;
		
		try {
			sql = "DELETE FROM works_on WHERE essn = ? and pno = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setString(1, essn);
			pstmt.setInt(2, pno);
			
			total = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("createStatment " + e.getMessage());
			System.out.println("SQL: " + sql);
		}
		
		return total;
	}			
		
	/**
	 * This method will add a dept_locations tuple. 
	 * @param dnumber The department number of the tuple to be added
	 * @param dlocation The department location of the tuple to be added
	 * @return total number of tuples added to dept_locations
	 */
		public int addDeptLoc(int dnumber, String dlocation){
			int total = 0;
			String sql = null;
			
			try{
				sql = "INSERT INTO dept_locations VALUES(?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setInt(1, dnumber);
				pstmt.setString(2, dlocation);
				
				total = pstmt.executeUpdate();
			} catch(SQLException e) {
				System.out.println("createStatement " + e.getMessage());
				System.out.println("SQL: " + sql);
			}
			
			return total;
		}
	
		
		
}// Utilities class