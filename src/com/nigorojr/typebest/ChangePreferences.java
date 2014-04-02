package com.nigorojr.typebest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import say.swing.JFontChooser;

/**
 * This class is used to update preferences.
 * 
 * @author Naoki Mizuno
 * 
 */
// TODO: 'Reset to default settings'
// TODO: extend Preferences?

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
        JFontChooser fontChooser = new JFontChooser();
        int result = fontChooser.showDialog(null);
        if (result == JFontChooser.OK_OPTION) {
            pref.setFont(fontChooser.getSelectedFont());
            pref.update();
        }
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
     * 
     * @return The new keyboard layout
     */
    // TODO: Current layout and existing layouts in different variables
    public String changeKeyboardLayout() {
        String previous = pref.getKeyboardLayout();

        String[] layouts = pref.getExistingKeyboardLayouts();
        // TODO: pass current item name
        ChooseFromExisting dialog = new ChooseFromExisting(layouts);
        dialog.dispose();

        String newLayout = dialog.getChosen();

        // Don't do anything if nothing changed
        if (newLayout == null || newLayout.equals(previous))
            return previous;

        boolean foundFlag = false;
        // Check if new layout is in the list of existing layouts
        for (int i = 0; i < layouts.length; i++) {
            // If found, make the new layout first in list
            if (layouts[i].equals(newLayout)) {
                String tmp = layouts[i];
                layouts[i] = layouts[0];
                layouts[0] = tmp;
                foundFlag = true;
                break;
            }
        }

        // Update list of layouts
        String newLayoutList = "";
        if (foundFlag == false)
            newLayoutList += newLayout + ",";
        for (int i = 0; i < layouts.length - 1; i++)
            newLayoutList += layouts[i] + ",";
        newLayoutList += layouts[layouts.length - 1];

        pref.setExistingKeyboardLayouts(newLayoutList);
        pref.update();

        return newLayout;
    }

    /**
     * Shows a dialog that allows user to change the color. A dialog that asks
     * the user which color to change is shown first. Then, according to the
     * selection, another dialog appears that allows the user to change the
     * selected color. Clicking on cancel will make no changes.
     */
    public void changeColor() {
        ColorChooser cc = new ColorChooser();
        cc.setVisible(true);
    }

    /**
     * Given a list of existing elements, this class shows a JDialog and allows
     * the user to either select from the existing items or enter a new item.
     * 
     * @author Naoki Mizuno
     * 
     */
    @SuppressWarnings("serial")
    class ChooseFromExisting extends JDialog {
        private JButton ok = new JButton("OK");
        private JButton cancel = new JButton("Cancel");
        private String selectedString;

        private JComboBox comboBox;
        private JTextField textField;

        public ChooseFromExisting(String[] existing) {
            comboBox = new JComboBox(existing);
            textField = new JTextField();

            add(selectAreaBuilder());
            add(buttonBuilder(), BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
            setModalityType(ModalityType.DOCUMENT_MODAL);
            setVisible(true);
        }

        private JPanel selectAreaBuilder() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

            panel.add(new JLabel("Select from existing items"));
            panel.add(comboBox);
            panel.add(new JLabel("or enter new item"));
            panel.add(textField);

            return panel;
        }

        /**
         * Returns the selected String. This method first looks at the
         * JTextField and sees if the user typed anything into it. If nothing is
         * typed, it'll use the selected item in the JComboBox.
         * 
         * @return The String selected.
         */
        private String getSelected() {
            String str = textField.getText().trim();
            if (str == null || str.equals(""))
                str = (String) comboBox.getSelectedItem();
            return str;
        }

        private JPanel buttonBuilder() {
            class ButtonListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == ok)
                        selectedString = getSelected();
                    dispose();
                }
            }
            ButtonListener listener = new ButtonListener();
            ok.addActionListener(listener);
            cancel.addActionListener(listener);

            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttons.add(ok);
            buttons.add(cancel);

            return buttons;
        }

        public String getChosen() {
            return selectedString;
        }
    }

    @SuppressWarnings("serial")
    class ColorChooser extends JDialog implements ActionListener {
        private JComboBox colorOf;
        private JColorChooser chooser;
        private JButton ok = new JButton("OK");
        private JButton cancel = new JButton("Cancel");
        private JButton apply = new JButton("Apply");

        public ColorChooser() {
            colorOf = new JComboBox(colorTypes);
            colorOf.addActionListener(this);
            String selectedByDefault = (String) colorOf.getSelectedItem();
            chooser = new JColorChooser(getColorForType(selectedByDefault));

            add(colorOf, BorderLayout.NORTH);
            add(chooser);
            add(buttonsPanelBuilder(), BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }

        private JPanel buttonsPanelBuilder() {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));

            ok.addActionListener(this);
            cancel.addActionListener(this);
            apply.addActionListener(this);
            panel.add(ok);
            panel.add(cancel);
            panel.add(apply);

            return panel;
        }

        private Color getColorForType(String type) {
            Color c = null;
            if (type.equals(TO_BE_TYPED))
                c = pref.getToBeTyped();
            else if (type.equals(ALREADY_TYPED))
                c = pref.getAlreadyTyped();
            else if (type.equals(MISTYPE))
                c = pref.getMissTypeColor();
            else if (type.equals(BACKGROUND))
                c = pref.getBackgroundColor();
            return c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedType = (String) colorOf.getSelectedItem();

            if (e.getSource() == ok || e.getSource() == apply) {
                Color selectedColor = chooser.getColor();

                if (selectedType.equals(TO_BE_TYPED))
                    pref.setToBeTyped(selectedColor);
                else if (selectedType.equals(ALREADY_TYPED))
                    pref.setAlreadyTyped(selectedColor);
                else if (selectedType.equals(MISTYPE))
                    pref.setMissTypeColor(selectedColor);
                else if (selectedType.equals(BACKGROUND))
                    pref.setBackgroundColor(selectedColor);

                pref.update();

                if (e.getSource() == ok)
                    dispose();
            }
            else if (e.getSource() == cancel)
                dispose();
            // When ComboBox is changed
            else {
                Color c = getColorForType(selectedType);

                if (c == null)
                    return;

                chooser.setColor(c);
            }
        }
    }
}