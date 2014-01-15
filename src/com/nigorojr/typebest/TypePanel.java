package com.nigorojr.typebest;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * This is the panel that the the user types in whatever is shown. It shows the
 * untyped words in blue and the correctly typed words in red.
 * 
 * @author Naoki Mizuno
 * 
 */
// TODO: Change the changeUser method so that it shows the existing users

public class TypePanel extends JPanel {

    private static Preferences pref;

    public static final File lastUserFile = new File("lastUser.txt");

    private ArrayList<Line> lines = new ArrayList<Line>();
    private Iterator<Line> lineIterator = lines.iterator();
    private Line currentLine = null;
    private Word currentWord = null;
    private Letter currentLetter = null;

    private int miss = 0;
    private boolean finished = false;

    private String keyboardLayout;
    private JLabel keyboardLayoutLabel;

    public TypePanel() {
        super();

        setSize(800, 400);
        setLayout(new FlowLayout(FlowLayout.LEADING));

        keyboardLayoutLabel = new JLabel("");
    }

    /**
     * Attempts to find the last user who used this program and load his/her
     * preferences. If that attempt fails, this method will create a new set of
     * preferences with the default parameters. The lastUserFile must have the
     * username on the first line and the user's ID on the second line. The
     * username will be used when creating a new set of preferences when the ID
     * is not present.
     */
    public void loadPreferences() {
        if (!lastUserFile.exists()) {
            // Prompt for a username
            String message = "It looks like it's the first time this program has been used.\nEnter your user name:";
            String username = JOptionPane.showInputDialog(message);
            try {
                pref = new Preferences(username);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Scanner scanner = new Scanner(lastUserFile);

                if (scanner.hasNext()) {
                    String username = scanner.nextLine();
                    if (scanner.hasNext()) {
                        int user_id = Integer.parseInt(scanner.nextLine());
                        pref = new Preferences(user_id, username);
                    }
                    // When there's only 1 line
                    else
                        pref = new Preferences(username);
                }
                // When the lastUserFile is an invalid empty file
                else
                    pref = new Preferences();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds lines to the ArrayList
     */
    public void loadLinesAndAddToPanel() {
        // TODO: get modes and number of letters
        Iterator<String> words = WordSelector.getWords(WordSelector.NORMAL)
                .iterator();
        totalNumOfLetters = 0;
        int panelWidth = this.getWidth();
        Line line = new Line();
        while (words.hasNext()) {
            String word = words.next();
            totalNumOfLetters += word.length();
            // If the line was full and that word couldn't be added
            if (!line.addWordIfWithin(word, panelWidth)) {
                // Add *current* line to the ArrayList
                lines.add(line);
                // Create a new line and add that word to it
                line = new Line();
                line.addWordIfWithin(word, panelWidth);
                // TODO: not a good solution!
            }
        }

        // Add to this panel
        Iterator<Line> lineIterator = lines.iterator();
        while (lineIterator.hasNext())
            add(lineIterator.next());
    }

    public void processPressedKey(char pressed) {
        // Don't go any further if it's done
        if (finished)
            return;

        JPanel p = wordPanels.get(words_cnt);
        JLabel l = (JLabel) (p.getComponent(cnt));
        String s;
        if ((s = l.getText()) != null) {
            // Start timer
            if (startTime == -1) {
                // Start timer on the MainWindow
                timer.start();
                startTime = System.nanoTime();
            }

        Letter letter = getCurrentLetter();
        if (letter == null)
            finished();
        else {
            if (letter.isCorrectKeyStroke(pressed))
                letter.setForeground(pref.getAlreadyTyped());
            else
                letter.setForeground(pref.getMissTypeColor());
        }

        repaint();
    }

    public Letter getCurrentLetter() {
        if (currentLine == null && lineIterator.hasNext())
            currentLine = lineIterator.next();
        if (currentWord == null && currentLine.hasNext())
            currentWord = currentLine.nextWord();
        if (currentWord == null && currentWord.hasNext())
            currentLetter = currentWord.nextLetter();

        if (!currentWord.hasNext()) {
            if (!currentLine.hasNext()) {
                return null;
            }
            else {
                currentLine = lineIterator.next();
                currentWord = currentLine.nextWord();
            }
        }
        currentLetter = currentWord.nextLetter();

        return currentLetter;
    }

            // Stop the timer in the main window
            timer.stop();

            finished = true;

            Record record = new Record(user_id, username, keyboardLayout, time,
                    miss);
            showFinishMessage(record);

            // Then, show the list of results and where this round falls into
            user.getRecords().showListOfRecords();
        }
        repaint();
    }

    public void showFinishMessage(Record record) {
        // Show the result
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(pref.getTimeFractionDigit());
        String message = String.format("Time: %f\nMiss: %d\n",
                df.format(record.time), record.miss);

        df.setMaximumFractionDigits(pref.getSpeedFractionDigit());
        message += String.format("Speed: %f keys/sec",
                df.format(totalNumOfLetters / record.time));

        JOptionPane.showMessageDialog(null, message, "Result",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Clears the words and prepares for a new round. It will shuffle the words
     * in the dictionary file (of course it depends on the "shuffle" parameter).
     */
    public void restart() {
        // Clear everything
        this.removeAll();

        // Reset the timer
        timer.stop();
        // Sets text of the JLabel (timeElapsed) to "0.0"
        timeElapsed.setText("0.0");

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
     * Shows a dialog so that the user can change the default font. The values
     * will then be passed to the user's Settings instance and finally, printed
     * to the file so that the user doesn't have to change every time.
     */
    public void changeFont() {
        // TODO
    }

    /**
     * Changes the current user to the given username. Also updates the
     * lastUserFile, which contains the name of the last user and the user
     * ID (creates one when it's not found).
     * 
     * @param username
     *            The new username that will be switched to.
     */
    public void changeUser(String username) {
        // TODO: add setUsername(username) to Preferences class

        try {
            PrintWriter pw = new PrintWriter(lastUserFile);
            pw.println(username);
            pw.println(pref.getID());
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
        String message = String.format(
                "Current username is '%s'\nEnter new username",
                pref.getUsername());
        String username = JOptionPane.showInputDialog(message);
        if (username != null && !username.trim().equals("")) {
            changeUser(username);
            restart();
        }
    }

    /**
     * Changes the keyboard layout to a new layout. The records are stored
     * separately among each layout. It makes a copy of the previous layout and
     * compare it with the new layout. After successfully changing the keyboard
     * layout, it re-reads the records and starts a new round.
     */
    public void changeKeyboardLayout() {
        String previous = pref.getKeyboardLayout();

        // TODO
        String current = pref.getKeyboardLayout();

        if (!current.equals(previous)) {
            keyboardLayoutLabel.setText(current);
            restart();
        }
    }

    /**
     * Shows a dialog that allows user to change the color. A dialog that asks
     * the user which color to change is shown first. Then, according to the
     * selection, another dialog appears that allows the user to change the
     * selected color. Clicking on cancel will make no changes.
     */
    public void changeColor() {
        // TODO: use ChangeColor class
    }

    /**
     * Saves the settings to the user's settings file.
     */
    public void saveSettings() {
        // TODO: update through Preferences class
        // TODO: automatically save settings when altered
    }

    /**
     * Returns the JLabel that shows the current keyboard layout. TODO: Modify
     * this to get information from the database and then create a JLabel and
     * return that.
     */
    public JLabel getKeyboardLayoutLabel() {
        return keyboardLayoutLabel;
    }

    /**
     * Returns the current set of preferences. This is used in the Letter class
     * for setting the foreground/background color of the letters. Make sure
     * that pref is not null.
     * 
     * @return
     */
    public static Preferences getPreferences() {
        return pref;
    }

    public String getKeyboardLayout() {
        return keyboardLayout;
    }
}