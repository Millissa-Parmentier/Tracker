package tracker;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This is the runnable program object for the Defect Tracker System.
 */
public class DefectTracker extends JFrame {
	
	
	/**
	 * @param args
	 */	

	
	public static void main(String[] args) {

		//Set up the frame
		JFrame frame = new JFrame("The Tracker");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height = screenSize.height - 100;
		frame.setSize(screenSize.width,screenSize.height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Create an opaque panel
		JPanel content = new JPanel();
		content.setOpaque(true);
		content.setPreferredSize(screenSize);
		
		//Make the panel the content pane and add the tracker to it.
		frame.setContentPane(content);
		content.add(new TrackerPane(content), BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);	
		
	}//end main
	
	}// end DefectTracker