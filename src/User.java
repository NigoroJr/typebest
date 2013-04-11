import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * This class is for storing the user's information such as
 * the settings, records, fonts, and the size of the window etc.
 * 
 * @author Naoki Mizuno
 *
 */

public class User {
	private String userName;
	// TODO: Add instance variables for the records and settings
	
	public User(String userName) {
		this.userName = userName;
		
		// Read settings
		Settings s = null;
		readSettings(s);
		
		// Read records
		Records r = null;
		readRecords(r);
		// TODO: Store the data into a new object
		
	}
	
	public void readSettings(Settings s) {
		try {
			// TODO: Read data
			Scanner read = new Scanner(new File(userName + "_settings.dat"));
		}
		catch (FileNotFoundException e) {
			int choice = JOptionPane.showConfirmDialog(null,
					"No settings found.\nCreat a new setting file?", "No setting file found",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			// Can't proceed without creating a setting file
			if (choice == 1)
				System.exit(0);
			
			// TODO: Write default settings or write to a file when there is a change in the setting?
			// Create a new Settings object with the default settings with only the userName
			s = new Settings(userName);
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(userName + "_settings.dat"));
				
				bw.write(s.toString());
				
				bw.flush();
				bw.close();
			}
			catch (IOException newSettings) {
				JOptionPane.showMessageDialog(null, "Failed to create a new settings file.");
				System.exit(0);
			}
		}	// End of catch when no setting file exists
	}
	
	public void readRecords(Records r) {
		try {
			Scanner read = new Scanner(new File(userName + "_records.dat"));
			// TODO: Read data
		}
		catch (FileNotFoundException e) {
			// Not doing anything because a file can be made once there is a new record.
			// TODO: Add an instance variable in the Records class that indicates whether it's a completely new record or not.
		}
	}
}
