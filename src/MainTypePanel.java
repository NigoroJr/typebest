import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * This is the panel that the the user types in whatever is shown.
 * It shows the untyped words in blue and the correctly typed words in red.
 * 
 * @author Naoki Mizuno
 *
 */
// TODO: Get rid of the duplicate instance variables
// TODO: Create menu
// TODO: Settings for shuffle, color
// TODO: Change the changeUser method so that it shows the existing users (like in the keyboard layout)

public class MainTypePanel extends JPanel {

	public static final String DIC_FILENAME = "dic.txt";
    
	// public static final String LAST_USER = "lastUser.dat";
	public static final File lastUserFile = new File("lastUser.dat");
	private User user;
    
    private Color toBeTyped;
    private Color alreadyTyped;
    private Color backgroundColor;
    private Color missTypeColor;
    private Font defaultFont;
	private boolean shuffled;
    // The number of digits to show after decimal point
	private int speedFractionDigit;
	private int timeFractionDigit;
	private String keyboardLayout;
    
    private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<JPanel> wordPanels = new ArrayList<JPanel>();
    private int totalNumOfLetters = 0;
    private int correctKeyStrokes = 0;
    private int cnt = 0;
    private int words_cnt = 0;
    private int miss = 0;
    private boolean finished = false;
    private boolean restartFlag = false;
    
    private long startTime = -1;

    // Change the color of the each letter
	private boolean fun = false;


    public MainTypePanel() {
        super();
        
        setSize(800, 400);
        setLayout(new FlowLayout(FlowLayout.LEADING));
    }

    public void processPressedKey(char pressed) {
    	// Don't go any further if it's done
    	if (finished)
    		return;
    	
		// Restart when ESCAPE key is pressed twice-in-a-row
		if (pressed == KeyEvent.VK_ESCAPE) {
			if (restartFlag) {
				restartFlag = false;
				restart();
			}
			else
				restartFlag = true;
			return;
		}
		else
			restartFlag = false;

		
    	JPanel p = wordPanels.get(words_cnt);
    	JLabel l = (JLabel)(p.getComponent(cnt));
        String s;
        if ((s = l.getText()) != null) {
            // Start timer
            if (startTime == -1)
            	startTime = System.nanoTime();
            
        	// Don't hilight spaces
        	if (s.charAt(0) == '_' && pressed == ' ') {
        		l.setForeground(backgroundColor);
        		correctKeyStrokes++;
        	}
        	else if (pressed == s.charAt(0)) {
                l.setForeground(alreadyTyped);
                correctKeyStrokes++;
            }
            else {
            	miss++;
            	l.setForeground(missTypeColor);
            	// This is supposed to emit a beep sound
            	// Toolkit.getDefaultToolkit().beep();
                return;
            }
        }
        
        cnt++;
        // If it's the end of the word
    	if (pressed == ' ') {
    		words_cnt++;
    		cnt = 0;
    	}

        // Finish if the user finishes typing all the words
        // (subtracts 1 because the last word is always a white space)
        if (correctKeyStrokes == totalNumOfLetters  - 1) {
        	// Record the time it took
        	long endTime = System.nanoTime();
        	
        	DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance();
        	df.setMaximumFractionDigits(timeFractionDigit);
        	double duration = (double)(endTime - startTime) / 1000000000;
        	String message = "Time: " + df.format(duration) + " sec\n";
        	
        	message += "Miss: " + miss + "\n";
        	
        	df.setMaximumFractionDigits(speedFractionDigit);
        	message += "Speed: " + df.format(totalNumOfLetters / duration) + " keys/sec\n";
            		
            JOptionPane.showMessageDialog(null, message,
                     "Result", JOptionPane.INFORMATION_MESSAGE);
        }
        repaint();
    }

    /**
	 * Loads the data where the user exited last time from a
	 * file named lastUser.dat and put that to the window.
	 * lastUser.dat contains which user last used, and another file with
	 * the user's name as a file name will be loaded.
	 * For example, if the name John was in lastUser.dat, then
	 * John_settings.dat and John_records.dat will be loaded (if they exist).
	 */
	public void loadLastUser() {
		// Read from the file if it exists
		if (lastUserFile.exists()) {
			try {
				Scanner read = new Scanner(lastUserFile);
				changeUser(read.nextLine());
				read.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			// Prompt the user for a user name using changeUser method, which then passes the input to loadLastUser(String)
			changeUser();
		}
		
		// Do the things that needs to be done after loading the previous user
		afterLoadingUser();
	}
	
	/**
	 * These are the things that needs to be done after the previous user's data has been retrieved.
	 * Importing the settings and the records, reading from the dictionary  and tokenizing
	 * to prepare for a new round are some of the things.
	 */
	private void afterLoadingUser() {
		// The settings for the user is read/created AND read when an User instance is created.
		// Thus, we don't have to worry about the settings not being created.
		importSettings();
		
		// Set the panel's background to whatever the user specified
        setBackground(backgroundColor);
        // Read from dictionary file
        readDic();
        // Tokenize the words read from the file
        tokenize();
	}

	/**
	 * Changes the values in this class according to the user's customized (and/or the default
	 * value used in the data field in Settings class) settings.
	 * @param s A Settings instance that contains the user's settings.
	 */
	public void importSettings() {
		Settings s = user.getSettings();
		
		toBeTyped = s.getToBeTyped();
		alreadyTyped = s.getAlreadyTyped();
		backgroundColor = s.getBackgroundColor();
		missTypeColor = s.getMissTypeColor();
		defaultFont = s.getDefaultFont();
		speedFractionDigit = s.getSpeedFractionDigit();
		timeFractionDigit = s.getTimeFractionDigit();
		shuffled = s.isShuffled();
		keyboardLayout = s.getKeyboardLayout();
	}
	
	/**
	 * Reads in words from a dictionary file and store them into an ArrayList.
	 * The total number of words is also counted so that it can be used when determining
	 * when the user successfully finished typing by comparing it with the number of "correctKeyStrokes".
	 * TODO: Add shuffle and make the words appear randomly
	 */
	public void readDic() {
	    // Add words from dictionary
	    try {
	        Scanner input = new Scanner(new File(DIC_FILENAME));
	        while (input.hasNext())
	            words.add(input.next());
	        input.close();
	    }
	    catch (FileNotFoundException e) {
	    	JOptionPane.showMessageDialog(null, "No dictionary file was found",
	    			"No Dictionary file", JOptionPane.OK_OPTION);
	    }
	    
	    // Shuffle the order of appearance of words when it is set to do so.
	    if (shuffled)
		    Collections.shuffle(words);
	
	    // Find out the total number of letters in the dictionary file
	    // Add 1 for the white space after the word
	    for (int i = 0; i < words.size(); i++)
	    	totalNumOfLetters += words.get(i).length() + 1;
	}

	/**
	 * Tokenize the words into chunks of letters that will then be set to the color "toBeTyped".
	 * Words will first be added to a JPanel so that the word will not be separated when coming to a new line.
	 * This method will update the panel to the newest state after adding everything to the panel.
	 */
	public void tokenize() {
		// First, clear all the words that are currently on the panel
		removeAll();
	    // Separate the words into chunks of letters
	    for (int w = 0; w < words.size(); w++) {
	        String word = words.get(w);
	        JPanel oneWordPanel = new JPanel();
	        oneWordPanel.setBackground(backgroundColor);
	        for (int i = 0; i < word.length(); i++) {
	            oneWordPanel.add(new JLabel(Character.toString(word.charAt(i))));
	        }
	        oneWordPanel.add(new JLabel("_"));
	        
	        wordPanels.add(oneWordPanel);
	    }
	    
	    // Add all the elements in the ArrayList to "this" after setting font and color
	    for (int i = 0; i < wordPanels.size(); i++) {
	    	JPanel p = wordPanels.get(i);
	    	for (Component c : p.getComponents()) {
	        	JLabel l = (JLabel)c;
	        	// Hide '_' by making it the same as the background color
	        	if (l.getText().charAt(0) == '_')
	        		l.setForeground(backgroundColor);
	        	// Randomize the color of the letters
	        	else if (fun)
		        	l.setForeground(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));
	        	else
		        	l.setForeground(toBeTyped);
	        	
	        	l.setFont(defaultFont);
	    	}
	    	this.add(p);
	    }
	    
	    repaint();
	    revalidate();
	}

	/**
	 * Clears the words and prepares for a new round.
	 * It will shuffle the words in the dictionary file (of course it depends on the "shuffle" parameter).
	 */
	public void restart() {
		// Clear everything
		this.removeAll();
		
		// Set everything to default value
		finished = false;
		cnt = 0;
		words_cnt = 0;
		miss = 0;
		totalNumOfLetters = 0;
		correctKeyStrokes = 0;
		startTime = -1;
		
		words.clear();
		wordPanels.clear();
		readDic();
		tokenize();
	}
	
	/**
	 * Shows a dialog so that the user can change the default font.
	 * The values will then be passed to the user's Settings instance and finally,
	 * printed to the file so that the user doesn't have to change every time.
	 */
	public void changeFont() {
		user.getSettings().changeFont();
		importSettings();
		// It's questionable whether to re-shuffle the words or not
		// restart();
		tokenize();
    }
	
	/**
	 * Changes the current user to the given user name. Also updates the lastUserFile, which contains the name
	 * of the last user (creates one when it's not found).
	 * @param userName The new user name that will be switched to.
	 */
	public void changeUser(String userName) {
		user = new User(userName);
		
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

	/**
	 * Shows an input dialog and changes the current user to the input. 
	 */
	public void changeUser() {
		String newUser = "";
		newUser = JOptionPane.showInputDialog("Current user is: " + user.getUserName() + "\nEnter new user:");
		// Change the user name if it's a valid value
		if (newUser != null && !newUser.trim().equals("")) {
			changeUser(newUser);
			afterLoadingUser();
			restart();
		}
	}
	
	/**
	 * Changes the keyboard layout to a new layout. The records are stored separately among each layout.
	 */
	public void changeKeyboardLayout() {
		// TODO: Use JComboBox and input field so that the use can choose from the existing layouts or create a new one
		// TODO: Create the method in the settings class
		JOptionPane.showMessageDialog(null, "Currently under development...Sorry!");
		restart();
	}
}