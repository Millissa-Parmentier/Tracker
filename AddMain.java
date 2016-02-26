package tracker;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AddMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame main = new JFrame();
		JPanel addPanel = new AddPanel();
		main.add(addPanel);
		
		main.setSize(700, 400);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		
	}

}