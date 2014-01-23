package com.nigorojr.typebest;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

public class Tester {

    public static void main(String[] args) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Testing WordSelector class...");
        Iterator<String> it = WordSelector.getWords(WordSelector.NORMAL, 50)
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

        ColorSelector.chooseColor();
    }

}
