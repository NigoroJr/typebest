package com.nigorojr.typebest;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Word extends JPanel {
    private ArrayList<Letter> letters = new ArrayList<Letter>();
    private String rawWord;

    /**
     * Accepts a String which is what will be shown in this JPanel.
     * The constructor will then split the word into letters and create a Letter
     * object for each letter. After that, Letter objects are added to the
     * this JPanel.
     * 
     * @param word
     *            The word that will be shown as a collection of "Letter" class.
     */
    public Word(String word) {
        rawWord = word;
        letterCount = 0;

        split(word);
        for (int i = 0; i < letters.size(); i++)
            this.add(letters.get(i));
    }

    /**
     * Splits up the word into letters and add them to an ArrayList.
     */
    private void split(String word) {
        for (int i = 0; i < rawWord.length(); i++) {
            Letter letter = new Letter(rawWord.charAt(i));
            letters.add(letter);
        }
    }
}
