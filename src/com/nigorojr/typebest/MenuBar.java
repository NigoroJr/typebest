package com.nigorojr.typebest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
    public static final String MODE_NORMAL = "Normal";
    public static final String MODE_LETTERS_A_F = "Letters A-F";
    public static final String MODE_LETTERS_G_P = "Letters G-P";
    public static final String MODE_LETTERS_Q_Z = "Letters Q-Z";
    public static final String MODE_PROGRAMMER = "Programmer";

    /* Used when restarting */
    // TODO: Determine whether this is better avoided
    private MainWindow mainWindow;
    private ChangePreferences cp;
    private WordSelector wordSelector;
    private LinkedHashMap<String, JMenu> menu = new LinkedHashMap<String, JMenu>() {
        {
            put("modes", new JMenu("Mode"));
            put("settings", new JMenu("Settings"));
        }
    };

    // Menu items in the Settings menu
    private LinkedHashMap<String, JMenuItem> settings = new LinkedHashMap<String, JMenuItem>() {
        {
            put("ch_user", new JMenuItem("Change User"));
            put("ch_font", new JMenuItem("Change Font"));
            put("ch_layout", new JMenuItem("Change Keyboard Layout"));
            put("ch_color", new JMenuItem("Change Color"));
        }
    };

    public MenuBar(ChangePreferences cp, WordSelector wordSelector,
            MainWindow mainWindow) {
        this.cp = cp;
        this.wordSelector = wordSelector;
        this.mainWindow = mainWindow;

        addMenu();

        addModes();
        addSettings();
    }

    public void addMenu() {
        Iterator<Entry<String, JMenu>> menus =
                menu.entrySet().iterator();

        while (menus.hasNext()) {
            String menuName = menus.next().getKey();
            // Add JMenu
            add(menu.get(menuName));
        }
    }

    public void addModes() {
        JMenuItem[] items = {
                new JMenuItem(MODE_NORMAL),
                new JMenuItem(MODE_LETTERS_A_F),
                new JMenuItem(MODE_LETTERS_G_P),
                new JMenuItem(MODE_LETTERS_Q_Z),
                new JMenuItem(MODE_PROGRAMMER),
        };

        /**
         * Changes the mode to the selected mode.
         * 
         * @author Naoki Mizuno
         * 
         */
        class ModeListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                int mode = WordSelector.NORMAL;
                if (e.getActionCommand().equals(MODE_NORMAL)) {
                    mode = WordSelector.NORMAL;
                }
                else if (e.getActionCommand().equals(MODE_LETTERS_A_F)) {
                    mode = WordSelector.LETTERS_A_F;
                }
                else if (e.getActionCommand().equals(MODE_LETTERS_G_P)) {
                    mode = WordSelector.LETTERS_G_P;
                }
                else if (e.getActionCommand().equals(MODE_LETTERS_Q_Z)) {
                    mode = WordSelector.LETTERS_Q_Z;
                }
                else if (e.getActionCommand().equals(MODE_PROGRAMMER)) {
                    mode = WordSelector.PROGRAMMER;
                }
                wordSelector.setCurrentMode(mode);
                mainWindow.restart();
            }
        }

        for (JMenuItem item : items) {
            item.addActionListener(new ModeListener());
            menu.get("modes").add(item);
        }
    }

    public void addSettings() {

        Iterator<Entry<String, JMenuItem>> items =
                settings.entrySet().iterator();

        class SettingsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == settings.get("ch_user"))
                    cp.changeUser();
                else if (e.getSource() == settings.get("ch_mode"))
                    // TODO: Change practice mode
                    ;
                else if (e.getSource() == settings.get("ch_font"))
                    cp.changeFont();
                else if (e.getSource() == settings.get("ch_layout"))
                    cp.changeKeyboardLayout();
                else if (e.getSource() == settings.get("ch_color"))
                    cp.changeColor();

            }
        }

        while (items.hasNext()) {
            JMenuItem item = items.next().getValue();
            item.addActionListener(new SettingsListener());
            menu.get("settings").add(item);
        }
    }

}