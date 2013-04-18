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
	private Settings s;
	private Records r;
	
	public User(String userName) {
		this.userName = userName;
		
		// Read settings
		readSettings();
		
		// Read records
		readRecords();
		// TODO: Store the data into a new object
	}
	
	/**
	 * Reads the user's settings (if there are any) and store it to the instance variable "s".
	 * The filename is {userName}_settings.dat
	 */
	public void readSettings() {
		try {
			Scanner read = new Scanner(new File(userName + "_settings.dat"));
			// TODO: Read data from the given file
			s = new Settings(userName, read);
		}
		catch (FileNotFoundException e) {
			// TODO: Will actually write to a file when there is a change. Otherwise, it means the user is using the default settings.
			s = new Settings(userName);
		}
	}
	
	/**
	 * Reads the user's record from a file that has a filename {userName}_records.dat and
	 * store it to the instance variable "r".
	 * NOTE: make this method abstract and do everything in the Records class?
	 */
	public void readRecords() {
		try {
			Scanner read = new Scanner(new File(userName + "_records.dat"));
			// TODO: Read data
		}
		catch (FileNotFoundException e) {
			// Not doing anything because a file can be made once there is a new record.
			// TODO: Add an instance variable in the Records class that indicates whether it's a completely new record or not.
		}
	}
	
	/**
	 * Returns the setting instance for this user.
	 * TODO: make a clone method in Settings
	 */
	public Settings getSettings() {
		// return s.clone()
		return s;
	}
	
	/**
	 * Returns the records instance for this user.
	 * TODO: make a clone method in Records
	 * NOTE: Is this really necessary? The Records class is used only to store records.
	 */
	public Records getRecords() {
		// return s.clone()
		return r;
	}
}
