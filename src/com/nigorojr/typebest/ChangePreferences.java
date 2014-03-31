package com.nigorojr.typebest;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is used to update preferences.
 * 
 * @author Naoki Mizuno
 * 
 */

public class ChangePreferences {
    private Preferences pref;
    public static final String TO_BE_TYPED = "To be typed";
    public static final String ALREADY_TYPED = "Already typed";
    public static final String MISTYPE = "Mistype";
    public static final String BACKGROUND = "Background";
    private String[] colorTypes = {
            TO_BE_TYPED,
            ALREADY_TYPED,
            MISTYPE,
            BACKGROUND,
    };

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
        final JDialog dialog = new JDialog();
        final JComboBox comboBox = new JComboBox(colorTypes);
        final JButton ok = new JButton("OK");
        final JButton cancel = new JButton("Cancel");
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttons.add(ok);
        buttons.add(cancel);

        class ComboBoxListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();

                if (e.getSource() == ok) {
                    if (selected.equals(TO_BE_TYPED))
                        pref.setToBeTyped(ColorSelector.chooseColor(
                                pref.getToBeTyped()));
                    else if (selected.equals(ALREADY_TYPED))
                        pref.setAlreadyTyped(ColorSelector.chooseColor(
                                pref.getAlreadyTyped()));
                    else if (selected.equals(MISTYPE))
                        pref.setMissTypeColor(ColorSelector.chooseColor(
                                pref.getMissTypeColor()));
                    else if (selected.equals(BACKGROUND))
                        pref.setBackgroundColor(ColorSelector.chooseColor(
                                pref.getBackgroundColor()));

                    pref.update();
                }
                else if (e.getSource() == cancel) {
                    dialog.setVisible(false);
                }
            }
        }

        ok.addActionListener(new ComboBoxListener());
        cancel.addActionListener(new ComboBoxListener());

        comboBox.addActionListener(new ComboBoxListener());
        comboBox.setPreferredSize(new Dimension(200, 20));

        JLabel label = new JLabel("Select color to change");
        label.setPreferredSize(new Dimension(200, 50));
        label.setFont(new Font("Arial", Font.PLAIN, 10));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(label);
        panel.add(comboBox);
        panel.add(buttons);

        dialog.add(panel);

        dialog.setSize(new Dimension(200, 100));
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
