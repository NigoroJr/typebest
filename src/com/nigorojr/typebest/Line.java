package com.nigorojr.typebest;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Line extends JPanel {
    private ArrayList<Word> line = new ArrayList<Word>();
    private final JLabel space = new JLabel(" ");

    /**
     * Returns the total width of the words in the line by adding the length of
     * the word and the width of the spaces between them.
     * 
     * @return The total width of the words in the line, including the spaces
     *         between the words.
     */
    public int getWordsWidth() {
        int width = 0;
        for (int i = 0; i < line.size(); i++)
            width += line.get(i).getWidth();

        // Add total length of the spaces between words
        width += space.getWidth() * (line.size() - 1);

        return width;
    }
}
