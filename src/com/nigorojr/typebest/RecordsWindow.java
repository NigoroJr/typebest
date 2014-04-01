package com.nigorojr.typebest;

import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DateFormat;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

/**
 * This class shows a window with a list of all the records of that user.
 * 
 * @author Naoki Mizuno
 * 
 */

@SuppressWarnings("serial")
public class RecordsWindow extends JDialog {
    private JPanel recordsPanel = new JPanel();
    public static final Font font = new Font("Arial", Font.PLAIN, 20);

    public RecordsWindow(Records records) {
        setTitle("Records");
        setSize(820, 440);

        recordsPanel
                .setLayout(new BoxLayout(recordsPanel, BoxLayout.PAGE_AXIS));
        addRecords(records);

        JScrollPane jsp = new JScrollPane(recordsPanel);
        jsp.setLayout(new ScrollPaneLayout());
        add(jsp);

        setPreferredSize(getSize());
        pack();
        setLocationRelativeTo(null);
    }

    private void addRecords(Records records) {
        Record[] recordArray = records.getAllRecords();

        for (Record record : recordArray) {
            JPanel oneRecord = new JPanel();
            oneRecord.setLayout(new FlowLayout(FlowLayout.LEADING));

            String[] recordString = {
                    String.format("%.3f", record.time / 1000000000.0),
                    Integer.toString(record.miss),
                    record.username,
                    record.keyboardLayout,
                    DateFormat.getInstance().format(record.date),
            };
            for (String str : recordString) {
                JLabel label = new JLabel(str);
                label.setFont(font);
                oneRecord.add(label);
            }

            oneRecord.setMaximumSize(oneRecord.getPreferredSize());
            recordsPanel.add(oneRecord);
        }
    }
}
