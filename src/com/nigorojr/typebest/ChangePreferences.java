package com.nigorojr.typebest;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

/**
 * This class is used to update preferences.
 * 
 * @author naoki
 * 
 */

public class ChangePreferences {
    private Preferences pref;

    public ChangePreferences(Preferences pref) {
        this.pref = pref;
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
        pref.setUsername(username);
        pref.update();
        writeLastUserToFile(pref.getUsername(), pref.getID());
    }

    /**
     * Writes the last user who ran the program to a file. This file has the
     * username in the first line and the user ID in the second line.
     * 
     * @param username
     *            The username.
     * @param id
     *            The user ID which was automatically generated.
     */
    public static void writeLastUserToFile(String username, long id) {
        try {
            PrintWriter pw = new PrintWriter(TypePanel.lastUserFile);
            pw.println(username);
            pw.println(id);
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
            // TODO
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

}
