package tracker;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * This is a panel object for entering new users into the user database.
 */

public class AddUserPanel extends JPanel {

	JTable userTable;
	JTable filterTable;
	JLabel title = new JLabel("ADD USER TO DEFECT TRACKER SYSTEM");
	JLabel userLabel = new JLabel("User ID");
	JTextField userField = new JTextField(10);
	JLabel lNameLabel = new JLabel("Last Name");
	JTextField lName = new JTextField(50);
	JLabel fNameLabel = new JLabel("First Name");
	JTextField fName = new JTextField(50);
	JLabel positionLabel = new JLabel("Position");
	JTextField position = new JTextField(75);
	JLabel accessLevelLabel = new JLabel("Access Level");
	JTextField accessLevel = new JTextField(2);
	JLabel teamLabel = new JLabel("Team");
	JTextField team = new JTextField(50);
	JLabel emailLabel = new JLabel("Email");
	JTextField email = new JTextField(100);
	JLabel passwordLabel = new JLabel("Password");
	JTextField password = new JTextField(15);
	JButton submit = new JButton("Add User");
	UserTableModel userTableModel;
	TrackerPane tracker;

	public AddUserPanel(TrackerPane tracker) {
		this.tracker = tracker;
		userTable = new JTable(userTableModel);
		userTableModel = new UserTableModel();

		ButtonListener b = new ButtonListener();
		submit.addActionListener(b);

		setLayout(new BorderLayout());

		JPanel buttonLabels = new JPanel(new GridLayout(8, 0));
		JPanel textBoxes = new JPanel(new GridLayout(8, 0));

		//Can we do access level as drop down?  What about Team, position?
		//This will mean less error checking.
		buttonLabels.add(userLabel);
		userField.setEditable(false); // this is auto-inc
		textBoxes.add(userField);
		buttonLabels.add(lNameLabel);
		textBoxes.add(lName);
		buttonLabels.add(fNameLabel);
		textBoxes.add(fName);
		buttonLabels.add(positionLabel);
		textBoxes.add(position);
		buttonLabels.add(accessLevelLabel);
		textBoxes.add(accessLevel);
		buttonLabels.add(teamLabel);
		textBoxes.add(team);
		buttonLabels.add(emailLabel);
		textBoxes.add(email);
		buttonLabels.add(passwordLabel);
		textBoxes.add(password);

		add(buttonLabels, BorderLayout.WEST);
		add(textBoxes, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		buttonPanel.add(submit);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//needs try-catch for numberformat exception
			
			if (e.getSource() == submit) {
				int tempUserInt = -1;           //temporary because auto-int
				String tempLName = lName.getText().trim();
				String tempFName = fName.getText().trim();
				String tempPosition = position.getText().trim();
				String tempAccessLevel = accessLevel.getText().trim();;
				String tempTeam = team.getText().trim();
				String tempEmail = email.getText().trim();
				String tempPassword = password.getText().trim();

				int tempAccessLevelInt = Integer.parseInt(tempAccessLevel);
				
				//Need error checking
				User user = new User(tempUserInt,tempPassword, tempLName, tempFName, tempPosition, tempAccessLevelInt, tempTeam, tempEmail);
				userTableModel.addUser(user);
				lName.setText("");
				fName.setText("");
				position.setText("");
				accessLevel.setText("");
				team.setText("");
				email.setText("");
				password.setText("");
				
				tracker.updateUserPanel();
			}
		}
	}

}
