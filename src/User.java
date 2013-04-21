import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		s = new Settings(userName + "_settings.dat");
		r = new Records(userName + "_records.dat");
		
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
		s.readSettings();
	}
	
	/**
	 * Writes the user's settings data to a file.
	 */
	public void writeSettings() {
		s.writeSettings();
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
	 * Returns the user name of this user.
	 * @return The user name of this user in String.
	 */
	public String getUserName() {
		return userName;
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
