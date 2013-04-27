import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
		
		// Read records
		// NOTE: This has to be before reading settings in order to set
		// the keyboard layout to the layout that was used in the last round.
		readRecords();
		
		// Read settings
		readSettings();
	}
	
	/**
	 * Reads the user's settings (if there are any) and store it to the instance variable "s".
	 * The filename is {userName}_settings.dat
	 */
	public void readSettings() {
		s.readSettings();
		
		// Make the current keyboard the same as the layout that was used in the last round.
		s.setKeyboardLayout(r.getExistingKeyboardLayouts().get(r.getExistingKeyboardLayouts().size() - 1));
	}
	
	/**
	 * Writes the user's settings data to a file.
	 */
	public void writeSettings() {
		s.writeSettings();
	}
	
	/**
	 * Reads the user's record from a file that has a filename {userName}_records.dat and
	 * store it to an instance variable.
	 * Because the Records class is unable to access the default keyboard that the user is using,
	 * it will be added here to the ArrayList of existing keyboards.
	 * This will be effective when there is no records file and the Records class can't read any existing
	 * keyboard layouts.
	 * NOTE: make this method abstract and do everything in the Records class?
	 */
	public void readRecords() {
		r.readRecords();
		
		// Add the default keyboard layout if it doesn't exist in the ArrayList in the Records class.
		if (!r.getExistingKeyboardLayouts().contains(s.getKeyboardLayout()))
			r.getExistingKeyboardLayouts().add(s.getKeyboardLayout());
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
