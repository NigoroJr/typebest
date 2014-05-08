package com.nigorojr.typebest;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tester {

    public static void main(String[] args) {
        // Test ColorSelector
        // ColorSelector.chooseColor();
        // testChangeColor();
        // changeColor();
        testDerby();
    }

    public static void testDerby() {
        // This is to test the behavior of the Database class
        try {
            Records rec = new Records();
            rec.addNewRecord(5, "foooooobar", "Dvorak", 23456234, 34);
            rec.addNewRecord(4, "hogehoge", "qwerty", 217934, 14);

            System.out.println(rec.getAllRecords()[1].username);
            System.out.println(rec.getAllRecords()[0].username);

            LinkedHashMap<String, String> val = new LinkedHashMap<String, String>();
            val.put("USERNAME", "'the username was successfully changed'");
            rec.update(val, "WHERE USER_ID = 4");
            rec.delete("WHERE USER_ID = 5");

            System.out.println(rec.getAllRecords()[1].user_id
                    + rec.getAllRecords()[1].username);
            System.out.println(rec.getAllRecords()[0].user_id
                    + rec.getAllRecords()[0].username);

            // Test for Preferences class
            Preferences p = new Preferences("test_user");
            p.readPreferencesForID(1);
            System.out.println(p.getKeyboardLayout());

            // Limit
            System.out.print("testing limit: ");
            System.out.println(rec.getTopRecords(2)[0].username);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testWordSelector() {
        System.out.println("Testing WordSelector class...");
        Iterator<String> it =
                new WordSelector().getWords(WordSelector.NORMAL, 50)
                .iterator();
        while (it.hasNext())
            System.out.print(it.next() + " ");

        JFrame frame = new JFrame("Testing!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        final TimerPanel tp = new TimerPanel();
        frame.add(tp);
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        tp.start();
                        break;
                    case MouseEvent.BUTTON2:
                        tp.reset();
                        break;
                    case MouseEvent.BUTTON3:
                        tp.stop();
                        break;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        };
        frame.addMouseListener(ml);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void testRecordWindow() {
        TypePanel p = new TypePanel(new WordSelector());
        Record rec = new Record(6, "foooooobar", "Dvorak", 514893, 22);
        p.getRecords().addNewRecord(rec);
        RecordsWindow r = new RecordsWindow(p.getRecords());
        r.setVisible(true);
    }

    public static void testChangeColor() {
        try {
            Preferences pref = new Preferences("Tester");
            ChangePreferences cp = new ChangePreferences(pref);
            System.out.println(pref.getToBeTyped());
            cp.changeColor();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeColor() {
        String[] colorTypes = { "tbt", "alr", "mis", "bac" };
        JDialog dialog = new JDialog();
        JComboBox<String> comboBox = new JComboBox<String>(colorTypes);
        final JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttons.add(ok);
        buttons.add(cancel);
        comboBox.setPreferredSize(new Dimension(200, 20));

        JLabel label = new JLabel("Select color to change");
        label.setPreferredSize(new Dimension(200, 50));
        label.setFont(new Font("Arial", Font.PLAIN, 10));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(label);
        panel.add(comboBox);
        panel.add(buttons);

        dialog.add(panel);

        dialog.setSize(new Dimension(200, 100));
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
