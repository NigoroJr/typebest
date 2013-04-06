/**
 * This class stores information about the user's record
 * @author Naoki Mizuno
 *
 */
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
