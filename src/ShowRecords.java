import java.awt.GridLayout;
import java.util.ArrayList;
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
		frame.setLayout(new GridLayout(0, 1));
		
		JPanel[] listOfResult = new JPanel[records.size()];
		Collections.sort(records);
		
		// TODO: Add panels with the records to listOfResult (or the frame)
		for (int i = 0; i < records.size(); i++) {
			Result r = records.get(i);
			String resultText = String.format("%-3d %10f          %2d          %s          %s          %s", i + 1, r.getTime(), r.getMiss(), r.getUserName(), r.getKeyboardLayout(),r.getDate().getTime());
			//System.out.println(records.get(i).toString());
			JLabel label = new JLabel(resultText);
			frame.add(label);
		}
		
		frame.setLocationRelativeTo(null);
		frame.setSize(800, 400);
		// Simply moves the window so that it covers the MainWindow
		frame.setLocation(frame.getLocation().x - frame.getSize().width / 2,
				frame.getLocation().y - frame.getSize().height / 2);
		frame.setVisible(true);
	}
}
