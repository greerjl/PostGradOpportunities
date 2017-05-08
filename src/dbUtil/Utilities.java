package dbUtil;
/**
 * This class establishes the basic methods to be used in Database management, and the 6 use cases
 * established in previous deliverables, as well as helper methods
 * It uses Java JDBC and MySQL JDBC driver, mysql-connector-java-5.1.18-bin.jar
 * to open an modify the DB.
 * 
 */

import java.sql.*;

/**
 * @author Dr. Blaha
 * @authors FutureFinders367
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
		//String homeUrl = "jdbc:mysql://localhost:2000/ff367_2017";
		String url = "jdbc:mysql://mal.cs.plu.edu:3306/ff367_2017";
		String username = "ff367";
		String password = "ff367";

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Error connecting to database: " + e.toString());
		}

	}// openDB
	
	/**  
	 * 1 Write and Test
	 * Overload the open method that opens a MySQL DB with the user name 
	 * and password given as input.
	 * 
	 * @param username is a String that is the DB account username
	 * @param password is a String that is the password the account
	 */
	public void openDatabase(String username, String password, boolean offCampus){
		//School
		String url = "jdbc:mysql://mal.cs.plu.edu:3306/ff367_2017";
		//Home
		if(offCampus){ url = "jdbc:mysql://localhost:2000/ff367_2017";}
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Error connecting to database: " + e.toString());
		}
	}

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
	 * Retrieves the company id to be used by other methods using a company name (As a user might see)
	 * @param comp_name
	 * @return the comp_id associated with a company, or 0 if that company is not in DB
	 */
	public int retrieveCompId(String comp_name){
		int compId = 0;
		ResultSet rset = null;
		String sql = null;
		try{
			sql = "SELECT comp_id FROM COMPANY WHERE name = '"+comp_name+"'";
			Statement stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			while(rset.next()){
				compId = Integer.parseInt(rset.getString(1));
			}
		}catch(SQLException e){
			System.out.println("createStatement " + e.getMessage() +" "+ sql);
		}	
		return compId;
	}
	
	/**
	 * Creates an opportunity tuple using current time stamp, and auto incremented id numbers
	 * @param expir_date the date the opportunity will be deleted from DB
	 * @param compName The name of the company the posting is for
	 * @param description The text the user will see regarding the position
	 * @param oppType Enum type {'Job','Internship','Grad_school'}
	 * @return the oppId being created
	 */
	public int insertOpportunity(String expir_date, String compName, String description, String oppType){
		//Retreives the company id so the user doesn't need to explicitly know it
		int compId = retrieveCompId(compName);
		ResultSet rt = null;
		//This grabs the most current date stamp so we can auto generate it
		Date postDate = new Date(System.currentTimeMillis());
		int rset = 0;
		int oppId = 0;
		String sql = null;
		
		try{
			sql = "INSERT INTO OPPORTUNITY VALUES ("+
			      "'"+postDate+"', '"+expir_date+"', "+compId+", '"+description+"', null, '"+oppType+"')";
			Statement stmt = conn.createStatement();
			rset = stmt.executeUpdate(sql, 1);
			rt = stmt.getGeneratedKeys();
			while(rt.next()){
				oppId = rt.getInt(1);
			}
			
		}catch(SQLException e){
			System.out.println("createStatement " + e.getMessage() + sql);
		}
		
		return oppId;
	}
	
	
	/**
	 * Use Case #1
	 * This method creates a SQL statement to add a new job.
	 * @param job_title the title of the job
	 * @param salary the salary of the job
	 * @param requirements for the job
	 * @param job_type being either Full-time or Part-time
	 * @param date the day the job starts
	 * @return ResultSet that contains 5 columns job_title, salary, requirements, job_type, start_date
	 */
	public int addJob(int oppId, String job_title, double salary, String requirements, String job_type, String date) {
		int rset = 0;
		String sql = null;
		
		try {
			sql = "INSERT INTO JOB VALUES(?, ?, ?, ?, ?, ? )";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			System.out.println(job_title+" "+salary+" "+requirements+" "+job_type+" "+date);
			pstmt.clearParameters();
			pstmt.setInt(1, oppId);
			pstmt.setString(2, job_title);
			pstmt.setDouble(3, salary);
			pstmt.setString(4, requirements);
			pstmt.setString(5, job_type);
			pstmt.setDate(6,Date.valueOf(date));
			
			rset = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() +" "+ sql);
		}
		
		return rset;
	}// addJob
	
	/**
	 * Helper method to take in a company name and jobName and retrieve compId and opp_id
	 * @param compName
	 * @param jobName
	 * @return
	 */
	public int[] retrieveJobOppInfo(String compName, String jobName){
		int[] result = new int[2];
		ResultSet rset = null;
		String sql = null;
		
		try{
			sql = "SELECT DISTINCT opp.opp_id, c.comp_id "+
				  "FROM OPPORTUNITY as opp join JOB as j on j.Opp_id = opp.opp_id JOIN COMPANY as c on opp.Comp_id = c.comp_id " +
				  "WHERE c.name = ? and j.job_title = ?";
				  
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setString(1, compName);
			pstmt.setString(2, jobName);
			
			rset = pstmt.executeQuery();
			while(rset.next()){
				result[0] = rset.getInt(1);
				result[1] = rset.getInt(2);
			}
			
		}catch(SQLException e){
			System.out.println("createStatement " + e.getMessage() +" "+ sql);
		}
		
		return result;
	}
	
	
	/**
	 * Use Case #2
	 * This method creates a SQL statement to list job_title, start_date, opp_id of all
	 * jobs that are of type full time. 
	 * 
	 * @return ResultSet that contains three columns opp_id, job_title, start_date of 
	 * 			all jobs that are full time
	 */
	public ResultSet getAllFullTimeJobs() {
		ResultSet rset = null;
		String sql = null;
		
		try {
			Statement stmt = conn.createStatement();
			sql = "SELECT start_date, job_title, opp_id FROM JOB " + "WHERE job_type = 'Full-time'"
					+ " ORDER BY start_date";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}
		
		return rset;
	}// getAllFullTimeJobs
	
	/**
	 * Use Case #3
	 * This method creates a SQL statement to add a bookmark.
	 * @param Opp_id linked to the opportunity being bookmarked
	 * @param id_number the users identification number
	 * @return ResultSet that contains two columns Opp_id and id_number
	 */
	public int addBookmark(int Opp_id, int id_number) {
		int rset = 0;
		String sql = null;
		
		try {
			sql = "INSERT INTO BOOKMARKS VALUES( ? , ? )";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setInt(1, id_number);
			pstmt.setInt(2, Opp_id);
			
			rset = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + " "+ sql);
		}
		
		return rset;
	}// addBookmark

	/**
	 * Use Case #4
	 * This method creates a SQL statement to update tuition_cost in GRAD_SCHOOL table
	 * based off of the Opp_id.
	 * 
	 * @param tuition_cost the cost of tuition at the specific GRAD_SCHOOL
	 * @param Opp_id the unique identifier tied to the GRAD_SCHOOL being updated
	 * @return int the number of tuples modified
	 */
	public int updateTuitionCost(double tuition_cost, int opp_id ) {
		int numTuplesModified = 0;
		String sql = null;
		
		try {
			sql = "UPDATE GRAD_SCHOOL SET tuition_cost = ? WHERE Opp_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setDouble(1, tuition_cost);
			pstmt.setInt(2, opp_id);
			
			numTuplesModified = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}
		
		return numTuplesModified;
	}// updateTuitionCost

	/**
	 * Use Case #5
	 * This method creates a SQL statement to list all bookmarks for a given user
	 * @param userId the userID to gather a report for
	 * @return ResultSet that contains multiple columns
	 */
	public ResultSet getUserBookmarks(int userId) {
		ResultSet rset = null;
		String sql = null;
		
		try {
			Statement stmt = conn.createStatement();
			sql = "SELECT * "+
					"FROM BOOKMARKS b join OPPORTUNITY o on b.Opp_id = o.opp_id "+
					"WHERE b.Id_number = "+userId;
			rset=stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}
		
		return rset;
	}// getUserBookmarks
	
	/**
	 * Use Case #6
	 * This method creates a SQL statement to delete a job posting using cascade through Opportunity
	 * @param Opp_id the unique identifier for the job being deleted
	 * @return ResultSet that contains tuples of deleted job
	 */
	public int deleteJob(int Opp_id) {
		int rset = 0;
		String sql = null;
		
		try {
			sql = "DELETE FROM OPPORTUNITY WHERE opp_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setInt(1, Opp_id);
			
			rset = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}
		
		return rset;
	}// deleteJob


}// Utilities class
