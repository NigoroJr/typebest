import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;



/**
 * This class is the main panel for the typing section
 * in the main window. It is where the words to type appear
 * and the user knows what to type, whether s/he typed right,
 * and what to type next.
 * @author naoki
 *
 */

public class MainTypePanel extends JPanel {
	// TODO: Add panels (maybe representing lines?)
	public MainTypePanel() {
		
//		this.setPreferredSize(new Dimension(super.getPreferredSize().width,
//				super.getPreferredSize().height));
//		this.setSize(400, 300);
		this.setLayout(new FlowLayout());
		this.setBackground(Color.red);
	}
}
