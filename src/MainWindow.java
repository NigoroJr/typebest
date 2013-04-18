import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * This class adds the necessary components and creates the window.
 * All the other stuff such as reading/writing data etc. are left to MainTypePanel class.
 * @author Naoki Mizuno
 *
 */

public class MainWindow extends JFrame {
	private MainTypePanel mtp = new MainTypePanel();
	private JButton restart = new JButton("Restart");
	
	/**
	 * Creates a new window with a panel that you type in, and a menu bar.
	 */
	public MainWindow() {
		super();
		
		SpringLayout springLayout = new SpringLayout();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(springLayout);
		
		// Add the menu bar
		menuBar();
		
		// Window to type in
		springLayout.putConstraint(SpringLayout.NORTH, mtp, 15, SpringLayout.NORTH, mainPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, mtp, -40, SpringLayout.SOUTH, mainPanel);
		springLayout.putConstraint(SpringLayout.WEST, mtp, 5, SpringLayout.WEST, mainPanel);
		springLayout.putConstraint(SpringLayout.EAST, mtp, -5, SpringLayout.EAST, mainPanel);
		
        addKeyListener(new TypingResponder());
		
        springLayout.putConstraint(SpringLayout.EAST, restart, -8, SpringLayout.EAST, mainPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, restart, -8, SpringLayout.SOUTH, mainPanel);
        
        ButtonResponder br = new ButtonResponder();
        restart.addActionListener(br);
        // When this is true, all the typing gets redirected to the button
        restart.setFocusable(false);
		
        // Add things to the main panel
		mainPanel.add(restart);
		mainPanel.add(mtp);
		
        // Add to the frame
		getContentPane().add(mainPanel);
		
		// The size of the main window
		setSize(800, 400);
		
		// Load the previous user's data
		mtp.loadPreviousSession();
	}
	
	
	/**
	 * Adds a menu bar to the main panel.
	 */
	public void menuBar() {
		// TODO: Think what kind of menus to add
		JMenuBar menu = new JMenuBar();
		JMenu open = new JMenu("Open");
		open.add(new JMenuItem("Change user"));
		open.add(new JMenuItem("Change Practice Mode"));
		
		menu.add(open);
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
			mtp.processPressedKey(e.getKeyChar());
		}
	}
	
	/**
	 * Determines what to do when a button is pressed.
	 */
	public class ButtonResponder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == restart)
				mtp.restart();
		}
	}

	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mw.setLocationRelativeTo(null);
		mw.setVisible(true);
	}
}
