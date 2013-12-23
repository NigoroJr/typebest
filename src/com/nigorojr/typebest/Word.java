package com.nigorojr.typebest;

import javax.swing.JLabel;

public class Word extends JLabel {
	private JLabel wordLabel = new JLabel();
	private String rawWord;
	
	/**
	 * Accepts a String which is what will be shown in this JLabel.
	 * The constructor will then split the word into letters and put them into
	 * individual JLabels.
	 * @param word
	 */
	public Word(String word) {
		rawWord = word;
		
		split();
	}
	
	private void split() {
		for (int i = 0; i < rawWord.length(); i++) {
			JLabel letter = new JLabel(Character.toString(rawWord.charAt(i)));
			wordLabel.add(letter);
		}
	}
}