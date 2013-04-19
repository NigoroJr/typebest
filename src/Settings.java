import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * This is a class used to save the settings for a certain user (for example,
 * the font, the last mode s/he used, the last date the program has been used, etc.)
 * 
 * @author Naoki Mizuno
 *
 */

public class Settings {
	private String userName;
	
    private Color toBeTyped = Color.BLUE;
    private Color alreadyTyped = Color.RED;
    private Color backgroundColor = Color.GRAY;
    private Color missTypeColor = Color.CYAN;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 30);
    // The number of digits to show after decimal point
	private int speedFractionDigit = 8;
	private int timeFractionDigit = 9;
	private boolean shuffled = true;
	
	/**
	 * Creates a new Settings instance with the default settings value and
	 * an user name of "Anonymous".
	 */
	public Settings() {
		this("Anonymous");
	}
	
	/**
	 * Creates a new Settings instance with the default value and the given user name.
	 * @param userName The user's name, which will be used in the filename.
	 */
	public Settings(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Creates a new Settings instance with the default value and read the data from the
	 * file given as a Scanner object. The file contains the settings that were changed
	 * from the default value.
	 * @param userName The user's name, which will be used in the filename.
	 * @param read A Scanner object to read from the file containing settings.
	 */
	public Settings(String userName, Scanner read) {
		// TODO: Write loops and setters that read settings
	}
	
	/**
	 * Returns the color of the letters that are not yet typed.
	 * @return The color of the letters that haven't been typed yet.
	 */
	public Color getToBeTyped() {
		return toBeTyped;
	}
	
	/**
	 * Returns the color of the letters that have already been typed correctly.
	 * @return The color of the letters that have already been typed correctly.
	 */
	public Color getAlreadyTyped() {
		return alreadyTyped;
	}
	
	/**
	 * Returns the background color for the typing panel.
	 * @return The background color of the typing panel.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Returns the color the letters become hilighted when the user miss types.
	 * @return The color that the letters become when there is a miss type.
	 */
	public Color getMissTypeColor() {
		return missTypeColor;
	}
	
	/**
	 * Returns the default font used in the typing panel.
	 * @return The default font used in the typing panel.
	 */
	public Font getDefaultFont() {
		return defaultFont;
	}
	
	/**
	 * Returns the value of the speedFractionDigit,
	 * which is the number of digits after decimal points when showing the typing speed.
	 * @return The number of digits shown after the decimal points when showing the typing speed.
	 */
	public int getSpeedFractionDigit() {
		return speedFractionDigit;
	}
	
	/**
	 * Returns the value of the timeFractionDigit,
	 * which is the number of digits after decimal points when showing the time.
	 * @return The number of digits shown after the decimal points when showing the time.
	 */
	public int getTimeFractionDigit() {
		return timeFractionDigit;
	}
	
	/**
	 * Returns whether the words in the dictionary file should be shuffled or not.
	 * When this is true, the words will be shuffled using the Collections.shuffle() method.
	 */
	public boolean isShuffled() {
		return shuffled;
	}
	
	/**
	 * Shows a dialog that allows the user to change the font. The user can change
	 * the font family(Arial, MS Gothic, etc.), style (bold, italic), and the size in points.
	 * It uses a JComboBox for the family, JCheckBox for style, and a JSlider for the size.
	 * The modified values are substituted into the variables in this Settings class when either
	 * "Save" or "Apply" is clicked.
	 */
	public void changeFont() {
		final JDialog dialog = new JDialog();
		dialog.setSize(280, 150);
		dialog.setVisible(true);
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(new BorderLayout());
		
		// Panel that contains Font family, style, and size
		JPanel fontSetting = new JPanel();
		fontSetting.setLayout(new FlowLayout());
		
		// Get all the fonts available in that environment
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		final JComboBox fontNameComboBox = new JComboBox(fonts);
		fontNameComboBox.setPreferredSize(new Dimension(170, fontNameComboBox.getPreferredSize().height));
		fontSetting.add(fontNameComboBox);
		
		// Panel that contains the font's style
		JPanel fontStyle = new JPanel();
		fontStyle.setLayout(new GridLayout(0, 1));
		
		final JCheckBox bold = new JCheckBox("Bold");
		final JCheckBox italic = new JCheckBox("Italic");
		fontStyle.add(bold);
		fontStyle.add(italic);
		fontSetting.add(fontStyle);
		
		// Slider to set the size with the default value of 30
		final JSlider sizeSlider = new JSlider(5, 80, 30);
		sizeSlider.setMajorTickSpacing(15);
		sizeSlider.setMinorTickSpacing(5);
		sizeSlider.setPreferredSize(new Dimension(200, 30));
		final JLabel sizeLabel = new JLabel(sizeSlider.getValue() + "points");
		fontSetting.add(new JPanel() {{
			this.add(sizeSlider);
			this.add(sizeLabel);
		}});
		// Add a listener so that the size changes as the knob moves
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider js = (JSlider)e.getSource();
				sizeLabel.setText(String.format("%2d points", js.getValue()));
			}
		});
		
		// OK, Apply, and Cancel buttons, each having their own ActionListener
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttons.add(new JButton("Save") {{
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int choice = JOptionPane.showConfirmDialog(null, "Set this as the default font?", null, JOptionPane.CANCEL_OPTION);
					if (choice == 0) {
						// Save it and close
						// TODO: Write to file
						dialog.setVisible(false);
					}
				}
			});
		}});
		buttons.add(new JButton("Apply") {{
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Change the font
					// Get font name
					String selected = (String)fontNameComboBox.getSelectedItem();
					// Get style
					int style;
					if (bold.isSelected() && italic.isSelected())
						style = Font.BOLD + Font.ITALIC;
					else if (bold.isSelected())
						style = Font.BOLD;
					else if (italic.isSelected())
						style = Font.ITALIC;
					else
						style = Font.PLAIN;
				}
			});
		}});
		buttons.add(new JButton("Cancel") {{
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Close the dialog
					dialog.setVisible(false);
				}
			});
		}});
		
		// Finally, add all the components
		dialog.add(fontSetting);
		dialog.add(buttons, BorderLayout.SOUTH);
	}
	
	/**
	 * Returns a String representation of the user's settings. It can be used for printing the information
	 * to a file, as well as debugging.
	 */
	@Override
	public String toString() {
		String ret = "";
	    ret += String.format("toBeTyped\n%d\n", toBeTyped.getRGB());
	    ret += String.format("alreadyTyped\n%d\n", alreadyTyped.getRGB());
	    ret += String.format("backgroundColor\n%d\n", backgroundColor.getRGB());
	    ret += String.format("defaultFont\n%s %s %d\n", defaultFont.getFamily(), defaultFont.getStyle(), defaultFont.getSize());
	    ret += String.format("speedFractionDigit\n%d\n", speedFractionDigit);
	    ret += String.format("timeFractionDigit\n%d\n", timeFractionDigit);
	    ret += String.format("shuffled\n%s\n", shuffled);
		return ret;
    }
}
