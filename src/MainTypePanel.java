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
 * This is a panel for the typing section in the main window.
 * It is where the words to type appear and the user knows what to type,
 * whether s/he typed right, and what to type next.
 * @author Naoki Mizuno
 *
 */

public class MainTypePanel extends JPanel implements KeyListener {

    public static final String FILENAME = "dic.txt";
    private String oneString = "";
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

        // Add all the words to one String
        for (int i = 0; i < words.size(); i++)
            oneString += words.get(i) + " ";

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
        // (The last word is a place holder added automatically
        // in order to determine if the user has finished)
        if (cnt == oneString.length() - 1)
            JOptionPane.showMessageDialog(null,
                    "Congratulations, you successfully typed all the words with " + miss + " miss types!",
                    "Mission Accomplished", JOptionPane.INFORMATION_MESSAGE);
        repaint();
    }
}
