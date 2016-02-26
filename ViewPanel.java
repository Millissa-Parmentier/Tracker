package tracker;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ViewPanel extends JPanel {
	
	// private DefectTable table;

	private final int BORDER_LENGTH = 20;
	private JScrollPane scroll;
	private Integer[][] fakearray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
	private String[] faketitles = { "numA", "numB", "numC" };

	public ViewPanel(TrackerPane tracker) {
		
		JTable table = new JTable(fakearray, faketitles);
		scroll = new JScrollPane(table);
		scroll.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.add(scroll);
		
	}
}