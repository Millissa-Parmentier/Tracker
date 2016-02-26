package tracker;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPanel extends JPanel {

	String[] priorities = { " ", "1", "2", "3", "4" };
	String[] stats = { " ", "Open", "Work in Progress", "Testing", "Deployed", "Cancelled", "Closed"}; 
	private UserDAO userDAO = new UserDAO();
	private ArrayList<String> subName = new ArrayList<String>();
	
	JLabel title = new JLabel("ADD DEFECT TO DATABASE");
	JLabel prioLabel = new JLabel("Priority");
	JComboBox priority = new JComboBox(priorities);
	JLabel statLabel = new JLabel("Status");
	JComboBox status = new JComboBox(stats);
	JLabel summLabel = new JLabel("Summary");
	JTextField summary = new JTextField(255);
	JLabel descriptLabel = new JLabel("Description");
	JTextField description = new JTextField(2500);
	JLabel commLabel = new JLabel("Comments");
	JTextField comments = new JTextField(2500);
	Object tempSubmitter;
	JLabel submitterLabel = new JLabel("Submitter");
	JComboBox submitter = new JComboBox(subName.toArray());

	JButton submit = new JButton("Add Defect");
	JButton clear = new JButton("Clear");
		
	AddDAO addDAO = new AddDAO();

	public AddPanel() {
		subName = userDAO.getSubmitter();
		JLabel submitterLabel = new JLabel("Submitter");
		JComboBox submitter = new JComboBox(subName.toArray());
		submitter.getSelectedItem();
		tempSubmitter = submitter.getSelectedItem();
			
		ButtonListener b = new ButtonListener();
		submit.addActionListener(b);
		clear.addActionListener(b);
		submitter.addActionListener(b);

		setLayout(new BorderLayout());
		title.setFont(new Font("Serif", Font.PLAIN, 16));
		add(title, BorderLayout.NORTH);

		JPanel textLabels = new JPanel(new GridLayout(7, 0));
		JPanel textBoxes = new JPanel(new GridLayout(7, 0));
		JPanel buttonSpace = new JPanel(new GridLayout(7, 0));

		textLabels.add(summLabel);
		textBoxes.add(summary);
		textLabels.add(descriptLabel);
		textBoxes.add(description);
		textLabels.add(submitterLabel);
		textBoxes.add(submitter);
		textLabels.add(prioLabel);
		textBoxes.add(priority);
		textLabels.add(statLabel);
		textBoxes.add(status);
		textLabels.add(commLabel);
		textBoxes.add(comments);
		textLabels.add(clear);
		textBoxes.add(submit);
		buttonSpace.add(clear);
		buttonSpace.add(submit);

		add(textLabels, BorderLayout.WEST);
		add(textBoxes, BorderLayout.CENTER);
		add(buttonSpace, BorderLayout.EAST);

	}

	class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == submit) {
				String tempSummary = summary.getText();
				String tempDescript = description.getText();
				String tempComment = comments.getText();
				int tempPrio = priority.getSelectedIndex();
				int tStat = status.getSelectedIndex();
				int tempSub = Integer.parseInt((String) tempSubmitter);
				String tempStat = stats[tStat];
				AddDefect t = new AddDefect(tempSummary, tempDescript, tempPrio, tempSub, tempStat, tempComment);
				addDAO.insertNewDefect(t);
				summary.setText("");
				description.setText("");
				priority.setSelectedIndex(0);
				status.setSelectedIndex(0);
				comments.setText("");

				System.out.println("Add new defect to database");
			}

			if (e.getSource() == clear) {
				summary.setText("");
				description.setText("");
				//submitter.setText("");
				priority.setSelectedIndex(0);
				status.setSelectedIndex(0);
				comments.setText("");
				//System.out.println("Clear fields in form");
			}
			revalidate();
			
		}

	}
}