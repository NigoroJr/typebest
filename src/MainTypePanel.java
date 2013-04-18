import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
// TODO: UPDATE JAVADOC!!!!!
// TODO: Create menu

public class MainTypePanel extends JPanel {

    public static final String FILENAME = "dic.txt";
    
	public static final String LAST_USER = "lastUser.dat";
	private User user;
    
    // I didn't make these final so that the user can change it later
    private Color toBeTyped;
    private Color alreadyTyped;
    private Color backgroundColor;
    private Font defaultFont;
    // The number of digits to show after decimal point
	private int speedFractionDigit;
	private int timeFractionDigit;
    
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
			if (restartFlag)
				restart();
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
            
            if (pressed == s.charAt(0)) {
                l.setForeground(alreadyTyped);
                correctKeyStrokes++;
            }
            else {
            	miss++;
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
        	df.setMinimumFractionDigits(timeFractionDigit);
        	double duration = (double)(endTime - startTime) / 1000000000;
        	String message = "Time: " + df.format(duration) + " sec\n";
        	
        	message += "Miss: " + miss + "\n";
        	
        	df.setMaximumFractionDigits(speedFractionDigit);
        	df.setMinimumFractionDigits(speedFractionDigit);
        	message += "Speed: " + df.format(totalNumOfLetters / duration) + " keys/sec\n";
//            message += String.format("Speed: %.8f keys/sec\n",
//            		totalNumOfLetters / duration);
            		
            JOptionPane.showMessageDialog(null, message,
                     "Result", JOptionPane.INFORMATION_MESSAGE);
        }
        repaint();
    }

    /**
     * Clears the words and prepares for a new round.
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
		
		// Note: Only revalidate() works here
		// repaint();
		revalidate();
	}
	
	/**
	 * Reads in words from a dictionary file and store them into an ArrayList.
	 * TODO: Add shuffle and make the words appear randomly
	 */
	public void readDic() {
        // Add words from dictionary
        try {
            Scanner input = new Scanner(new File(FILENAME));
            while (input.hasNext())
                words.add(input.next());
        }
        catch (FileNotFoundException e) {
        	JOptionPane.showMessageDialog(null, "No dictionary file was found",
        			"No Dictionary file", JOptionPane.OK_OPTION);
        }

        // Find out the total number of letters in the dictionary file
        // Add 1 for the white space after the word
        for (int i = 0; i < words.size(); i++)
        	totalNumOfLetters += words.get(i).length() + 1;

	}
	
	/**
	 * Tokenize the words into chunks of letters that will then be set to the color "toBeTyped".
	 * Words will first be added to a JPanel so that the word will not be separated when coming to a new line.
	 */
	public void tokenize() {
        // Separate the words into chunks of letters
        for (int w = 0; w < words.size(); w++) {
            String word = words.get(w);
            JPanel oneWordPanel = new JPanel();
            oneWordPanel.setBackground(backgroundColor);
            for (int i = 0; i < word.length(); i++) {
                oneWordPanel.add(new JLabel(Character.toString(word.charAt(i))));
            }
            oneWordPanel.add(new JLabel(" "));
            
            wordPanels.add(oneWordPanel);
        }
        
        // Add all the elements in the ArrayList to "this" after setting font and color
        for (int i = 0; i < wordPanels.size(); i++) {
        	JPanel p = wordPanels.get(i);
        	for (Component c : p.getComponents()) {
	        	JLabel l = (JLabel)c;
	        	l.setFont(defaultFont);
	        	l.setForeground(toBeTyped);
	        	// Checking whether the letters are separated
	        	// l.setForeground(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));
        	}
        	this.add(p);
        }
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
		
		// Do the things that needs to be done after loading the previous user
		afterLoadingPreviousSession();
	}
	
	/**
	 * These are the things that needs to be done after the previous user's data has been retrieved.
	 * Importing the settings and the records, reading from the dictionary  and tokenizing
	 * to prepare for a new round are some of the things.
	 */
	private void afterLoadingPreviousSession() {
		// The settings for the user is read/created when an User instance is created.
		// Thus, we can get the settings and change the values in this class accordingly
		importSettings(user.getSettings());
		// TODO: Create importRecords() method
		
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
	public void importSettings(Settings s) {
		toBeTyped = s.getToBeTyped();
		alreadyTyped = s.getAlreadyTyped();
		backgroundColor = s.getBackgroundColor();
		defaultFont = s.getDefaultFont();
		speedFractionDigit = s.getSpeedFractionDigit();
		timeFractionDigit = s.getTimeFractionDigit();
	}
}
