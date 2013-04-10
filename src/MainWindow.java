import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


public class MainWindow extends JFrame {
	public static final String LAST_USER = "lastUser.dat";
	private User user;
	
	/**
	 * Creates a new window with a panel that you type in, and a menu bar
	 */
	public MainWindow() {
		super();
		
		SpringLayout springLayout = new SpringLayout();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(springLayout);
		
		
		menuBar();
		
		// Window to type in
		MainTypePanel mtp = new MainTypePanel();
		springLayout.putConstraint(SpringLayout.NORTH, mtp, 15, SpringLayout.NORTH, mainPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, mtp, -40, SpringLayout.SOUTH, mainPanel);
		springLayout.putConstraint(SpringLayout.WEST, mtp, 5, SpringLayout.WEST, mainPanel);
		springLayout.putConstraint(SpringLayout.EAST, mtp, -5, SpringLayout.EAST, mainPanel);
		mainPanel.add(mtp);
		
		getContentPane().add(mainPanel);
		// The size of the main window
		setSize(800, 400);
		
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
				
				String userName = JOptionPane.showInputDialog("What's your user name?");
				user = new User(userName);
				
				// Write the previous user name to the file
				try {
					PrintWriter pw = new PrintWriter(lastUserFile);
					pw.println(userName);
					pw.flush();
					pw.close();
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
	
	/**
	 * Adds a menu bar to the main panel.
	 */
	public void menuBar() {
		// TODO: Add menu bar
		JMenuBar menu = new JMenuBar();
		JMenu open = new JMenu("Open");
		open.add(new JMenuItem("Change user"));
		open.add(new JMenuItem("Change Practice Mode"));
		
		menu.add(open);
		setJMenuBar(menu);
		
	}
	
	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mw.setLayout(new BorderLayout());
		mw.setLocationRelativeTo(null);
		mw.setVisible(true);
	}
	
}
