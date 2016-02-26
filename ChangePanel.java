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

public class ChangePanel extends JPanel{
	
	private ChangeTableModel changeTableModel = new ChangeTableModel();
	private JTable changeTable;
	private TableRowSorter<ChangeTableModel> ChangeRowSorter = new TableRowSorter<ChangeTableModel>(changeTableModel);
	private JScrollPane scroll;
	private Font tableFont = new Font("Dialog", Font.PLAIN, 16);
	private Font headerFont = new Font("Dialog", Font.BOLD, 16);
	private int[] colWidths = {10, 200, 225, 10, 5, 50, 10, 10, 225};
	private String[] filterFields = changeTableModel.getSearchableChangeColumnsAsArray();
	private JComboBox<String> changeFieldBox = new JComboBox<String>(filterFields);
	private Integer[] filterColumns = changeTableModel.getSearchableChangeColumnNumbersAsArray();
	private final int SHOW_ALL = -1;   //index used to indicate show all.
 	private JLabel comboLabel = new JLabel("Filter table by:");
	private JLabel filterLabel = new JLabel("Where field contains:");
	private JTextField filterText = new JTextField("");
	private JButton filter = new JButton("Filter");
	private JLabel filterButtonLabel = new JLabel("Select and then click below to filter");
	private final Dimension CELL_SPACING = new Dimension(50, 5);
	private final Color HEADER_COLOR = new Color(0.6f,0.7f,0.8f);

	// Constructor
	public ChangePanel() {

		changeTable = new JTable(changeTableModel);
		setLayout(new BorderLayout());
		
		//Create a panel for filtering tools 
		JPanel filterPanel = new JPanel(new GridLayout(2, 3));
		filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		
		//Create combo box for filtering
		comboLabel.setFont(headerFont);
		filterLabel.setFont(headerFont);
		filterButtonLabel.setFont(headerFont);
		changeFieldBox.setFont(tableFont);
		filterText.setFont(tableFont);
		filterPanel.add(comboLabel);
		filterPanel.add(filterLabel);
		filterPanel.add(filterButtonLabel);
		filterPanel.add(changeFieldBox);
		filterPanel.add(filterText);
		filterPanel.add(filter);
		
		//need to add listener here for combo box and filtering
		add(filterPanel, BorderLayout.NORTH);
		
		//Set up the full main table
		fullChangeTableSetup();
    
	    //Set up scroll pane and add table to it.  Add it to the main panel.
		scroll  = new JScrollPane(changeTable);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(350, 200));
		add(scroll, BorderLayout.CENTER);
		
		//Add button listener and fonts
		ButtonListener l = new ButtonListener();
		changeFieldBox.addActionListener(l);
		filter.addActionListener(l);;
	}

	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			getRootPane().setDefaultButton(filter);
			String option = (String)changeFieldBox.getSelectedItem();
			if (option == "SHOW ALL") {
				changeTableModel.searchAll();
			}else if (option == "Ticket ID") {
				String ticketID = filterText.getText();
				changeTableModel.searchOnTicketID(ticketID);
			}else if (option == "Change Date") {
				String changeDate = filterText.getText();
				changeTableModel.searchOnChangeDate(changeDate);
			}else if (option == "Field Name") {
				String fieldName = filterText.getText();
				changeTableModel.searchOnFieldName(fieldName);
			}else if (option == "Old Value") {
				String oldValue = filterText.getText();
				changeTableModel.searchOnOldValue(oldValue);
			}else if(e.getSource().equals(filter)){	
				try{
					int columnSelected = filterColumns[changeFieldBox.getSelectedIndex()];
					if (columnSelected == SHOW_ALL){
						changeTable.setRowSorter(null);
	
					}
				}
				catch (PatternSyntaxException PSe){
					String msg = "Invalid search string in ChangePanel.";
					JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception ex){
					String msg = "Error filtering data in ChangePanel.";
					JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				filterText.setText("");
			}
			revalidate();
			changeTable.repaint();
			//fullDefectTableSetup();
		}
	}
	
	
	private void fullChangeTableSetup(){
	    for(int j = 0; j < changeTable.getColumnCount(); j++){
	    	String columnName = changeTable.getColumnName(j);
	    	TableColumn tableColumn = changeTable.getColumn(changeTable.getColumnName(j));
	    	tableColumn.setPreferredWidth(colWidths[j]); 
	    	/*if(columnName.equals("Change Date")){
	    		changeTable.getColumnModel().getColumn(j).setCellRenderer(new DateCellRenderer());
	    	}*/
	    }
		changeTable.setFont(tableFont);
		changeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		changeTable.setSelectionMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		changeTable.setDragEnabled(false);
		changeTable.setColumnSelectionAllowed(false);
		TableRowSorter<ChangeTableModel> changeSorter = new TableRowSorter<ChangeTableModel>(changeTableModel);
	    changeTable.setRowSorter(changeSorter);
	    changeTable.setIntercellSpacing(CELL_SPACING);
	    JTableHeader header = changeTable.getTableHeader();
	    header.setBackground(HEADER_COLOR);
	    header.setFont(headerFont);
	    changeTable.setRowHeight(25);
	    TableColumnModel colModel = changeTable.getColumnModel();
	    /*for(int col = 0; col < colWidths.length; col++){
	    	
	    	if (!changeTableModel.isColumnViewable(col)){
	    		int viewCol = changeTable.convertColumnIndexToView(col);
	    		if (viewCol != -1){changeTable.removeColumn(colModel.getColumn(viewCol));
	    		}
	    	}
	    }*/
	}
    /*private class DateCellRenderer extends ChangeTableCellRenderer {
    	
        public DateCellRenderer() { super(); }

        @Override
        public void setValue(Object data) {
    		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/YY  hh:mm a");
            if(data instanceof LocalDateTime){setText(((LocalDateTime) data).format(f));}
            else {setText("");}        
        }
    }*/
	
}//end UserPanel}