package tracker;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * This is a panel object that displays a login screen and handles logging in and out.
 */
//Renamed class and Constructor to start with capital.
public class LoginUserPanel extends JPanel {

	//String[] User = {"userID", "password"};   //don't need this?
	
	private JLabel loginInstructions = new JLabel("Please enter your user ID and password to access the tracker system");
	private JLabel userIDLabel = new JLabel ("UserID");
	private JTextField userIDText = new JTextField(50);
	private JLabel passwordLabel = new JLabel("Password");
	private JTextField passwordText = new JTextField(50);
	private JButton login = new JButton("login");
	private JButton logout = new JButton("logout");
	private TrackerPane tracker;

	private static int access;
	
	//Added a JPanel in the constructor so that we can 
	//pass it the name of the main panel when needed.
	public LoginUserPanel(TrackerPane tracker, boolean isLoggedIn){
		
		this.tracker = tracker;
	
		ButtonListener b = new ButtonListener();
		login.addActionListener(b);		
		logout.addActionListener(b);	

		setLayout(new BorderLayout());
		
		loginInstructions.setFont(new Font("Serif", Font.PLAIN, 16));
		add(loginInstructions, BorderLayout.NORTH);
		
		JPanel buttonLabels = new JPanel(new GridLayout(2,0));
		JPanel textBoxes = new JPanel(new GridLayout(2,0));	
		
		buttonLabels.add(userIDLabel);
		textBoxes.add(userIDText);
		buttonLabels.add(passwordLabel);
		textBoxes.add(passwordText);
	
		add(buttonLabels, BorderLayout.WEST);
		add(textBoxes, BorderLayout.CENTER);
		
		add(buttonLabels, BorderLayout.WEST);
		add(textBoxes, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(login);
		buttonPanel.add(logout);
		
		add(buttonPanel, BorderLayout.SOUTH);		
		
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource().equals(login)) {
				
				String tempUserID = userIDText.getText();
				String tempPassword = passwordText.getText();
				UserDAO i = new UserDAO();
				boolean isLoggedIn = i.findUserDAO(tempUserID, tempPassword);
				System.out.println(isLoggedIn + " LoginUserPanel isLoggedIn");
				System.out.println("UID and PW entered");
				System.out.println(i + " i in LoginUserPanel");
				 
				if(isLoggedIn){
					tracker.LogIn();
				} else {
					String msg = "UserID and/or Password not found";
					JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				//Added procedure for logout.
			}else if (e.getSource().equals(logout)){
					tracker.LogOut();
			}
	
		}

	}
	public static int getAccess(){
		return access;
	}
	
	public static String getSQLPassword(){
		return "Kenzie#1";
	}
	
	public static String getURL(){
		return "jdbc:mysql://localhost:3306/tracker";
	}
}