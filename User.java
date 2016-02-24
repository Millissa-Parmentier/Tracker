package tracker;

/**
 * This is the User object containing data for all users.
 */

/**
 * @author Kerty Levy
 */

public class User {

	private int userID;
	private String password;
	private String lastName;
	private String firstName;
	private String position;
	private int accessLevel;
	private String team;
	private String email;
	
	public User(int i, String p, String ln, String fn, String r, int al, String t, String e){
		userID = i;
		password = p;
		lastName = ln;
		firstName = fn;
		position = r;
		accessLevel = al;
		team = t;
		email = e;
		
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
