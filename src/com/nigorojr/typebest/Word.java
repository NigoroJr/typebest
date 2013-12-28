package com.nigorojr.typebest;

import java.util.ArrayList;

import javax.swing.JLabel;

	private String rawWord;
	public Word(String word) {
		rawWord = word;
		
		split();
	}
public class Word {
    private ArrayList<Letter> word = new ArrayList<Letter>();

    /**
     * Accepts a String which is what will be shown in this JLabel.
     * The constructor will then split the word into letters and create a Letter
     * object for each letter.
     * 
     * @param word
     *            The word that will be shown as a collection of "Letter" class.
     */

    /**
     * Splits up the word into letters and add them to an ArrayList.
     */
    private void split() {
        for (int i = 0; i < rawWord.length(); i++) {
            Letter letter = new Letter(rawWord.charAt(i));
            word.add(letter);
        }
    }

}