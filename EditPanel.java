package tracker;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EditPanel extends JPanel {
	
	JPanel editPanel = new JPanel();

	public EditPanel(TrackerPane tracker) {
		JButton button = new JButton("fake edit");
		this.add(button);	
	}
}