package tracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * This is a data access object that reads and writes to the SQL User database. 
 */


public class UserDAO {

	private String[] fields = { "UserID", "PASSWORD", "LAST_NAME", "FIRST_NAME", "POSITION",
			"ACCESS_LEVEL", "TEAM", "EMAIL"};
	public ArrayList<User> arrayList = new ArrayList<User>();
	public ArrayList<String> users = new ArrayList<String>();
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	//Constructor
	public UserDAO() {
		boolean hadSuccess = createUserDAO();
		if (!hadSuccess){}//add call to pop up an error message here.
	}
	
	public UserDAO(String userID, String password){
		boolean hadSuccess = findUserDAO(userID, password);
		if (!hadSuccess) {
						
		}
		
	}	

	public boolean insertNewUser(User u) {
	
		String q = "insert into user (LAST_NAME, FIRST_NAME, POSITION, ACCESS_LEVEL, TEAM, EMAIL, PASSWORD) values "
				+ "('" + u.getLastName() + "', '" + u.getFirstName() + "', '" + u.getPosition() + "', '"
				+ u.getAccessLevel() + "', '" + u.getTeam() + "', '" + u.getEmail() + "', '" + u.getPassword()
				+ "');";
		return executeDAO(q);
	}

	public boolean deleteUser(User tempUser) {
		String q = "delete from user where userID='" + tempUser.getUserID() + "';";
		return executeDAO(q);
	}
	
	public ArrayList<String> getSubmitter() {
		String q = "select UserID from user;";
		makeConnection();
		users.clear();
		try {

			st = con.createStatement();
			rs = st.executeQuery(q);

			while (rs.next()) {

				users.add(rs.getString(1));
				
			}
			
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
			
		} catch (NumberFormatException ex) {
			System.out.println("Error converting String to integer.");
		} catch (SQLException ex) {
			System.out.println("Error with table or data in UserDAO 2");
		}//change sysouts to JOptionPane
		return users;
	}
	public ArrayList<User> searchAll() {
		String q = "select * from user;";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
	
	public ArrayList<User> searchUserID(String option) {
		String q = "select * from user where USERID like '" + option + "%';";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
	
	public ArrayList<User> searchLName(String option) {
		String q = "select * from user where LAST_NAME like '" + option + "%';";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
	
	public ArrayList<User> searchFName(String option) {
		String q = "select * from user where FIRST_NAME like '" + option + "%';";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
	
	public ArrayList<User> searchPosition(String option) {
		String q = "select * from user where POSITION like '" + option + "%';";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
	
	public ArrayList<User> searchAccessLevel(String option) {
		String q = "select * from user where ACCESS_LEVEL like '" + option + "%';";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
	
	public ArrayList<User> searchTeam(String option) {
		String q = "select * from user where TEAM like '" + option + "%';";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
	
	public ArrayList<User> searchEmail(String option) {
		String q = "select * from user where EMAIL like '" + option + "%';";
		arrayList = searchUserDAO(q);
		return arrayList;
	}
		
	public boolean replaceUser(User u){
		String q = "UPDATE user SET (LAST_NAME, FIRST_NAME, POSITION, ACCESS_LEVEL, TEAM, EMAIL, PASSWORD) values "
				+ "('" + u.getLastName() + "', '" + u.getFirstName() + "', '" + u.getPosition() + "', '"
				+ u.getAccessLevel() + "', '" + u.getTeam() + "', '" + u.getEmail() + "', '" + u.getPassword()
				+ "') WHERE USERID = '"+u.getUserID()+"';";
		return executeDAO(q);
	}

	public boolean replaceFieldAt(Object value, User u, int col){
		
		String q = "";
		if(fields[col].equals("ACCESS_LEVEL")){
			int intValue = (int) value;
			q = "UPDATE user SET "+fields[col]+" = "+ intValue + " WHERE USERID = '"+ u.getUserID()+"';";
		}else{
			String stringValue = (String) value;
			q = "UPDATE user SET "+fields[col]+" = '"+ stringValue + "' WHERE USERID = '"+ u.getUserID()+"';";			
		}
		return executeDAO(q);
	}
	
	public Object getFieldAt(User u, int col){
		
		String q = "SELECT "+fields[col]+" from USER WHERE USERID = '"+ u.getUserID()+"';";
		return executeDAO(q);
	}
		
	public void makeConnection() {
		String url = "jdbc:mysql://localhost:3306/tracker";
		String user = "root";
		String password = "Kenzie#1";

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection made");

		} catch (Exception ex) {
			Logger lgr = Logger.getLogger(UserDAO.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.out.println("Sql Exception");
		}
	}
	
	private boolean executeDAO(String q){
		makeConnection();
		boolean hadSuccess = false;
		
		try {
			st = con.createStatement();
			st.executeUpdate(q);

			// close the statement and connection once we are done
			if (con != null) {
				con.close();
			}
			
			if (st != null) {
				st.close();
			}
			
			hadSuccess = true;
		} catch (SQLException ex) {
			System.out.println("Error with table or data in UserDAO 3" + ex);
		}
		return hadSuccess;
	}
	
	private boolean createUserDAO(){

		makeConnection();
		boolean hadSuccess = false;
		
		try {

			String q = "SELECT * from user";

			st = con.createStatement();
			rs = st.executeQuery(q);

			while (rs.next()) {

				int tempUserID = Integer.parseInt(rs.getString(1));
				String tempPassword = rs.getString(2);
				String tempLastName = rs.getString(3);
				String tempFirstName = rs.getString(4);
				String tempPosition = rs.getString(5);
				int tempAccessLevel = Integer.parseInt(rs.getString(6));
				String tempTeam = rs.getString(7);
				String tempEmail = rs.getString(8);
				//Check for errors first.
				User i = new User(tempUserID, tempPassword, tempLastName, tempFirstName, tempPosition, tempAccessLevel,
						tempTeam, tempEmail);
				arrayList.add(i);
			}
	
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
			hadSuccess = true;
		} catch (NumberFormatException ex) {
			System.out.println("Error converting String to integer.");
		} catch (SQLException ex) {
			System.out.println("Error with table or data in UserDAO 4");
		}//change sysouts to JOptionPane
		return hadSuccess;
	}
	
	boolean findUserDAO(String userID, String password){
		makeConnection();
		boolean hadSuccess = false;
		
		try {
			String q = "SELECT * from user where password = '"  + password + "' and userID = " + userID + ";";
			st = con.createStatement();
			rs = st.executeQuery(q);
			
			while (rs.next()) {
				int tempUserID = Integer.parseInt(rs.getString(1));
				String tempPassword = rs.getString(2);
				String tempLastName = rs.getString(3);
				String tempFirstName = rs.getString(4);
				String tempPosition = rs.getString(5);
				int tempAccessLevel = Integer.parseInt(rs.getString(6));
				String tempTeam = rs.getString(7);
				String tempEmail = rs.getString(8);
				//Check for errors first.
				User i = new User(tempUserID, tempPassword, tempLastName, tempFirstName, tempPosition, tempAccessLevel,
						tempTeam, tempEmail);
				arrayList.add(i);
				hadSuccess = true;
			}
			
			if (rs != null) {
				rs.close();
			} 
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (NumberFormatException ex) {
			System.out.println("Error converting String to integer.");
		} catch (SQLException ex) {
			System.out.println("Error with table or data in UserDAO 5");
			String msg = "Invalid login" + ex;
			JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return hadSuccess;
	}
	
	private ArrayList<User> searchUserDAO(String q){

		makeConnection();
		arrayList.clear();
		try {

			st = con.createStatement();
			rs = st.executeQuery(q);

			while (rs.next()) {

				int tempUserID = Integer.parseInt(rs.getString(1));
				String tempPassword = rs.getString(2);
				String tempLastName = rs.getString(3);
				String tempFirstName = rs.getString(4);
				String tempPosition = rs.getString(5);
				int tempAccessLevel = Integer.parseInt(rs.getString(6));
				String tempTeam = rs.getString(7);
				String tempEmail = rs.getString(8);
				//Check for errors first.
				User i = new User(tempUserID, tempPassword, tempLastName, tempFirstName, tempPosition, tempAccessLevel,
						tempTeam, tempEmail);
				arrayList.add(i);
			}
			
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
			
		} catch (NumberFormatException ex) {
			System.out.println("Error converting String to integer.");
		} catch (SQLException ex) {
			System.out.println("Error with table or data in UserDAO 1");
		}//change sysouts to JOptionPane
		return arrayList;
	}

}