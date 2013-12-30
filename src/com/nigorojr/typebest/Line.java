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

    /**
     * Adds a word to the line. This method also adds a space character to the
     * line in order to separate the words with spaces.
     * 
     * @param word
     */
    public void addWord(String word) {
        Word wordPanel = new Word(word);
        line.add(wordPanel);

        // Don't add a space character if it's the first word in the line
        if (!line.isEmpty())
            this.add(space);
        this.add(wordPanel);
    }
}
