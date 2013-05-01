import java.util.Calendar;
import java.util.TimeZone;

/**
 * This class represents the result of 1 round.
 * It contains information such as the time it took, the number of miss types,
 * and the date that the result was recorded.
 * This class should be used with the Records method.
 * @author Naoki Mizuno
 *
 */

public class Result implements Comparable {
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
	public Result(double time, int miss, String userName, String keyboardLayout, long date) {
		this.time = time;
		this.miss = miss;
		this.userName = userName;
		this.keyboardLayout = keyboardLayout;
		this.date = Calendar.getInstance(TimeZone.getDefault());
		this.date.setTimeInMillis(date);
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

	/**
	 * Compares this result with the given object and see if the given Result, which should be non-null, is
	 * a better result. If the given Result is better (comes earlier when sorting), it returns -1.
	 * It first checks the time, then if the time is the same, the number of miss types,
	 * and if, although very unlikely, those are also the same, it compares the date from recent to oldest
	 * and then, although very VERY unlikely, if those are also the same, it will return 0 indicating that
	 * the 2 results are completely identical.
	 * @param that The Result that will be compared to this result.
	 * @return -1 if the given Result is in front of this result, 1 if it's the other way, and 0 if same.
	 */
	@Override
	public int compareTo(Object o) {
		final int BETTER = -1;
		final int EQUAL = 0;
		final int WORSE = 1;
		
		if (!(o instanceof Result))
			throw new IllegalArgumentException();
		
		Result that = (Result)o;
		
		// When given time is better than this
		if (that.time < this.time)
			return BETTER;
		if (that.time > this.time)
			return WORSE;
		
		// When given miss is less than this
		if (that.miss < this.miss)
			return BETTER;
		if (that.miss > this.miss)
			return WORSE;
			
		// If the 2 above are the same, compare the dates
		int comparison = this.date.compareTo(that.date);
		if (comparison < 0)
			return BETTER;
		if (comparison > 0)
			return WORSE;
		
		// Finally, if they are identical, return EQUAL
		return EQUAL;
	}
}
