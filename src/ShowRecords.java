import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class shows the list of records with the time, miss, user name, keyboard layout, and date.
 * @author Naoki Mizuno
 *
 */

public class ShowRecords {
	
	public ShowRecords(ArrayList<Result> records) {
		JFrame frame = new JFrame("Results");
		JPanel[] listOfResult = new JPanel[records.size()];
		Collections.sort(records);
		
		GridBagLayout gridBag = new GridBagLayout();
		
		GridBagConstraints oneResult = new GridBagConstraints();
		for (int i = 0; i < records.size(); i++) {
			Result r = records.get(i);
			String date = String.format("%2d/%2d/%4d %2d:%02d:%2d",
					r.getDate().get(Calendar.MONTH), r.getDate().get(Calendar.DATE), r.getDate().get(Calendar.YEAR),
					r.getDate().get(Calendar.HOUR_OF_DAY), r.getDate().get(Calendar.MINUTE), r.getDate().get(Calendar.SECOND));
			String resultText = String.format("%-3d %10f    %2d %12s %8s    %s",
					i + 1, r.getTime(), r.getMiss(), r.getUserName(), r.getKeyboardLayout(), date);
			
			JLabel label = new JLabel(resultText);
			label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
			label.setPreferredSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));
			// Use random color to make it easier to see the boundaries
			label.setBackground(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));
			label.setOpaque(true);
			
			oneResult.gridx = 0;
			oneResult.gridy = i;
			gridBag.setConstraints(label, oneResult);
			
		}
		
		frame.setLayout(gridBag);
		frame.setLocationRelativeTo(null);
		frame.setSize(800, 400);
		// Simply moves the window so that it covers the MainWindow
		frame.setLocation(frame.getLocation().x - frame.getSize().width / 2,
				frame.getLocation().y - frame.getSize().height / 2);
		frame.setVisible(true);
	}
}
