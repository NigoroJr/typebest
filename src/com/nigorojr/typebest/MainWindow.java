package com.nigorojr.typebest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 * This class adds the necessary components and creates the window. All the
 * other stuff such as reading/writing data etc. are left to MainTypePanel
 * class.
 * TODO: change "miss type" to "mistype"
 * 
 * @author Naoki Mizuno
 */

@SuppressWarnings("serial")
public class MainWindow extends JDialog {
    private TypePanel typePanel;
    private WordSelector wordSelector = new WordSelector();
    private ClickResponder clickResponder = new ClickResponder();
    private JButton restartButton = new JButton("Restart");
    private TimerPanel timeElapsed;
    private JLabel keyboardLayout;

    private boolean restartFlag = false;
    private boolean finished = false;

    /**
     * Creates a new window with a panel that you type in, and a menu bar.
     */
    public MainWindow() {
        super();

        setTitle("TypeBest");

        SpringLayout springLayout = new SpringLayout();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(springLayout);

        // Initialize TypePanel and read preferences
        typePanel = new TypePanel(wordSelector);
        typePanel.loadPreferences();
        typePanel.loadLinesAndAddToPanel();

        // Add the menu bar
        ChangePreferences cp =
                new ChangePreferences(typePanel.getPreferences());
        MenuBar menuBar = new MenuBar(cp, wordSelector, this);
        setJMenuBar(menuBar);

        // Box that shows how much time has elapsed
        timeElapsed = new TimerPanel();
        springLayout.putConstraint(SpringLayout.SOUTH, timeElapsed, -5,
                SpringLayout.NORTH, typePanel);
        springLayout.putConstraint(SpringLayout.EAST, timeElapsed, -15,
                SpringLayout.EAST, typePanel);

        // JLabel that shows the current keyboard layout
        keyboardLayout =
                new JLabel(typePanel.getPreferences().getKeyboardLayout());
        springLayout.putConstraint(SpringLayout.SOUTH, keyboardLayout,
                -5, SpringLayout.NORTH, typePanel);
        springLayout.putConstraint(SpringLayout.WEST, keyboardLayout,
                15, SpringLayout.WEST, typePanel);

        // Window to type in
        springLayout.putConstraint(SpringLayout.NORTH, typePanel, 50,
                SpringLayout.NORTH, mainPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, typePanel, -40,
                SpringLayout.SOUTH, mainPanel);
        // springLayout.putConstraint(SpringLayout.WEST, typePanel, 5,
        // SpringLayout.WEST, mainPanel);
        // springLayout.putConstraint(SpringLayout.EAST, typePanel, -5,
        // SpringLayout.EAST, mainPanel);

        addKeyListener(new TypingResponder());

        springLayout.putConstraint(SpringLayout.EAST, restartButton, -8,
                SpringLayout.EAST, mainPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, restartButton, -8,
                SpringLayout.SOUTH, mainPanel);

        restartButton.addActionListener(clickResponder);
        // When this is true, all the typing gets redirected to the button
        restartButton.setFocusable(false);

        // Add things to the main panel
        mainPanel.add(keyboardLayout);
        mainPanel.add(timeElapsed);
        mainPanel.add(restartButton);
        mainPanel.add(typePanel);

        // Make the MainTypePanel scrollable (experimental)
        // JScrollPane scrollPane = new JScrollPane();
        // scrollPane.setViewportView(typePanel);

        // Add to the frame
        getContentPane().add(mainPanel);
        // getContentPane().add(scrollPane);

        // The size of the main window
        setSize(820, 440);
        setPreferredSize(getSize());
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public void restart() {
        timeElapsed.reset();
        finished = false;
        typePanel.reset();
    }

    /**
     * When a key is pressed, pass it to the method in TypePanel that
     * determines what to do.
     */
    public class TypingResponder implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Restart when ESCAPE key is pressed twice in a row
            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                if (restartFlag) {
                    restartFlag = false;
                    restart();
                }
                else
                    restartFlag = true;
            }
            else if (!finished) {
                if (!typePanel.isRunning()) {
                    typePanel.start();
                    timeElapsed.start();
                    finished = false;
                }

                restartFlag = false;
                typePanel.processPressedKey(e.getKeyChar());

                // If this was the last key
                if (!typePanel.isRunning()) {
                    timeElapsed.stop();
                    finished = true;
                    typePanel.showResultAndAdd(typePanel.getLastRecord());
                    RecordsWindow rw = new RecordsWindow(typePanel.getRecords());
                    rw.showDefault(typePanel.getLastRecord());
                }
            }
        }
    }

    /**
     * Determines what to do when a button is pressed.
     */
    public class ClickResponder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == restartButton)
                restart();
        }
    }

    public void setKeyboardLayout(String layout) {
        keyboardLayout.setText(layout);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new MainWindow();
    }
}
