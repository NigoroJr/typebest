/**
 * This class stores information about the user's record
 * @author Naoki Mizuno
 *
 */

// TODO: Make the file a binary file so that the data can't be edited easily

public class Records {
	private String userName;
	private boolean first = false;
	
	public Records() {
		this("Anonymous");
	}
	
	public Records(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Sets the instance variable first to the given value.
	 * This will be used when reading the records file, and will be set to true
	 * if no records file was found.
	 * I made this because the settings file can be used to store information about how the user
	 * changed the settings from the default setting. In other words, storing just the diff
	 * will probably be easier to read and write instead of reading/writing all the settings
	 * even though they are default values.
	 * @param first True if it's the first time for the user to use this record.
	 */
	public void setIsFirst(boolean first) {
		this.first = first;
	}
	
	/**
	 * Returns whether it's the first time to use this software (= has a record or not)
	 * @return True if it is the first time, false (default) if not
	 */
	public boolean isFirst() {
		return first;
	}
}
