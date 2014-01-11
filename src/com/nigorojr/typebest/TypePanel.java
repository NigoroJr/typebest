package com.nigorojr.typebest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;
import java.util.TimeZone;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This is the panel that the the user types in whatever is shown. It shows the
 * untyped words in blue and the correctly typed words in red.
 * 
 * @author Naoki Mizuno
 * 
 */
// TODO: Create menu
// TODO: Change the changeUser method so that it shows the existing users (like
// in the keyboard layout)
// TODO: Add JLable to the main panel that shows which layout the user is
// currently using
// TODO: Change how the words are displayed so that it can be more flexible.

public class TypePanel extends JPanel {

    // public static final String LAST_USER = "lastUser.dat";
    public static final File lastUserFile = new File("lastUser.dat");

    private Preferences pref;
    private int miss = 0;
    private boolean finished = false;

    // Not make it null because it will cause a NullPointerException when using
    // SpringLayout
    private JLabel currentKeyboardLayout = new JLabel("");

    public TypePanel() {
        super();

        setSize(800, 400);
        setLayout(new FlowLayout(FlowLayout.LEADING));
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

            // Don't highlight spaces
            if (s.charAt(0) == '_' && pressed == ' ') {
                l.setForeground(user.getSettings().getBackgroundColor());
                correctKeyStrokes++;
            }
            else if (pressed == s.charAt(0)) {
                l.setForeground(user.getSettings().getAlreadyTyped());
                correctKeyStrokes++;
            }
            else {
                miss++;
                l.setForeground(user.getSettings().getMissTypeColor());
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
        if (correctKeyStrokes == totalNumOfLetters - 1) {
            // Record the time it took
            long endTime = System.nanoTime();

            // Stop the timer in the main window
            timer.stop();

            // Set finished to true
            finished = true;

            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance();
            df.setMaximumFractionDigits(user.getSettings()
                    .getTimeFractionDigit());
            double duration = (double) (endTime - startTime) / 1000000000;
            String message = "Time: " + df.format(duration) + " sec\n";

            message += "Miss: " + miss + "\n";

            df.setMaximumFractionDigits(user.getSettings()
                    .getSpeedFractionDigit());
            message += "Speed: " + df.format(totalNumOfLetters / duration)
                    + " keys/sec\n";

            // Create a Result instance for this round and write to a binary
            // file
            Result result = new Result(duration, miss, user.getUserName(), user
                    .getSettings().getKeyboardLayout(), Calendar.getInstance(
                    TimeZone.getDefault()).getTimeInMillis());
            user.getRecords().writeRecords(result);

            // Show the result
            JOptionPane.showMessageDialog(null, message, "Result",
                    JOptionPane.INFORMATION_MESSAGE);

            // Then, show the list of results and where this round falls into
            user.getRecords().showListOfRecords();
        }
        repaint();
    }

    /**
     * Loads the data where the user exited last time from a file named
     * lastUser.dat and put that to the window. lastUser.dat contains which user
     * last used, and another file with the user's name as a file name will be
     * loaded. For example, if the name John was in lastUser.dat, then
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
            // Prompt for a user name
            String newUser = JOptionPane
                    .showInputDialog("It looks like it's the first time this program has been used.\nEnter your user name:");
            changeUser(newUser);
        }

        // Add a JLabel that shows what keyboard layout is currently selected
        // This can only be done after the user has been read,
        // because we don't know what keyboard layout the last user used.
        currentKeyboardLayout.setText(user.getSettings().getKeyboardLayout());
        // Uses the default font's family but set the style and size to
        // specified.
        currentKeyboardLayout.setFont(new Font(user.getSettings()
                .getDefaultFont().getFamily(), Font.PLAIN, 30));

        // Do the things that needs to be done after loading the previous user
        afterLoadingUser();
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
        user.getSettings().changeFont();
        // It's questionable whether to re-shuffle the words or not
        // restart();
        tokenize();
    }

    /**
     * Changes the current user to the given user name. Also updates the
     * lastUserFile, which contains the name of the last user (creates one when
     * it's not found).
     * 
     * @param userName
     *            The new user name that will be switched to.
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
        newUser = JOptionPane.showInputDialog("Current user is: "
                + user.getUserName() + "\nEnter new user:");
        // Change the user name if it's a valid value
        if (newUser != null && !newUser.trim().equals("")) {
            changeUser(newUser);
            afterLoadingUser();
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
        String previous = user.getSettings().getKeyboardLayout();

        user.getSettings().changeKeyboardLayout(
                user.getRecords().getExistingKeyboardLayouts());

        String current = user.getSettings().getKeyboardLayout();

        if (!current.equals(previous)) {
            currentKeyboardLayout.setText(current);
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

        final JDialog dialog = new JDialog();
        dialog.setTitle("Change color of");
        dialog.setLocationRelativeTo(null);
        dialog.setSize(190, 70);
        dialog.setLayout(new GridLayout(0, 1));

        // Ask which color the user wants to change
        final String[] choices = { "Untyped Letters", "Typed Letters",
                "Misstypes", "Background" };
        final JComboBox choose = new JComboBox(choices);

        JPanel buttons = new JPanel() {
            {
                final ActionListener click = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("OK")) {
                            dialog.setVisible(false);
                            String choice = (String) choose.getSelectedItem();
                            // Change the selected
                            Color selected;
                            if (choice.equals(choices[0])
                                    && (selected = ColorSelector.chooseColor(user
                                            .getSettings().getToBeTyped())) != null)
                                user.getSettings().setToBeTyped(selected);
                            else if (choice.equals(choices[1])
                                    && (selected = ColorSelector.chooseColor(user
                                            .getSettings().getAlreadyTyped())) != null)
                                user.getSettings().setAlreadyTyped(selected);
                            else if (choice.equals(choices[2])
                                    && (selected = ColorSelector.chooseColor(user
                                            .getSettings().getMissTypeColor())) != null)
                                user.getSettings().setMissTypeColor(selected);
                            else if (choice.equals(choices[3])
                                    && (selected = ColorSelector
                                            .chooseColor(user.getSettings()
                                                    .getBackgroundColor())) != null)
                                user.getSettings().setBackgroundColor(selected);
                        }
                        else if (e.getActionCommand().equals("Cancel"))
                            dialog.setVisible(false);
                    }
                };
                this.add(new JButton("OK") {
                    {
                        this.addActionListener(click);
                    }
                });
                this.add(new JButton("Cancel") {
                    {
                        this.addActionListener(click);
                    }
                });
            }
        };

        dialog.add(choose);
        dialog.add(buttons);

        dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
        dialog.setVisible(true);

        tokenize();
    }

    /**
     * Saves the settings to the user's settings file.
     */
    public void saveSettings() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Save settings to a file?", null, JOptionPane.YES_OPTION);

        if (choice == 0)
            user.getSettings().writeSettings();
    }

    /**
     * Returns the JLabel that shows the current keyboard layout. TODO: Modify
     * this to get information from the database and then create a JLabel and
     * return that.
     */
    public JLabel getCurrentKeyboardLayout() {
        return currentKeyboardLayout;
    }
}