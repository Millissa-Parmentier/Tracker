package tracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeDAO {

	ArrayList<Change> arrayList = new ArrayList<Change>();
	ArrayList<User> changeArray = new ArrayList<User>();
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;

	public ChangeDAO() {

		makeConnection();
 		try {
 			String q = "SELECT * from changes";
 			st = con.createStatement();
 			rs = st.executeQuery(q);
 			while (rs.next()) {
 				// int tempTicketID = rs.getInt(2);
 				// int tempPrio = rs.getInt(0);
 				// String tempSummary = rs.getString(3);
 				// int tempSubmitter = 1;
 				// AddDefect e = new AddDefect(tempSummary, tempPrio,
 				// tempSubmitter);
 				// arrayList.add(e);
 			
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
 
 		} catch (SQLException ex) {
 			System.out.println("Error with table or data in ChangeDAO.");
 		}
 	}
	
	public ArrayList<Change> searchAll() {
		String q = "select * from changes;";
		arrayList = searchChangeDAO(q);
		System.out.println("ChangeDAO searchALL");
		return arrayList;
	}

	public ArrayList<Change> searchTicketID(String option) {
		String q = "select * from changes where Ticket_ID like '" + option + "%';";
		arrayList = searchChangeDAO(q);
		return arrayList;
	}
	
	public ArrayList<Change> searchChangeDate(String option) {
		String q = "select * from changes where CHANGE_DATE like '" + option + "%';";
		arrayList = searchChangeDAO(q);
		return arrayList;
	}
	
	public ArrayList<Change> searchFieldName(String option) {
		String q = "select * from changes where FIELD_NAME like '" + option + "%';";
		arrayList = searchChangeDAO(q);
		return arrayList;
	}
 
	public ArrayList<Change> searchOldValue(String option) {
		String q = "select * from changes where OLD_VALUE like '" + option + "%';";
		arrayList = searchChangeDAO(q);
		return arrayList;
	}
	
	public void addNewChange(Object o) {
		String q = "insert into CHANGES (TICKET_ID, CHANGE_DATE, FIELD_NAME, OLD_VALUE) values"
				+ "(10,2016-02-25,'SUMMARY','TEST'";
		executeDAO(q);
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
			System.out.println("Error with table or data2 in ChangeDAO" + ex);
		}
		return hadSuccess;
	}
	
	private ArrayList<Change> searchChangeDAO(String q){

		makeConnection();
		arrayList.clear();
		try {

			st = con.createStatement();
			rs = st.executeQuery(q);

			while (rs.next()) {
				int tempTicketID = Integer.parseInt(rs.getString(1));
				Date dateTime = rs.getDate(2);
				Instant instant = Instant.ofEpochMilli(dateTime.getTime());
				LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
				String tempFieldName = rs.getString(3);
				String tempOldValue = rs.getString(4);
				//Check for errors first.
				Change i = new Change(tempTicketID, res, tempFieldName, tempOldValue);
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
			System.out.println("Error converting String to integer." + ex);
		} catch (SQLException ex) {
			System.out.println("Error with table or data");
		}//change sysouts to JOptionPane
		return arrayList;
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
 			Logger lgr = Logger.getLogger(AddDAO.class.getName());
 			lgr.log(Level.SEVERE, ex.getMessage(), ex);
 			System.out.println("Sql Exception");
 
 		}
 
 	}
 }