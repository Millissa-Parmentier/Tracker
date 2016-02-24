package tracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;

/**
 * This is a data access object that reads and writes to the SQL User database. 
 */

/**
 * @author Kerty Levy
 * @author Milissa Parmentier
 */

public class UserDAO {

	private String[] fields = { "UserID", "PASSWORD", "LAST_NAME", "FIRST_NAME", "POSITION",
			"ACCESS_LEVEL", "TEAM", "EMAIL"};
	public ArrayList<User> arrayList = new ArrayList<User>();
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;

	//Constructor
	public UserDAO() {
		boolean hadSuccess = createUserDAO();
		if (!hadSuccess){}//add call to pop up an error message here.
	}


	// ***We will need a method here to check that the password entered matches.
	// Should also add getters and setters just in case.
	// Don't need this now, but will need if we add the option to change
	// password.
	
	

	public boolean insertNewUser(User u) {
	
		String q = "insert into user (LAST_NAME, FIRST_NAME, POSITION, ACCESS_LEVEL, TEAM, EMAIL, PASSWORD) values "
				+ "('" + u.getLastName() + "', '" + u.getFirstName() + "', '" + u.getPosition() + "', '"
				+ u.getAccessLevel() + "', '" + u.getTeam() + "', '" + u.getEmail() + "', '" + u.getPassword()
				+ "');";
		return executeDAO(q);
	}

	
	// These added by Millissa
	public boolean deleteUser(User tempUser) {
		String q = "delete from user where userID='" + tempUser.getUserID() + "';";
		return executeDAO(q);
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
	
	
	public boolean searchUser(User u) {
		makeConnection();
		boolean hadSuccess = false;

		try {
			String q = "";
			String criteria = "";
			int cnt = 0;

			Integer uid = u.getUserID();
			if (uid != null) {
				criteria += "userid=" + u.getUserID();
			}
			if (u.getLastName() != null) {
				if (cnt > 0) {
					criteria += ", and ";
				}
				criteria += "last_name=" + u.getLastName();
			}
			if (u.getFirstName() != null) {
				if (cnt > 0) {
					criteria += ", and ";
				}
				criteria += "first_name=" + u.getFirstName();
			}
			if (u.getPosition() != null) {
				if (cnt > 0) {
					criteria += ", and ";
				}
				criteria += "position=" + u.getPosition();
			}

			Integer al = u.getAccessLevel();
			if (al != null) {
				if (cnt > 0) {
					criteria += ", and ";
				}
				criteria += "access_level=" + u.getAccessLevel();
			}
			if (u.getTeam() != null) {
				if (cnt > 0) {
					criteria += ", and ";
				}
				criteria += "team=" + u.getTeam();
			}
			if (u.getEmail() != null) {
				if (cnt > 0) {
					criteria += ", and ";
				}
				criteria += "email=" + u.getEmail();
			}
			if (u.getPassword() != null) {
				if (cnt > 0) {
					criteria += ", and ";
				}
				criteria += "password=" + u.getPassword();
			}
			if (cnt > 0) {
				q = "select * from user where " + criteria + ";";
			} else {
				q = "select * from user;";
			}
			st = con.createStatement();
			st.executeUpdate(q);
			if (st != null) {
				st.close();
				hadSuccess = true;
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			System.out.println("Error with table or data");
		}
		return hadSuccess;
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
			System.out.println("Error with table or data" + ex);
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
			System.out.println("Error with table or data");
		}//change sysouts to JOptionPane
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
			System.out.println("Error with table or data");
		}//change sysouts to JOptionPane
		return arrayList;
	}

}
