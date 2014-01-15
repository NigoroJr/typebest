package com.nigorojr.typebest;

import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ChangeColor extends JDialog {
    public ChangeColor() {
        final JDialog dialog = new JDialog();
        dialog.setTitle("Change color of");
        dialog.setLocationRelativeTo(null);
        dialog.setSize(190, 70);
        dialog.setLayout(new GridLayout(0, 1));

        // Ask which color the user wants to change
        final String[] choices = { "Untyped Letters", "Typed Letters",
                "Misstypes", "Background" };
        final JComboBox choose = new JComboBox(choices);

        JPanel buttons = new JPanel() {
            {
                final ActionListener click = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("OK")) {
                            dialog.setVisible(false);
                            String choice = (String) choose.getSelectedItem();
                            // Change the selected
                            Color selected;
                            if (choice.equals(choices[0])
                                    && (selected = ColorSelector
                                            .chooseColor(user
                                                    .getSettings()
                                                    .getToBeTyped())) != null)
                                user.getSettings().setToBeTyped(selected);
                            else if (choice.equals(choices[1])
                                    && (selected = ColorSelector
                                            .chooseColor(user
                                                    .getSettings()
                                                    .getAlreadyTyped())) != null)
                                user.getSettings().setAlreadyTyped(selected);
                            else if (choice.equals(choices[2])
                                    && (selected = ColorSelector
                                            .chooseColor(user
                                                    .getSettings()
                                                    .getMissTypeColor())) != null)
                                user.getSettings().setMissTypeColor(selected);
                            else if (choice.equals(choices[3])
                                    && (selected = ColorSelector
                                            .chooseColor(user.getSettings()
                                                    .getBackgroundColor())) != null)
                                user.getSettings().setBackgroundColor(selected);
                        }
                        else if (e.getActionCommand().equals("Cancel"))
                            dialog.setVisible(false);
                    }
                };
                this.add(new JButton("OK") {
                    {
                        this.addActionListener(click);
                    }
                });
                this.add(new JButton("Cancel") {
                    {
                        this.addActionListener(click);
                    }
                });
            }
        };

        dialog.add(choose);
        dialog.add(buttons);

        dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
        dialog.setVisible(true);
    }
}
