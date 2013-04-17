import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * This is the panel that the the user types in whatever is shown.
 * It shows the untyped words in blue and the correctly typed words in red.
 * 
 * @author Naoki Mizuno
 *
 */
// TODO: UPDATE JAVADOC!!!!!
// TODO: Modify tokenize() method so that it's easier to understand (try to think of a way to get rid of the tmp var)
// TODO: Put the letters into a JLabel that represents the word so that the words will no be separated when coming to the end of the line.
//|words being sep|
//|arated among li|
// Create new branch for this.

public class MainTypePanel extends JPanel {

    public static final String FILENAME = "dic.txt";
    
    // I didn't make these final so that the user can change it later
    private Color toBeTyped = Color.BLUE;
    private Color alreadyTyped = Color.RED;
    private Color backGroundColor = Color.GRAY;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 30);
    
    private int totalNumOfLetters = 0;
    private int correctKeyStrokes = 0;
    private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<JPanel> wordPanels = new ArrayList<JPanel>();
    private static int cnt = 0;
    private static int words_cnt = 0;
    private static int miss = 0;
    private boolean finished = false;
    private boolean restartFlag = false;
    
    private long startTime = -1;
    private long endTime = -1;

    public MainTypePanel() {
        super();

        setSize(800, 400);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setBackground(backGroundColor);
        // Read from dictionary file
        readDic();

        // Tokenize the words read from the file
        tokenize();

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
        	endTime = System.nanoTime();
        	
        	long duration = endTime - startTime;
            JOptionPane.showMessageDialog(null,
                    "Time: " + (double)duration / 1000000000 + "\nMiss: " + miss,
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
		endTime = -1;
		
		words.clear();
		wordPanels.clear();
		readDic();
		tokenize();
		
		// Note: Only revalidate() worked here
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
            oneWordPanel.setBackground(backGroundColor);
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
}
