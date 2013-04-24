import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.Timer;

/**
 * This class adds the necessary components and creates the window.
 * All the other stuff such as reading/writing data etc. are left to MainTypePanel class.
 * @author Naoki Mizuno
 * TODO: Make the timer work!!!
 */

public class MainWindow extends JFrame {
	private MainTypePanel mtp = new MainTypePanel();
    private ClickResponder cr = new ClickResponder();
	private JButton restart = new JButton("Restart");
	private Timer timer;
	private long startTime = -1;
	HashMap<String, JMenuItem> menuItem = new HashMap<String, JMenuItem>();
	
	/**
	 * Creates a new window with a panel that you type in, and a menu bar.
	 */
	public MainWindow() {
		super();
		
		SpringLayout springLayout = new SpringLayout();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(springLayout);
		
		// Add items to menu
		menuItem.put("ch_user", new JMenuItem("Change User"));
		menuItem.put("ch_mode", new JMenuItem("Change Practice Mode"));
		menuItem.put("ch_font", new JMenuItem("Change Font"));
		menuItem.put("ch_layout", new JMenuItem("Change Keyboard Layout"));
		menuItem.put("ch_color", new JMenuItem("Change Color"));
		// NOTE: "Save Settings" will be added separately
		
		// Add the menu bar
		menuBar();
		
		// Box that shows how much time has elapsed
		final JLabel timeElapsed = new JLabel("0.0", JLabel.RIGHT);
		timeElapsed.setFont(new Font("Arial", Font.BOLD, 30));
		timeElapsed.setOpaque(true);
		timeElapsed.setPreferredSize(new Dimension(80, 34));
		timeElapsed.setBorder(BorderFactory.createLoweredBevelBorder());
		springLayout.putConstraint(SpringLayout.SOUTH, timeElapsed, -5, SpringLayout.NORTH, mtp);
		springLayout.putConstraint(SpringLayout.EAST, timeElapsed, -15, SpringLayout.EAST, mtp);
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(1);
				df.setMinimumFractionDigits(1);
				timeElapsed.setText(df.format((System.currentTimeMillis() - startTime) / 1000.0));
			}
		});
		
		// Window to type in
		springLayout.putConstraint(SpringLayout.NORTH, mtp, 50, SpringLayout.NORTH, mainPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, mtp, -40, SpringLayout.SOUTH, mainPanel);
		springLayout.putConstraint(SpringLayout.WEST, mtp, 5, SpringLayout.WEST, mainPanel);
		springLayout.putConstraint(SpringLayout.EAST, mtp, -5, SpringLayout.EAST, mainPanel);
		
        addKeyListener(new TypingResponder());
		
        springLayout.putConstraint(SpringLayout.EAST, restart, -8, SpringLayout.EAST, mainPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, restart, -8, SpringLayout.SOUTH, mainPanel);
        
        restart.addActionListener(cr);
        // When this is true, all the typing gets redirected to the button
        restart.setFocusable(false);
		
        // Add things to the main panel
        mainPanel.add(timeElapsed);
		mainPanel.add(restart);
		mainPanel.add(mtp);
		
        // Add to the frame
		getContentPane().add(mainPanel);
		
		// The size of the main window
		setSize(800, 400);
		
		// Load the previous user's data
		mtp.loadLastUser();
	}
	
	
	/**
	 * Adds a menu bar to the main panel.
	 */
	public void menuBar() {
		JMenuBar menu = new JMenuBar();
		// TODO: Think what kind of menus to add
		JMenu settings = new JMenu("Settings");
		for (String key : menuItem.keySet()) {
			menuItem.get(key).addActionListener(cr);
			settings.add(menuItem.get(key));
		}
		// This item is added separately so that it shows up at the last of the list
		JMenuItem save = new JMenuItem("Save Current Settings");
		save.addActionListener(cr);
		settings.add(save);
		
		menu.add(settings);
		setJMenuBar(menu);
	}
	
	/**
	 * When a key is pressed, pass it to the method in MainTypePanel that determines what to do.
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
			if (startTime == -1) {
				startTime = System.currentTimeMillis();
				timer.start();
			}
			mtp.processPressedKey(e.getKeyChar());
		}
	}
	
	/**
	 * Determines what to do when a button is pressed.
	 */
	public class ClickResponder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == restart)
				mtp.restart();
			// TODO: Actions when selecting various menu items
			else if (e.getSource() == menuItem.get("ch_user"))
				mtp.changeUser();
			else if (e.getSource() == menuItem.get("ch_mode"))
				// TODO: Change practice mode
				;
			else if (e.getSource() == menuItem.get("ch_font"))
				mtp.changeFont();
			else if (e.getSource() == menuItem.get("ch_layout"))
				mtp.changeKeyboardLayout();
			else if (e.getSource() == menuItem.get("ch_color"))
				mtp.changeColor();
			else if (e.getActionCommand() == "Save Current Settings")
				mtp.saveSettings();
		}
	}

	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mw.setLocationRelativeTo(null);
		mw.setVisible(true);
	}
}
