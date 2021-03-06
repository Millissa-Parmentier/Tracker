package tracker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Date;

public class DefectDAO {
	
	public ArrayList<Defect> arrayList = new ArrayList<Defect>();
	public ArrayList<String> defects = new ArrayList<String>();
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;

	public DefectDAO() {

		makeConnection();

		try {

			String q = "SELECT * from defect";

			st = con.createStatement();
			rs = st.executeQuery(q);

			while (rs.next()) {
				int tempTicketID = rs.getInt(1);
				String tempSummary = rs.getString(2);
				String tempDescription = rs.getString(3);
				int tempAssignee = rs.getInt(4);
				int tempPriority = rs.getInt(5);
				Timestamp tempStamp = rs.getTimestamp(6);
				LocalDateTime tempDateEntered = LocalDateTime.ofInstant(tempStamp.toInstant(), ZoneOffset.ofHours(-6));
				int tempSubmitter = rs.getInt(7);
				String tempStatus = rs.getString(8);
				String tempComments = rs.getString(9);
				Defect d = new Defect(tempTicketID, tempSummary, tempDescription, tempAssignee, tempPriority,
						tempDateEntered, tempSubmitter, tempStatus, tempComments);
				arrayList.add(d);//int t, String sm, String de, int a, int p, LocalDateTime dt, int sb, String st, String c
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
			System.out.println("Error with table1");
		}

	}


	public boolean insertNewDefect(Defect i) {

			String q = "insert into defect (summary, description, assignee_id, priority_level, date_entered, submitter, status, comments) "
					+ "values ('" + i.getSummary() + "', '" + i.getDescription() + "', '"
					+ i.getAssignee_id() + "', '" + i.getPriority_level() + "', '" + getTimestampFromDefect(i) + "', '"
					+ i.getSubmitter() + "', '" + i.getStatus() + "', '" + i.getComments() + "');";
			System.out.println("q in insertNewDefect in DefectDAO " + q);
			return executeDAO(q);
	}

	
	public ArrayList<Defect> searchAll() {
		String q = "select * from defect;";
		arrayList = searchDefectDAO(q);
		System.out.println("DefectDAO searchALL");
		return arrayList;
	}

	public ArrayList<Defect> searchTicketID(String option) {
		String q = "select * from defect where Ticket_ID like '" + option + "%';";
		arrayList = searchDefectDAO(q);
		return arrayList;
	}
	
	public ArrayList<Defect> searchSummary(String option) {
		String q = "select * from defect where SUMMARY like '" + option + "%';";
		arrayList = searchDefectDAO(q);
		return arrayList;
	}
	
	public ArrayList<Defect> searchDesc(String option) {
		String q = "select * from defect where SUMMARY like '" + option + "%';";
		arrayList = searchDefectDAO(q);
		return arrayList;
	}
	
	public ArrayList<Defect> searchAssignedTo(String option) {
		String q = "select * from defect where ASSIGNEE_ID like '" + option + "%';";
		arrayList = searchDefectDAO(q);
		return arrayList;
	}
	
	public ArrayList<Defect> searchPriority(String option) {
		String q = "select * from defect where PRIORITY like '" + option + "%';";
		arrayList = searchDefectDAO(q);
		return arrayList;
	}
	
	public ArrayList<Defect> searchDateEntered(String option) {
		String q = "select * from defect where DATE_ENTERED like '" + option + "%';";
		arrayList = searchDefectDAO(q);
		return arrayList;
	}
	
	public ArrayList<Defect> searchSubmittedBy(String option) {
		String q = "select * from defect where SUBMITTER like '" + option + "%';";
		arrayList = searchDefectDAO(q);
		return arrayList;
	}
	
	
	public boolean replaceDefect(Defect i){
			
		String q = "UPDATE defect SET ((summary, description, assignee_id, priority_level, date_entered, submitter, status, comments) "
					+ "values ('" + i.getSummary() + "', '" + i.getDescription() + "', '"
					+ i.getAssignee_id() + "', '" + i.getPriority_level() + "', '" + getTimestampFromDefect(i) + "', '"
					+ i.getSubmitter() + "', '" + i.getStatus() + "', '" + i.getComments() + "') WHERE ticketID = '"+i.getTicketID()+"';";
		System.out.println("q in replaceDefect in DefectDAO " + q);
		return executeDAO(q);		
	}
	
	
	public boolean deleteDefect(Defect defect){
		
		String q = "delete from defect where TICKET_ID='" + defect.getTicketID() + "';";
		
		return executeDAO(q);
	}
	
	
	public boolean replaceFieldAt(Object value, Defect i, int col){
		
		String q = "";
		String field = Defect.fields[col];
		if(field.equals("ASSIGNEE_ID")|| field.equals("PRIORITY") || field.equals("SUBMITTER")){
			int intValue = (int) value;
			q = "UPDATE defect SET "+field+" = "+ intValue + " WHERE TICKET_ID = '"+ i.getTicketID()+"';";
		}else {
			String stringValue = (String) value;
			q = "UPDATE defect SET "+field+" = '"+ stringValue + "' WHERE TICKET_ID = '"+ i.getTicketID()+"';";			
		}
		System.out.println("q in replaceFieldAt in DefectDAO " + q);
		return executeDAO(q);
	}
	
	
	public Object getFieldAt(Defect i, int col){
		
		String q = "SELECT "+Defect.fields[col]+" from DEFECT WHERE TICKET_ID = '"+ i.getTicketID()+"';";
		return executeDAO(q);
	}
	
	public  Timestamp getTimestampFromDefect(Defect d){
	
		LocalDateTime dateTime = d.getDate_entered();
		Timestamp timestamp = Timestamp.valueOf(dateTime);
		return timestamp;
	}

	
	public void searchDefect(Defect d) {

		makeConnection();

		try {

			String q = "";
			String criteria = "";
			int cnt = 0;

			Integer t = d.getTicketID();
			if (t != null) {
				criteria += "ticket_id=" + d.getTicketID();
			}

			Integer sb = d.getSubmitter();
			if (sb != null) {
				if (cnt > 0) {
					criteria += ", and ";
					criteria += "submitter=" + d.getSubmitter();
				}
			}

			Integer a = d.getAssignee_id();
			if (a != null) {
				if (cnt > 0) {
					criteria += ", and ";
					criteria += "assignee_id=" + d.getAssignee_id();
				}
			}

			LocalDateTime dt = d.getDate_entered();
			if (dt != null) {
				if (cnt > 0) {
					criteria += ", and ";
					criteria += "date_entered=" + d.getDate_entered();
				}
			}

			String stat = d.getStatus();
			if (stat != null) {
				if (cnt > 0) {
					criteria += ", and ";
					criteria += "status=" + d.getStatus();
				}
			}

			Integer p = d.getPriority_level();
			if (p != null) {
				if (cnt > 0) {
					criteria += ", and ";
					criteria += "priority_level=" + d.getPriority_level();
				}
			}

			if (cnt > 0) {
				q = "select * from Defect where " + criteria + ";";
			} else {
				q = "select * from Defect;";
			}
			st = con.createStatement(); // throwing error
			st.executeUpdate(q); // throwing error
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			System.out.println("Error with table or data1");

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
			System.out.println("Error with table or data2 in DefectDAO" + ex);
			
		}
		return hadSuccess;
	}
	
	private ArrayList<Defect> searchDefectDAO(String q){

		makeConnection();
		arrayList.clear();
		try {

			st = con.createStatement();
			rs = st.executeQuery(q);

			while (rs.next()) {
				int tempAssignee=0;
				int tempTicketID = Integer.parseInt(rs.getString(1));
				String tempSummary = rs.getString(2);
				String tempDesc = rs.getString(3);
				if (rs.getString(4) != null) {
					tempAssignee = Integer.parseInt(rs.getString(4));
				}
								
				int tempPriority = Integer.parseInt(rs.getString(5));
				Date dateTime = rs.getDate(6);
				Instant instant = Instant.ofEpochMilli(dateTime.getTime());
				LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
				int tempSubmitter = Integer.parseInt(rs.getString(7));
				String tempStatus = rs.getString(8);
				String tempComments = rs.getString(9);
				//Check for errors first.
				Defect i = new Defect(tempTicketID, tempSummary, tempDesc, tempAssignee, tempPriority, res,
						tempSubmitter, tempStatus, tempComments);
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

	
	
	private void makeConnection() {

		String url = LoginUserPanel.getURL();
		String user = "root";
		String password = LoginUserPanel.getSQLPassword();

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection made");

		} catch (Exception ex) {
			Logger lgr = Logger.getLogger(DefectDAO.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.out.println("Sql Exception");

		}

	}
	
}