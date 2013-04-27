import java.util.Calendar;

/**
 * This class represents the result of 1 round.
 * It contains information such as the time it took, the number of miss types,
 * and the date that the result was recorded.
 * This class should be used with the Records method.
 * @author Naoki Mizuno
 *
 */

public class Result {
	private double time;
	private int miss;
	private String userName;
	private String keyboardLayout;
	private Calendar date;
	
	/**
	 * Creates a new Result instance with the given parameters.
	 * ObjectOutputStream does not work correctly when putting the Calendar object at the last (= writing
	 * an Object at the last) so be careful when adding something to the class.
	 * @param time The time it took for the user to finish typing all the letters correctly.
	 * @param miss The total miss types the user made.
	 * @param userName The user name of the user.
	 * @param keyboardLayout The keyboard layout the user used in this round.
	 * @param date The date that the record was created.
	 */
	public Result(double time, int miss, String userName, String keyboardLayout, Calendar date) {
		this.time = time;
		this.miss = miss;
		this.userName = userName;
		this.keyboardLayout = keyboardLayout;
		this.date = date;
	}
	
	/**
	 * Returns how much time it took for the user to type all the words.
	 * @return The time it took for the user to type all the words.
	 */
	public double getTime() {
		return time;
	}
	
	/**
	 * Sets the value of time.
	 * @param time The time it took for the user to type all the words.
	 */
	public void setTime(double time) {
		this.time = time;
	}
	
	/**
	 * Returns the number of miss types the user made during that round.
	 * @return The number of miss types the user made.
	 */
	public int getMiss() {
		return miss;
	}
	
	/**
	 * @param miss The miss types the user made during that round.
	 */
	public void setMiss(int miss) {
		this.miss = miss;
	}
	
	/**
	 * Returns the user name of the user.
	 * @return The user name of the user.
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Sets the user name to the given value.
	 * @param userName The user name of the user who played that round.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Returns the date when the record was added.
	 * @return The date the record was added.
	 */
	public Calendar getDate() {
		return date;
	}
	
	/**
	 * Sets the date of the record added to the given value.
	 * @param date The date that record was added.
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/**
	 * Returns the keyboard layout that was used in this round.
	 * @return The keyboard layout that was used in this round.
	 */
	public String getKeyboardLayout() {
		return keyboardLayout;
	}
	
	/**
	 * Sets the keyboard layout to the given value.
	 * @param keyboardLayout The keyboard layout that was used in this round.
	 */
	public void setKeyboardLayout(String keyboardLayout) {
		this.keyboardLayout = keyboardLayout;
	}
}
