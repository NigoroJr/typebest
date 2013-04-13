import java.awt.Color;
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

public class MainTypePanel extends JPanel {

    public static final String FILENAME = "dic.txt";
    
    private Color toBeTyped = Color.BLUE;
    private Color alreadyTyped = Color.RED;
    // I didn't make this final so that the user can change it later
    private Font defaultFont = new Font("MS Gothic", Font.BOLD, 30);
    
    private int totalNumOfLetters = 0;
    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private static int cnt = 0;
    private static int miss = 0;
    private boolean finished = false;
    
    private long startTime = -1;
    private long endTime = -1;

    public MainTypePanel() {
        super();

        setSize(800, 400);
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);
        
        // Read from dictionary file
        readDic();

        // Tokenize the words read from the file
        tokenize();

    }

    public void processPressedKey(char pressed) {
    	// Don't go any further if it's done
    	if (finished)
    		return;

        String s;
        if ((s = labels.get(cnt).getText()) != null) {
            // Start timer
            if (startTime == -1)
            	startTime = System.nanoTime();
            
            if (pressed == s.charAt(0)) {
                labels.get(cnt).setForeground(alreadyTyped);
            }
            else {
            	miss++;
                return;
            }
        }

        // Remember where the last index was
        cnt++;

        // Finish if the user finishes typing all the words
        // (subtracts 1 because the last word is always a white space)
        if (cnt == totalNumOfLetters - 1) {
        	// Record the time it took
        	endTime = System.nanoTime();
        	
        	long duration = endTime - startTime;
            JOptionPane.showMessageDialog(null,
                    "Time: " + (double)duration / 1000000000 + "\nMiss: " + miss,
                    "Mission Accomplished", JOptionPane.INFORMATION_MESSAGE);
        }
        repaint();
    }

    /**
     * Clears the words and prepares for a new round.
     */
	public void resetPanel() {
		// Clear everything
		this.removeAll();
		
		// Set everything to default value
		finished = false;
		cnt = 0;
		miss = 0;
		totalNumOfLetters = 0;
		startTime = -1;
		endTime = -1;
		
		words.clear();
		labels.clear();
//		System.out.println(String.format("w: %d, l: %d", words.size(), labels.size()));
		readDic();
		tokenize();
		
		repaint();
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
	 */
	public void tokenize() {
        // Separate the words into chunks of letters
        for (int w = 0; w < words.size(); w++) {
            String word = words.get(w);
            for (int i = 0; i < word.length(); i++) {
                JLabel tmp = new JLabel(Character.toString(word.charAt(i)));
                tmp.setFont(defaultFont);
                tmp.setForeground(toBeTyped);
                labels.add(tmp);
                this.add(tmp);
            }
            JLabel tmp = new JLabel(" ");
            tmp.setFont(defaultFont);
            labels.add(tmp);
            this.add(tmp);
        }
	}
}
