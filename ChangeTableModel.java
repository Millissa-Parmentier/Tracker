/**
 * 
 */
package tracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ChangeTableModel extends AbstractTableModel{
	
	protected String[] changeColumnNames = { "Ticket ID", "Change Date", "Field Name", "Old Value",};
	private boolean[][] canEditChangeField = {{false, false, false, false},
											{false, false, false, false},
											{false, false, false, false}};
private boolean[][] canViewChangeField = {{true, true, true, true},
										{true, true, true, true},
										{true, true, true, true}};
		private ChangeDAO changeDAO = new ChangeDAO();
	private ArrayList<Change> changeModel = new ArrayList<Change>(changeDAO.arrayList);
	private ArrayList<String> searchableChangeColumns = new ArrayList<String>();
	private ArrayList<Integer> searchableChangeColumnNumbers = new ArrayList<Integer>();
	private int access;

	
	// Constructor
	public ChangeTableModel() {
		this.access = LoginUserPanel.getAccess();
		setSearchableChangeColumns();
	}

	
	public Change getChangeAt(int row) {
		return this.changeModel.get(row);
	}
	
	public void searchAll() {
		changeModel  = changeDAO.searchAll();
	}

	public void searchOnTicketID(String option) {
		changeModel  = changeDAO.searchTicketID(option);
	}
	
	public void searchOnChangeDate(String option) {
		changeModel  = changeDAO.searchChangeDate(option);
	}
	
	public void searchOnFieldName(String option) {
		changeModel  = changeDAO.searchFieldName(option);
	}
	
	public void searchOnOldValue(String option) {
		changeModel  = changeDAO.searchOldValue(option);
	}
				
	
	@Override
	public boolean isCellEditable(int row, int col) {		
		return canEditChangeField[access][col];
	}

	
	public boolean isColumnViewable(int col) {		
		return canViewChangeField[access][col];
	}

	private  void setSearchableChangeColumns(){
		searchableChangeColumns.add("SHOW ALL");
		searchableChangeColumnNumbers.add(-1);

		for(int col = 0; col < getColumnCount(); col++){
			if(canViewChangeField[access][col]){
				searchableChangeColumns.add(getColumnName(col));
				searchableChangeColumnNumbers.add(col);
				System.out.println(getColumnName(col)+ "   "+col);
			}
		}
	}
	
	public String[] getSearchableChangeColumnsAsArray(){
		return searchableChangeColumns.toArray(new String[searchableChangeColumns.size()]);
	}
	
	public Integer[] getSearchableChangeColumnNumbersAsArray(){
		return searchableChangeColumnNumbers.toArray(new Integer[searchableChangeColumnNumbers.size()]);
	}

	@Override
	public int getColumnCount() {
		return changeColumnNames.length;
	}// end getColumnCount

	
	@Override
	public String getColumnName(int column) {
		return this.changeColumnNames[column];
	}// end getColumnName

	
	@Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
	}

	@SuppressWarnings("finally")
	@Override
	public int getRowCount() {
		return changeModel.size();
	}// end getRowCount


	
	@Override
	public Object getValueAt(int row, int col) {
		switch (col) {

		case 0:
			return this.changeModel.get(row).getTicketID();
		case 1:
			return this.changeModel.get(row).getChangeDate();
		case 2:
			return this.changeModel.get(row).getFieldName();
		case 3:
			return this.changeModel.get(row).getOldValue();
		default:
			popUpErrorMessage("Data access error.");
			return null;
		}
	}// end getValueAt
	
	/*public void setValueAt(Object value, int row, int col) {

		Object oldValue = getValueAt(row, col);
		if (!(oldValue.equals(value))){
			boolean hadSuccess = changeDAO.replaceFieldAt(value, getChangeAt(row), col);
			//boolean hadSuccess = false;
			
			if (hadSuccess){
				popUpSuccessMessage("Change saved to database.");
				setTableValueAt(value, row, col);
			}else{
				popUpErrorMessage("Error changing in database.");
				setTableValueAt(oldValue, row, col);
			} 
		} 			
	}// end setValueAt

	
	public void setTableValueAt(Object value, int row, int col) {

		switch (col) {

		case 0:
			changeModel.get(row).setTicketID(((int) value));
			break;		
		case 1: 
			changeModel.get(row).setChangeDate((String) value);
			break;		
		case 2: 
			changeModel.get(row).set((String) value);
			break;			
		case 3: 
			changeModel.get(row).setAssignee_id((int) value);
			break;			
		case 4: 
			changeModel.get(row).setPriority_level((int) value);
			break;		
		case 5: 
			changeModel.get(row).setDate_entered((LocalDateTime) value);
			break;		
		case 6: 
			changeModel.get(row).setSubmitter((int) value);
			break;		
		case 7: 
			changeModel.get(row).setStatus((String) value);	
			break;
		case 8: 
			changeModel.get(row).setComments((String) value);
			break;
		default:
			popUpErrorMessage("Error retrieving data.");
		}//end switch
		fireTableCellUpdated(row, col);
	}*/
	
	public int findChangeRow(int ID) {
		int row = -1;
		for (int i = 0; i < getRowCount(); i++) {
			if (((int) getValueAt(i, 0)) == ID) {
				row = i;
				break;
			}
		}
		return row;
	}

	
	private void popUpErrorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	
	private void popUpSuccessMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);	
	} 
	
}// end ChangeTableModel