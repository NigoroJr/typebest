package com.nigorojr.typebest;

import java.awt.FlowLayout;
import java.text.DateFormat;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

@SuppressWarnings("serial")
public class RecordsWindow extends JFrame {
    private JPanel recordsPanel = new JPanel();

    public RecordsWindow(Records records) {
        super("Records");

        setSize(800, 500);
        setPreferredSize(getSize());

        recordsPanel.setLayout(new BoxLayout(recordsPanel, BoxLayout.PAGE_AXIS));
        addRecords(records);

        JScrollPane jsp = new JScrollPane(recordsPanel);
        jsp.setLayout(new ScrollPaneLayout());
        add(jsp);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void addRecords(Records records) {
        Record[] recordArray = records.getAllRecords();
        for (Record r : recordArray) {
            JPanel p = new JPanel();
            // TODO: set fonts
            p.setLayout(new FlowLayout(FlowLayout.LEADING));
            p.add(new JLabel(String.format("%.3f", r.time / 1000000000.0)));
            p.add(new JLabel(Integer.toString(r.miss)));
            p.add(new JLabel(r.keyboardLayout));
            p.add(new JLabel(r.username));
            p.add(new JLabel(DateFormat.getInstance().format(r.date)));

            recordsPanel.add(p);
        }
    }
}
