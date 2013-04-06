import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


public class MainWindow extends JFrame {
	public static final String LAST_USER = "lastUser.dat";
	private User user;
	
	public MainWindow() {
		super();
		
		this.setPreferredSize(new Dimension(800, 400));
		this.setBackground(Color.CYAN);
		this.setLayout(new FlowLayout());
		
		SpringLayout springLayout = new SpringLayout();
		
		JPanel p = new JPanel();
		p.setLayout(springLayout);
		
		// TODO: Add menu bar
		// For now, add button
		Button menu = new Button("Menu");
		springLayout.putConstraint(SpringLayout.NORTH, menu, 100, SpringLayout.NORTH, p);
		
		// Window to type in
		MainTypePanel mtp = new MainTypePanel();
		springLayout.putConstraint(SpringLayout.NORTH, mtp, 150, SpringLayout.NORTH, p);
		
		this.add(p);
		
		// Load the previous user's data
		loadPreviousSession();
	}
	
	/**
	 * Loads the data where the user exited last time from a
	 * file named lastUser.dat and put that to the window.
	 * lastUser.dat contains which user last used, and another file with
	 * the user's name as a file name will be loaded.
	 * For example, if the name John was in lastUser.dat, then
	 * John_settings.dat and John_records.dat will be loaded (if they exist).
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
		MainWindow mw = new MainWindow();
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mw.setLayout(new BorderLayout());
		// The main window
		mw.add(new MainWindow());
		
		mw.pack();
		mw.setLocationRelativeTo(null);
		mw.setVisible(true);
	}
}
