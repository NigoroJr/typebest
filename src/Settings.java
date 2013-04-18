import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;


/**
 * This is a class used to save the settings for a certain user (for example,
 * the font, the last mode s/he used, the last date the program has been used, etc.)
 * 
 * @author Naoki Mizuno
 *
 */

public class Settings {
	private String userName;
	
    private Color toBeTyped = Color.BLUE;
    private Color alreadyTyped = Color.RED;
    private Color backgroundColor = Color.GRAY;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 30);
    // The number of digits to show after decimal point
	private int speedFractionDigit = 8;
	private int timeFractionDigit = 9;
	private boolean shuffled = true;
	
	/**
	 * Creates a new Settings instance with the default settings value and
	 * an user name of "Anonymous".
	 */
	public Settings() {
		this("Anonymous");
	}
	
	/**
	 * Creates a new Settings instance with the default value and the given user name.
	 * @param userName The user's name, which will be used in the filename.
	 */
	public Settings(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Creates a new Settings instance with the default value and read the data from the
	 * file given as a Scanner object. The file contains the settings that were changed
	 * from the default value.
	 * @param userName The user's name, which will be used in the filename.
	 * @param read A Scanner object to read from the file containing settings.
	 */
	public Settings(String userName, Scanner read) {
		// TODO: Write loops and setters that read settings
	}
	
	/**
	 * Returns the color of the letters that are not yet typed.
	 * @return The color of the letters that haven't been typed yet.
	 */
	public Color getToBeTyped() {
		return toBeTyped;
	}
	
	/**
	 * Returns the color of the letters that have already been typed correctly.
	 * @return The color of the letters that have already been typed correctly.
	 */
	public Color getAlreadyTyped() {
		return alreadyTyped;
	}
	
	/**
	 * Returns the background color for the typing panel.
	 * @return The background color of the typing panel.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Returns the default font used in the typing panel.
	 * @return The default font used in the typing panel.
	 */
	public Font getDefaultFont() {
		return defaultFont;
	}
	
	/**
	 * Returns the value of the speedFractionDigit,
	 * which is the number of digits after decimal points when showing the typing speed.
	 * @return The number of digits shown after the decimal points when showing the typing speed.
	 */
	public int getSpeedFractionDigit() {
		return speedFractionDigit;
	}
	
	/**
	 * Returns the value of the timeFractionDigit,
	 * which is the number of digits after decimal points when showing the time.
	 * @return The number of digits shown after the decimal points when showing the time.
	 */
	public int getTimeFractionDigit() {
		return timeFractionDigit;
	}
	
	/**
	 * Returns whether the words in the dictionary file should be shuffled or not.
	 * When this is true, the words will be shuffled using the Collections.shuffle() method.
	 */
	public boolean isShuffled() {
		return shuffled;
	}
	
	/**
	 * Returns a String representation of the user's settings. It can be used for printing the information
	 * to a file, as well as debugging.
	 */
	@Override
	public String toString() {
		String ret = "";
	    ret += String.format("toBeTyped\n%d\n", toBeTyped.getRGB());
	    ret += String.format("alreadyTyped\n%d\n", alreadyTyped.getRGB());
	    ret += String.format("backgroundColor\n%d\n", backgroundColor.getRGB());
	    ret += String.format("defaultFont\n%s %s %d\n", defaultFont.getFamily(), defaultFont.getStyle(), defaultFont.getSize());
	    ret += String.format("speedFractionDigit\n%d\n", speedFractionDigit);
	    ret += String.format("timeFractionDigit\n%d\n", timeFractionDigit);
	    ret += String.format("shuffled\n%s\n", shuffled);
		return ret;
	}
}
