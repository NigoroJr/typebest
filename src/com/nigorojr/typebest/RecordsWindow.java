package com.nigorojr.typebest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.apache.derby.client.am.DateTime;

/**
 * This class shows a window with a list of all the records of that user.
 * 
 * @author Naoki Mizuno
 * 
 */

@SuppressWarnings("serial")
public class RecordsWindow extends JDialog implements ActionListener {
    private Records records;
    public static final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    private JButton ok;

    public RecordsWindow(Records records) {
        this.records = records;

        ok = new JButton("OK");

        setTitle("Records");
        setSize(820, 440);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Creates a JPanel with the buttons.
     * 
     * @return JPanel with the buttons
     */
    private JPanel buttonsBuilder() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        buttons.add(ok);
        ok.addActionListener(this);

        return buttons;
    }

    private JPanel recordPanelHeaderBuilder() {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        Border border = BorderFactory.createLineBorder(Color.RED);

        JLabel time = new JLabel("Time");
        time.setFont(font);
        time.setBorder(border);
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setPreferredSize(new Dimension(100, time.getPreferredSize().height));

        JLabel miss = new JLabel("Miss");
        miss.setFont(font);
        miss.setBorder(border);
        miss.setHorizontalAlignment(SwingConstants.CENTER);
        miss.setPreferredSize(new Dimension(60, miss.getPreferredSize().height));

        JLabel name = new JLabel("Username");
        name.setFont(font);
        name.setBorder(border);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setPreferredSize(new Dimension(200, name.getPreferredSize().height));

        JLabel layout = new JLabel("Keyboard");
        layout.setFont(font);
        layout.setBorder(border);
        layout.setHorizontalAlignment(SwingConstants.CENTER);
        layout.setPreferredSize(new Dimension(150,
                layout.getPreferredSize().height));

        JLabel date = new JLabel("Date");
        date.setFont(font);
        date.setBorder(border);
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setPreferredSize(new Dimension(230, date.getPreferredSize().height));

        time.setBackground(Color.LIGHT_GRAY);
        miss.setBackground(Color.LIGHT_GRAY);
        name.setBackground(Color.LIGHT_GRAY);
        layout.setBackground(Color.LIGHT_GRAY);
        date.setBackground(Color.LIGHT_GRAY);

        time.setOpaque(true);
        miss.setOpaque(true);
        name.setOpaque(true);
        layout.setOpaque(true);
        date.setOpaque(true);

        header.add(time);
        header.add(miss);
        header.add(name);
        header.add(layout);
        header.add(date);

        header.setMaximumSize(header.getPreferredSize());
        return header;
    }

    private JPanel oneRecordPanelBuilder(Record record) {
        return oneRecordPanelBuilder(record, null, null);
    }

    private JPanel oneRecordPanelBuilder(Record record, Color textColor,
            Color backgroundColor) {
        JPanel oneRecord = new JPanel();
        oneRecord.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        Border border = BorderFactory.createLineBorder(Color.RED);

        JLabel time = new JLabel(String.format("%.3f",
                record.time / 1000000000.0));
        time.setFont(font);
        time.setBorder(border);
        time.setHorizontalAlignment(SwingConstants.RIGHT);
        time.setPreferredSize(new Dimension(100, time.getPreferredSize().height));

        JLabel miss = new JLabel(Integer.toString(record.miss));
        miss.setFont(font);
        miss.setBorder(border);
        miss.setHorizontalAlignment(SwingConstants.RIGHT);
        miss.setPreferredSize(new Dimension(60, miss.getPreferredSize().height));

        JLabel name = new JLabel(record.username);
        name.setFont(font);
        name.setBorder(border);
        name.setPreferredSize(new Dimension(200, name.getPreferredSize().height));

        JLabel layout = new JLabel(record.keyboardLayout);
        layout.setFont(font);
        layout.setBorder(border);
        layout.setPreferredSize(new Dimension(150,
                layout.getPreferredSize().height));

        JLabel date = new JLabel(DateFormat.getInstance().format(record.date));
        date.setFont(font);
        date.setBorder(border);
        date.setPreferredSize(new Dimension(230, date.getPreferredSize().height));

        // Add text color
        if (textColor != null && backgroundColor != null) {
            time.setForeground(textColor);
            miss.setForeground(textColor);
            name.setForeground(textColor);
            layout.setForeground(textColor);
            date.setForeground(textColor);

            time.setBackground(backgroundColor);
            miss.setBackground(backgroundColor);
            name.setBackground(backgroundColor);
            layout.setBackground(backgroundColor);
            date.setBackground(backgroundColor);

            time.setOpaque(true);
            miss.setOpaque(true);
            name.setOpaque(true);
            layout.setOpaque(true);
            date.setOpaque(true);
        }

        oneRecord.add(time);
        oneRecord.add(miss);
        oneRecord.add(name);
        oneRecord.add(layout);
        oneRecord.add(date);

        oneRecord.setMaximumSize(oneRecord.getPreferredSize());
        return oneRecord;
    }

    /**
     * Shows all the records in the database.
     */
    public void showAll() {
        showTop(records.getAllRecords().length);
    }

    /**
     * Shows the top n records in the database.
     * 
     * @param n
     *            The number of records to show from the top.
     */
    public void showTop(int n) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Header
        panel.add(recordPanelHeaderBuilder());

        for (Record record : records.getTopRecords(n))
            panel.add(oneRecordPanelBuilder(record));

        JScrollPane pane = new JScrollPane(panel);
        pane.setLayout(new ScrollPaneLayout());
        add(pane);

        add(buttonsBuilder(), BorderLayout.SOUTH);
        showWindow();
    }

    /**
     * Shows the top records and also the current record.
     */
    public void showDefault(Record lastRecord) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Header
        panel.add(recordPanelHeaderBuilder());

        boolean appendLastRecord = true;
        for (Record record : records.getTopRecords(10)) {
            // Add color if last record is in top 10
            if (record.equals(lastRecord)) {
                panel.add(oneRecordPanelBuilder(record, Color.BLUE, Color.GRAY));
                appendLastRecord = false;
            }
            else
                panel.add(oneRecordPanelBuilder(record));
        }
        // If last record wasn't in the top 10
        if (appendLastRecord) {
            // TODO: Improve message
            JLabel mes = new JLabel("Not in the top 10");
            JPanel mesPanel = new JPanel();
            mesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            mesPanel.add(mes);
            panel.add(mesPanel);

            panel.add(oneRecordPanelBuilder(lastRecord, Color.BLUE, Color.GRAY));
        }

        add(panel);
        add(buttonsBuilder(), BorderLayout.SOUTH);
        showWindow();
    }

    private void showWindow() {
        setPreferredSize(getSize());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok)
            dispose();
    }
}
