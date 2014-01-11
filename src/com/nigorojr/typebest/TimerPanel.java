package com.nigorojr.typebest;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This class is used to show how much time has elapsed since the user started
 * typing the first letter. This uses the time that the timer started and
 * subtract that from the current system time in nanoseconds to find how much
 * time has elapsed. Using this method appears smoother than using a counter.
 * 
 * @author Naoki Mizuno
 * 
 */

public class TimerPanel extends JLabel implements ActionListener {
    private Timer timer;
    private static DecimalFormat df = new DecimalFormat() {
        {
            setMaximumFractionDigits(1);
            setMinimumFractionDigits(1);
        }
    };
    private long startTime;

    public TimerPanel() {
        super(df.format(0), JLabel.RIGHT);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        setOpaque(true);
        setPreferredSize(new Dimension(90, 34));
        setBorder(BorderFactory.createLoweredBevelBorder());
        timer = new Timer(100, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setText(df.format((System.nanoTime() - startTime) / 1000000000.0));
    }

    /**
     * Starts the timer.
     */
    public void start() {
        if (!timer.isRunning())
            startTime = System.nanoTime();
        timer.start();
    }

    /**
     * Stops the timer. This does not reset the time to 0 so it is the same as
     * pausing the timer. Use reset() if you want to reset time to 0.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Resumes the timer.
     */
    public void resume() {
        timer.start();
    }

    /**
     * Resets the timer to 0.
     */
    public void reset() {
        timer.stop();
        setText(df.format(0));
    }
}