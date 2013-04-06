
/**
 * This is a class used to save the settings for a certain user (for example,
 * the font, the last mode s/he used, the last date the program has been used, etc.)
 * 
 * @author Naoki Mizuno
 *
 */

public class Settings {
	private String userName;
	
	public Settings() {
		this("Anonymous");
	}
	
	public Settings(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "have to write something here once I know some important things about this class";
	}
}
