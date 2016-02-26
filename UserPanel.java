package tracker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;

public class UserPanel extends JPanel {
	
	private UserTableModel userTableModel = new UserTableModel();
	private JTable userTable;
	private TableRowSorter<UserTableModel> userRowSorter = new TableRowSorter<UserTableModel>(userTableModel);
	private JScrollPane scroll;
	private JButton enter = new JButton("Search user");
	private JButton remove = new JButton("Remove user");
	private Font tableFont = new Font("Dialog", Font.PLAIN, 16);
	private Font headerFont = new Font("Dialog", Font.BOLD, 16);
	private String[] filterFields = userTableModel.getSearchableUserColumnsAsArray();
	private JComboBox<String> userFieldBox = new JComboBox<String>(filterFields);
	private Integer[] filterColumns = userTableModel.getSearchableUserColumnNumbersAsArray();
	private final int SHOW_ALL = -1;   //index used to indicate show all.
    private int[] colWidths = {10, 100, 100, 100, 100, 10, 100, 150};
	private JLabel comboLabel = new JLabel("Filter table by:");
	private JLabel filterLabel = new JLabel("Where field contains:");
	private JTextField filterText = new JTextField("");
	private final Dimension USER_CELL_SPACING = new Dimension(5, 0);
	private final Color USER_HEADER_COLOR = new Color(0.6f,0.7f,0.8f);
	private JButton filter = new JButton("Filter");
	private JLabel filterButtonLabel = new JLabel("Select and then click below to filter");
	
	 
	// Constructor
	public UserPanel() {
		
		userTable = new JTable(userTableModel);
		setLayout(new BorderLayout());
		
		
		//Create a panel with filtering tools 
		JPanel filterPanel = new JPanel(new GridLayout(2, 3));
		filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		comboLabel.setFont(headerFont);
		filterLabel.setFont(headerFont);
		filterButtonLabel.setFont(headerFont);
		userFieldBox.setFont(tableFont);
		filterText.setFont(tableFont);
		filterPanel.add(comboLabel);
		filterPanel.add(filterLabel);
		filterPanel.add(filterButtonLabel);
		filterPanel.add(userFieldBox);
		filterPanel.add(filterText);
		filterPanel.add(filter);
		add(filterPanel, BorderLayout.NORTH);
		
		//Set up the full main table
		fullUserTableSetup();
    
	    //Set up scroll pane and add table to it.  Add it to the main panel.
		scroll  = new JScrollPane(userTable);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(350, 200));
		add(scroll, BorderLayout.CENTER);
		
		//Add button listener and fonts
		ButtonListener l = new ButtonListener();
		remove.addActionListener(l);
		userFieldBox.addActionListener(l);
		filter.addActionListener(l);
		
		//Create a panel of buttons and add to main panel.
		JPanel buttonPanel = new JPanel();
		remove.setToolTipText("Select row and click here to remove.");
		//enter.setFont(headerFont);
		remove.setFont(headerFont);
		//buttonPanel.add(enter);
		buttonPanel.add(remove);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			getRootPane().setDefaultButton(filter);
			String option = (String)userFieldBox.getSelectedItem();
			if (e.getSource().equals(remove)) {
				int row = userTable.getSelectedRow();
				if(row == -1){
					String msg = "Please select a user to remove.";
					JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				userTableModel.removeUserAt(userTable.convertRowIndexToModel(row));
			}else if (option == "Show All") {
				userTableModel.searchOnAll();
			}else if (option == "User ID") {
				String userID = filterText.getText();
				userTableModel.searchOnUserID(userID);
			}else if (option == "Last Name") {
				String lName = filterText.getText();
				userTableModel.searchOnLName(lName);
			}else if (option == "First Name") {
				String fName = filterText.getText();
				userTableModel.searchOnFName(fName);
			}else if (option == "Position") {
				String position = filterText.getText();
				userTableModel.searchOnPosition(position);
			}else if (option == "Access Level") {
				String accessLevel = filterText.getText();
				userTableModel.searchOnAccessLevel(accessLevel);
			}else if (option == "Team Name") {
				String team = filterText.getText();
				userTableModel.searchOnTeam(team);
			}else if (option == "Email Address") {
				String email = filterText.getText();
				userTableModel.searchOnEmail(email);
			}else if(e.getSource().equals(filter)){	
				try{
					int columnSelected = filterColumns[userFieldBox.getSelectedIndex()];
					if (columnSelected == SHOW_ALL){
						userTable.setRowSorter(null);

					}else{
				        String text = filterText.getText().trim();
				        userRowSorter.setRowFilter(RowFilter.regexFilter(text, columnSelected));
				        userTable.setRowSorter(userRowSorter);
					}
				}
				catch (PatternSyntaxException PSe){
					String msg = "Invalid search string.";
					JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception ex){
					String msg = "Error filtering data.";
					JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				filterText.setText("");
			}
			revalidate();
            userTable.repaint();
		}
	}
	
	
	private void fullUserTableSetup(){

		userTable.setFont(tableFont);
		userTable.setAutoCreateColumnsFromModel(false);
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userTable.setSelectionMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		userTable.setDragEnabled(false);
		userTable.setColumnSelectionAllowed(false);
		TableRowSorter<UserTableModel> userRowSorter = new TableRowSorter<UserTableModel>(userTableModel);
	    userTable.setRowSorter(userRowSorter);
	    userTable.setIntercellSpacing(USER_CELL_SPACING);
	    JTableHeader header = userTable.getTableHeader();
	    header.setBackground(USER_HEADER_COLOR);
	    header.setFont(headerFont);
	    userTable.setRowHeight(25);
	    for(int col = 0; col < colWidths.length; col++){
	    	userTable.getColumn(userTable.getColumnName(col)).setPreferredWidth(colWidths[col]);
	     } 
	    TableColumnModel colModel = userTable.getColumnModel();
	    for(int col = 0; col < colWidths.length; col++){
	    	if (!userTableModel.isColumnViewable(col)){
	    		int viewCol = userTable.convertColumnIndexToView(col);
	    		if (viewCol != -1){userTable.removeColumn(colModel.getColumn(viewCol));
	    		}
	    	}
	    }
	}//end fullUserTableSetup
	
}//end UserPanel
