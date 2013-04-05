import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class User {
	private String userName;
	// TODO: Add instance variables for the records and settings
	
	public User(String userName) {
		this.userName = userName;
		
		// Read settings
		readSettings();
		
		// Read records
		readRecords();
		// TODO: Store the data into a new object
	}
	
	public void readSettings() {
		try {
			// TODO: Read data
			Scanner read = new Scanner(new File(userName + "_settings.dat"));
		}
		catch (FileNotFoundException e) {
			// TODO: Make a new settings file (probably a new method)
			System.out.println("No settings data found");
		}
	}
	
	public void readRecords() {
		try {
			Scanner read = new Scanner(new File(userName + "_records.dat"));
			// TODO: Read data
		}
		catch (FileNotFoundException e) {
			// TODO: Make a new records file (no need for that
			// and do it when there's a new record?)
			System.out.println("No records found");
		}
	}
}
