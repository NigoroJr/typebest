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
//TODO: Put the letters into a JLabel that represents the word so that the words will no be separated when coming to the end of the line.
//|words being sep|
//|arated among li|

public class MainTypePanel extends JPanel implements KeyListener {

    public static final String FILENAME = "dic.txt";
    private int totalNumOfLetters = 0;
    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private static int cnt = 0;
    private static int miss = 0;
    private boolean finished = false;

    public MainTypePanel() {
        super();

        setSize(800, 400);
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);

//        JPanel p = new JPanel();
//        p.setLayout(new FlowLayout());

        // Add words from dictionary
        try {
            Scanner input = new Scanner(new File(FILENAME));
            while (input.hasNext())
                words.add(input.next());
        }
        catch (FileNotFoundException e) {
            // TODO: When no dictionary file is found
            e.printStackTrace();
        }

        // Find out the total number of letters in the dictionary file
        // Add 1 for the white space after the word
        for (int i = 0; i < words.size(); i++)
        	totalNumOfLetters += words.get(i).length() + 1;

        // Separate the words into chunks of letters
        for (int w = 0; w < words.size(); w++) {
            String word = words.get(w);
            for (int i = 0; i < word.length(); i++) {
                JLabel tmp = new JLabel(Character.toString(word.charAt(i)));
                tmp.setFont(new Font("Arial", Font.BOLD, 40));
                tmp.setForeground(Color.BLUE);
                labels.add(tmp);
//                p.add(tmp);
                this.add(tmp);
            }
            JLabel tmp = new JLabel(" ");
            tmp.setFont(new Font("Arial", Font.BOLD, 40));
            labels.add(tmp);
//            p.add(tmp);
            this.add(tmp);
        }

        // To listen for key press
        addKeyListener(this);

//        getContentPane().add(p);
//        this.add(p);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    	// Don't go any further if it' done
    	if (finished)
    		return;

        char pressed = e.getKeyChar();

        String s;
        if ((s = labels.get(cnt).getText()) != null) {
            if (pressed == s.charAt(0))
                labels.get(cnt).setForeground(Color.RED);
            else {
            	miss++;
                return;
            }
        }

        // Remember where the last index was
        cnt++;

        // Finish if the user finishes typing all the words
        // (subtracts 1 because the last word is always a white space)
        if (cnt == totalNumOfLetters - 1)
            JOptionPane.showMessageDialog(null,
                    "Congratulations, you successfully typed all the words with " + miss + " miss types!",
                    "Mission Accomplished", JOptionPane.INFORMATION_MESSAGE);
        repaint();
    }
}
