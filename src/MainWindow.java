import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainWindow extends JPanel {
	public static final String LAST_USER = "lastUser.dat";
	private User user;
	
	public MainWindow() {
		super();
		
		this.setPreferredSize(new Dimension(800, 400));
		this.setBackground(Color.BLACK);
		// So that we can see that the MainWindow panel is working
		this.setLayout(new BorderLayout(20, 20));
		
		// TODO: Add menu bar
		// For now, add button
		this.add(new JButton("Menu bar"), BorderLayout.NORTH);
		
		// Window to type in
		this.add(new MainTypePanel(), BorderLayout.CENTER);
		
		// Load the previous user's data
		loadPreviousSession();
	}
	
	/**
	 * Loads the data where the user exited last time from a
	 * file named lastUser.dat and put that to the window.
	 * lastUser.dat contains which user last used, and another file with
	 * the user's name as a file name will be loaded.
	 * TODO: Better JavaDoc here
	 */
	public void loadPreviousSession() {
		File lastUserFile = new File(LAST_USER);
		if (!(lastUserFile.exists())) {
				System.out.println("Last user file does not exist!\nCreating a new one.");
				// TODO: Add some operation that prompts the user for a name (JOptionPane, maybe)
				// Then, create a new User instance with the default settings, etc.
		}
		else {
			Scanner read = null;
			try {
				read = new Scanner(lastUserFile);
				String lastUser = read.nextLine();
				// Create a current user from the previous session
				user = new User(lastUser);
				
				read.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame jf = new JFrame("TypeBest");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new BorderLayout());
		// The main window
		jf.add(new MainWindow());
		
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
