import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ColorDialog extends JDialog {
	private static Color selectedColor;
	
	private static JSlider redSlider;
	private static JSlider greenSlider;
	private static JSlider blueSlider;
	
	private static JLabel redSliderText = new JLabel("Red: ");
	private static JLabel greenSliderText = new JLabel("Green: ");
	private static JLabel blueSliderText = new JLabel("Blue: ");
	
	/**
	 * Shows a new color selection dialog with the current (default values) being black.
	 */
	public ColorDialog() {
		this(Color.BLACK);
	}
	
	/**
	 * Creates a new color selection dialog with the default values of the given color.
	 * There are 2 preview windows, one being the current color and the other being the color
	 * from the current value of the sliders. 3 sliders each represents the value of red, green, blue
	 * respectively and the right preview window will update itself as the user slides the slider.
	 * @param current The color that will be set as the default value.
	 */
	public ColorDialog(final Color current) {
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		// Initialize the instance variables
		redSlider = new JSlider(0, 255, current.getRed());
		greenSlider = new JSlider(0, 255, current.getGreen());
		blueSlider = new JSlider(0, 255, current.getBlue());
		redSliderText = new JLabel("Red: " + redSlider.getValue());
		greenSliderText = new JLabel("Green: " + greenSlider.getValue());
		blueSliderText = new JLabel("Blue: " + blueSlider.getValue());
				
		// Panel for color preview and sliders
		JPanel grid = new JPanel(new GridLayout(0, 1));
		// Panel for preview
		final JPanel preview = new JPanel() {{
			this.add(new JPanel() {{
				this.setBackground(current);
				this.setPreferredSize(new Dimension(100, 80));
			}});
			this.add(new JPanel() {{
				this.setBackground(new
						Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
				this.setPreferredSize(new Dimension(100, 80));
			}});
		}};
	
		// Add CheckEvent to the sliders
		ChangeListener slide = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() == redSlider)
					redSliderText.setText("Red: " + redSlider.getValue());
				else if (e.getSource() == greenSlider)
					greenSliderText.setText("Green: " + greenSlider.getValue());
				else if (e.getSource() == blueSlider)
					blueSliderText.setText("Blue: " + blueSlider.getValue());
				// Change the color of the preview panel (index is 1)
				preview.getComponent(1).setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
			}
		};
		redSlider.addChangeListener(slide);
		greenSlider.addChangeListener(slide);
		blueSlider.addChangeListener(slide);
		
		// Sliders
		JPanel sliders = new JPanel(new GridLayout(0, 1)) {{
			this.add(new JPanel() {{
				this.add(redSliderText);
				this.add(redSlider);
			}});
			this.add(new JPanel() {{
				this.add(greenSliderText);
				this.add(greenSlider);
			}});
			this.add(new JPanel() {{
				this.add(blueSliderText);
				this.add(blueSlider);
			}});
		}};
		grid.add(preview);
		grid.add(sliders);
		
		// Add buttons
		JPanel buttons = new JPanel();
		JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		ActionListener click = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					selectedColor = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
					setVisible(false);
				}
				else if (e.getActionCommand().equals("Cancel"))
					setVisible(false);
			}
		};
		ok.addActionListener(click);
		cancel.addActionListener(click);
		buttons.add(ok);
		buttons.add(cancel);
		
		// Set all the panels
		this.add(grid);
		this.add(buttons, BorderLayout.SOUTH);
		
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		this.setVisible(true);
	}
	
	public static Color chooseColor() {
		return chooseColor(Color.BLACK);
	}
	
	public static Color chooseColor(Color c) {
		ColorDialog dialog = new ColorDialog(c);
		return selectedColor;
	}
}
