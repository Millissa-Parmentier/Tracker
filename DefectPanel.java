package tracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;


public class DefectPanel extends JPanel{
	
	private DefectTableModel defectTableModel = new DefectTableModel();
	private JTable defectTable;
	private TableRowSorter<DefectTableModel> defectRowSorter = new TableRowSorter<DefectTableModel>(defectTableModel);
	private JScrollPane scroll;
	private JButton remove = new JButton("Remove Defect");
	private Font tableFont = new Font("Dialog", Font.PLAIN, 16);
	private Font headerFont = new Font("Dialog", Font.BOLD, 16);
	private int[] colWidths = {10, 200, 225, 10, 5, 50, 10, 10, 225};
	private String[] filterFields = defectTableModel.getSearchableDefectColumnsAsArray();
	private JComboBox<String> defectFieldBox = new JComboBox<String>(filterFields);
	private Integer[] filterColumns = defectTableModel.getSearchableDefectColumnNumbersAsArray();
	private final int SHOW_ALL = -1;   //index used to indicate show all.
 	private JLabel comboLabel = new JLabel("Filter table by:");
	private JLabel filterLabel = new JLabel("Where field contains:");
	private JTextField filterText = new JTextField("");
	private JButton filter = new JButton("Filter");
	private JLabel filterButtonLabel = new JLabel("Select and then click below to filter");
	private final Dimension CELL_SPACING = new Dimension(50, 5);
	private final Color HEADER_COLOR = new Color(0.6f,0.7f,0.8f);


	
	// Constructor
	public DefectPanel() {

		defectTable = new JTable(defectTableModel);
		setLayout(new BorderLayout());
		
		//Create a panel for filtering tools 
		JPanel filterPanel = new JPanel(new GridLayout(2, 3));
		filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		
		//Create combo box for filtering
		comboLabel.setFont(headerFont);
		filterLabel.setFont(headerFont);
		filterButtonLabel.setFont(headerFont);
		defectFieldBox.setFont(tableFont);
		filterText.setFont(tableFont);
		filterPanel.add(comboLabel);
		filterPanel.add(filterLabel);
		filterPanel.add(filterButtonLabel);
		filterPanel.add(defectFieldBox);
		filterPanel.add(filterText);
		filterPanel.add(filter);
		
		//need to add listener here for combo box and filtering
		add(filterPanel, BorderLayout.NORTH);
		
		//Set up the full main table
		fullDefectTableSetup();
    
	    //Set up scroll pane and add table to it.  Add it to the main panel.
		scroll  = new JScrollPane(defectTable);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(350, 200));
		add(scroll, BorderLayout.CENTER);
		
		//Add button listener and fonts
		ButtonListener l = new ButtonListener();
		defectFieldBox.addActionListener(l);
		remove.addActionListener(l);
		filter.addActionListener(l);;

		//Create a panel of buttons and add to main panel.
		JPanel buttonPanel = new JPanel();
		remove.setToolTipText("Select row and click here to remove.");
		remove.setFont(headerFont);
		buttonPanel.add(remove);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			getRootPane().setDefaultButton(filter);
			String option = (String)defectFieldBox.getSelectedItem();
			if (e.getSource().equals(remove)) {
				int row = defectTable.getSelectedRow();
				if(row == -1){
					String msg = "Please select a defect record to remove.";
					JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}			
				defectTableModel.removeDefectAt(defectTable.convertRowIndexToModel(row));
	
			}else if (option == "SHOW ALL") {
				defectTableModel.searchAll();
			}else if (option == "Ticket ID") {
				String ticketID = filterText.getText();
				defectTableModel.searchOnTicketID(ticketID);
			}else if (option == "Summary") {
				String summary = filterText.getText();
				defectTableModel.searchOnSummary(summary);
			}else if (option == "Description") {
				String desc = filterText.getText();
				defectTableModel.searchOnDesc(desc);
			}else if (option == "Assigned to") {
				String assignedTo = filterText.getText();
				defectTableModel.searchOnAssignedTo(assignedTo);
			}else if (option == "Priority") {
				String priority = filterText.getText();
				defectTableModel.searchOnPriority(priority);
			}else if (option == "Date Entered") {
				String dateEntered = filterText.getText();
				defectTableModel.searchOnDateEntered(dateEntered);
			}else if (option == "Submitted by") {
				String submittedBy = filterText.getText();
				defectTableModel.searchOnSubmittedBy(submittedBy);
			}else if(e.getSource().equals(filter)){	
				try{
					int columnSelected = filterColumns[defectFieldBox.getSelectedIndex()];
					if (columnSelected == SHOW_ALL){
						defectTable.setRowSorter(null);
	
					}else{
				        String text = filterText.getText().trim();
				        defectRowSorter.setRowFilter(RowFilter.regexFilter(text, columnSelected));
				        defectTable.setRowSorter(defectRowSorter);
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
			defectTable.repaint();
			fullDefectTableSetup();
		}
	}
	
	
	private void fullDefectTableSetup(){
	    for(int j = 0; j < defectTable.getColumnCount(); j++){
	    	String columnName = defectTable.getColumnName(j);
	    	TableColumn tableColumn = defectTable.getColumn(defectTable.getColumnName(j));
	    	tableColumn.setPreferredWidth(colWidths[j]); 
	    	if(columnName.equals("Date Entered")){
	    		defectTable.getColumnModel().getColumn(j).setCellRenderer(new DateCellRenderer());
	    	}
	    }
		defectTable.setFont(tableFont);
		defectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		defectTable.setSelectionMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		defectTable.setDragEnabled(false);
		defectTable.setColumnSelectionAllowed(false);
		TableRowSorter<DefectTableModel> defectSorter = new TableRowSorter<DefectTableModel>(defectTableModel);
	    defectTable.setRowSorter(defectSorter);
	    defectTable.setIntercellSpacing(CELL_SPACING);
	    JTableHeader header = defectTable.getTableHeader();
	    header.setBackground(HEADER_COLOR);
	    header.setFont(headerFont);
	    defectTable.setRowHeight(25);
	    TableColumnModel colModel = defectTable.getColumnModel();
	    for(int col = 0; col < colWidths.length; col++){
	    	
	    	if (!defectTableModel.isColumnViewable(col)){
	    		int viewCol = defectTable.convertColumnIndexToView(col);
	    		if (viewCol != -1){defectTable.removeColumn(colModel.getColumn(viewCol));
	    		}
	    	}
	    }
	}
    private class DateCellRenderer extends DefaultTableCellRenderer {
    	
        public DateCellRenderer() { super(); }

        @Override
        public void setValue(Object data) {
    		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/YY  hh:mm a");
            if(data instanceof LocalDateTime){setText(((LocalDateTime) data).format(f));}
            else {setText("");}        
        }
    }
	
}//end UserPanel