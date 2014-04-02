package com.nigorojr.typebest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
class ColorChooser extends JDialog implements ActionListener {
    private Preferences pref;

    private JComboBox colorOf;
    private JColorChooser chooser;
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");
    private JButton apply = new JButton("Apply");

    private PreviewPanel previewPanel;

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

    public ColorChooser(Preferences pref) {
        this.pref = pref;

        colorOf = new JComboBox(colorTypes);
        colorOf.addActionListener(this);
        String selectedByDefault = (String) colorOf.getSelectedItem();
        chooser = new JColorChooser(getColorForType(selectedByDefault));
        previewPanel = new PreviewPanel(getColorForType(selectedByDefault));

        chooser.setPreviewPanel(previewPanel);
        chooser.getSelectionModel().addChangeListener(previewPanel);

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

            previewPanel.setOldColor(c);
            chooser.setColor(c);
        }
    }

    class PreviewPanel extends JPanel implements ChangeListener {
        /* Color that's currently used */
        private Color oldColor;
        /* New color selected in the JColorChooser */
        private Color newColor;

        /* Sample text */
        private JPanel sampleText;
        private JLabel typedLabel;
        private JLabel mistypedLabel;
        private JLabel toBeTypedLabel;
        private String sampleTextString = "Typing is fun!";
        private int typedLetters = 8;

        /* Sample colors */
        private JPanel oldColorPanel;
        private JPanel newColorPanel;

        public PreviewPanel(Color oldColor) {
            this.oldColor = oldColor;
            this.newColor = oldColor;

            setLayout(new FlowLayout(FlowLayout.CENTER));

            sampleText = sampleTextBuilder();
            add(sampleText);
            add(colorPreviewBuilder());
        }

        private JPanel sampleTextBuilder() {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panel.setOpaque(true);

            String typed = sampleTextString.substring(0, typedLetters);
            String mistyped =
                    sampleTextString.substring(typedLetters, typedLetters + 1);
            String toBeTyped = sampleTextString.substring(typedLetters + 1);

            // Create JLabels for each color
            typedLabel = new JLabel(typed);
            mistypedLabel = new JLabel(mistyped);
            toBeTypedLabel = new JLabel(toBeTyped);

            // Set current colors
            typedLabel.setForeground(pref.getAlreadyTyped());
            mistypedLabel.setForeground(pref.getMissTypeColor());
            toBeTypedLabel.setForeground(pref.getToBeTyped());

            // Set font to current font
            typedLabel.setFont(pref.getFont());
            mistypedLabel.setFont(pref.getFont());
            toBeTypedLabel.setFont(pref.getFont());

            panel.add(typedLabel);
            panel.add(mistypedLabel);
            panel.add(toBeTypedLabel);
            panel.setBackground(pref.getBackgroundColor());

            return panel;
        }

        private JPanel colorPreviewBuilder() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.setOpaque(true);

            oldColorPanel = new JPanel();
            oldColorPanel.setPreferredSize(new Dimension(50, 25));
            oldColorPanel.setOpaque(true);
            oldColorPanel.setBackground(oldColor);

            newColorPanel = new JPanel();
            newColorPanel.setPreferredSize(new Dimension(50, 25));
            newColorPanel.setOpaque(true);
            newColorPanel.setBackground(newColor);

            panel.add(oldColorPanel);
            panel.add(newColorPanel);

            return panel;
        }

        public void setOldColor(Color c) {
            oldColor = c;
            oldColorPanel.setBackground(oldColor);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            newColor = chooser.getColor();

            // Update sample text
            String selectedType = (String) colorOf.getSelectedItem();
            if (selectedType.equals(TO_BE_TYPED))
                toBeTypedLabel.setForeground(newColor);
            else if (selectedType.equals(ALREADY_TYPED))
                typedLabel.setForeground(newColor);
            else if (selectedType.equals(MISTYPE))
                mistypedLabel.setForeground(newColor);
            else if (selectedType.equals(BACKGROUND))
                sampleText.setBackground(newColor);

            newColorPanel.setBackground(newColor);
        }
    }
}
