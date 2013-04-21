import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    private Color toBeTyped = Color.BLUE;
    private Color alreadyTyped = Color.RED;
    private Color backgroundColor = Color.GRAY;
    private Color missTypeColor = Color.CYAN;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 30);
    // The number of digits to show after decimal point
	private int speedFractionDigit = 8;
	private int timeFractionDigit = 9;
	private boolean shuffled = true;
	private String keyboardLayout = "Qwerty";
	
	private File settingsFile;
	
	/**
	 * Creates a new Settings object with the information of the filename where the settings are stored.
	 */
	public Settings(String settingsFileName) {
		settingsFile = new File(settingsFileName);
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
	 * Returns the keyboard layout in String.
	 */
	public String getKeyboardLayout() {
		return keyboardLayout;
	}
	
	/**
	 * Sets the color of toBeTyped to the given Color.
	 */
	public void setToBeTyped(Color c) {
		toBeTyped = c;
	}
	
	/**
	 * Sets the color of alreadyTyped to the given Color.
	 */
	public void setAlreadyTyped(Color c) {
		alreadyTyped = c;
	}
	
	/**
	 * Sets the color of missTypeColor to the given Color.
	 */
	public void setMissTypeColor(Color c) {
		missTypeColor = c;
	}
	
	/**
	 * Sets the color of backgroundColor to the given Color.
	 */
	public void setBackgroundColor(Color c) {
		backgroundColor = c;
	}
	
	/**
	 * Reads the user's settings from the given file. In order to increase readability and accuracy,
	 * it checks for a complete match with the variable name.
	 */
	public void readSettings() {
		if (settingsFile.exists()) {
			try {
				Scanner file = new Scanner(settingsFile);
				String key, value;
				while (file.hasNext()) {
					
					// Checks for the validity of the key and value
					// It will use the default settings after a fail while reading the file.
					if (((key  = file.nextLine()) == null || !file.hasNext() || (value = file.nextLine()) == null ||
							key.equals("") || value.equals("")))
						break;
					
					else if (key.equals("toBeTyped"))
						toBeTyped = new Color(Integer.parseInt(value));
					else if (key.equals("alreadyTyped"))
						alreadyTyped = new Color(Integer.parseInt(value));
					else if (key.equals("backgroundColor"))
						backgroundColor = new Color(Integer.parseInt(value));
					else if (key.equals("missTypeColor"))
						missTypeColor = new Color(Integer.parseInt(value));
					else if (key.equals("defaultFont"))
						defaultFont = new Font(value,
								Integer.parseInt(file.nextLine()), Integer.parseInt(file.nextLine()));
					else if (key.equals("speedFractionDigit"))
						speedFractionDigit = Integer.parseInt(value);
					else if (key.equals("timeFractionDigit"))
						timeFractionDigit = Integer.parseInt(value);
					else if (key.equals("shuffled"))
						shuffled = Boolean.parseBoolean(value);
					else if (key.equals("keyboardLayout"))
						keyboardLayout = value;
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes the user's settings to the file using the given PrintWriter.
	 * Uses the toString method to get all the information about the instance variables.
	 * @param pw PrintWriter object containing the file information.
	 */
	public void writeSettings() {
		try {
			PrintWriter pw = new PrintWriter(settingsFile);
			pw.print(toString());
			pw.flush();
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(new BorderLayout());
		
		// Panel that contains Font family, style, and size
		JPanel fontSetting = new JPanel();
		fontSetting.setLayout(new FlowLayout());
		
		// Get all the fonts available in that environment
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		final JComboBox fontNameComboBox = new JComboBox(fonts);
		fontNameComboBox.setSelectedItem(defaultFont.getFamily());
		fontNameComboBox.setPreferredSize(new Dimension(170, fontNameComboBox.getPreferredSize().height));
		fontSetting.add(fontNameComboBox);
		
		// Panel that contains the font's style
		JPanel fontStyle = new JPanel();
		fontStyle.setLayout(new GridLayout(0, 1));
		
		final JCheckBox bold = new JCheckBox("Bold", defaultFont.isBold());
		final JCheckBox italic = new JCheckBox("Italic", defaultFont.isItalic());
		fontStyle.add(bold);
		fontStyle.add(italic);
		fontSetting.add(fontStyle);
		
		// Slider to set the size with the default value of the current point size
		final JSlider sizeSlider = new JSlider(5, 80, defaultFont.getSize());
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
		final ActionListener click = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add
				if (e.getActionCommand() == "Save" ||
					e.getActionCommand() == "Apply") {
					// Change the font
					// Get font name
					String name = (String)fontNameComboBox.getSelectedItem();
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
					
					int size = sizeSlider.getValue();
					
					// change font
					setDefaultFont(name, style, size);
					
					if (e.getActionCommand() == "Save") {
						int choice = JOptionPane.showConfirmDialog(null,
								"Set this as the default font?\n(Other settings will also be saved)",
								null, JOptionPane.CANCEL_OPTION);
						if (choice == 0) {
							// Save it and close
							writeSettings();
							dialog.setVisible(false);
						}
					}
						
				}
				if (e.getActionCommand() == "Close")
					// Close the dialog
					dialog.setVisible(false);
			}
		};
		buttons.add(new JButton("Save") {{
			this.addActionListener(click);
		}});
		buttons.add(new JButton("Apply") {{
			this.addActionListener(click);
		}});
		buttons.add(new JButton("Close") {{
			this.addActionListener(click);
		}});
		
		// Finally, add all the components
		dialog.add(fontSetting);
		dialog.add(buttons, BorderLayout.SOUTH);
		// Prevent other codes from running while the dialog is open
		dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		dialog.setVisible(true);
	}
	
	/**
	 * Changes the default font to the given font. This method accepts the family name, style, and the size.
	 * This method should be used with the changeFont method in this class.
	 * @param name The family name of the font in String
	 * @param style The style of the font. Font.PLAIN = 0, Font.BOLD = 1, Font.ITALIC = 2. BOLD + ITALIC is 3.
	 * @param size The point size of the font as an integer.
	 */
	public void setDefaultFont(String name, int style, int size) {
		defaultFont = new Font(name, style, size);
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
	    ret += String.format("defaultFont\n%s\n%d\n%d\n", defaultFont.getFamily(),
	    		defaultFont.getStyle(), defaultFont.getSize());
	    ret += String.format("speedFractionDigit\n%d\n", speedFractionDigit);
	    ret += String.format("timeFractionDigit\n%d\n", timeFractionDigit);
	    ret += String.format("shuffled\n%s\n", shuffled);
	    ret += String.format("keyboardLayout\n%s\n", keyboardLayout);
		return ret;
    }
}
