package com.nigorojr.typebest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * This class handles selecting words depending on the mode.
 * 
 * @author Naoki Mizuno
 * 
 */

public class WordSelector {
    /* Modes */
    public static final int NORMAL = 0;
    public static final int LETTERS = 1;
    public static final int ENDLESS = 2;
    public static final int PROGRAMMER = 3;
    public static final int CUSTOM = 4;

    /* Default dictionary file locations */
    public static final String DEFAULT_DICT_NORMAL = "dic/Word1500.txt";
    public static final String DEFAULT_DICT_A_F = "dic/WordA-F.txt";
    public static final String DEFAULT_DICT_G_P = "dic/WordG-P.txt";
    public static final String DEFAULT_DICT_Q_Z = "dic/WordQ-Z.txt";
    public static final String DEFAULT_DICT_PROGRAMMER = "dic/Programmer.txt";

    public static final int DEFAULT_NUM_OF_LETTERS = 400;

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     */
    public static ArrayList<String> getWords(int mode) {
        return getWords(mode, DEFAULT_NUM_OF_LETTERS);
    }

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     * @param numOfLetters
     *            The total number of letters that needs to be typed. This
     *            number includes spaces.
     */
    public static ArrayList<String> getWords(int mode, int numOfLetters) {
        switch (mode) {
            case NORMAL:
                return getWords(mode, numOfLetters, DEFAULT_DICT_NORMAL);
            case LETTERS:
                // TODO
                String[] locations = {};
                return getWords(mode, numOfLetters, locations);
            case ENDLESS:
                // TODO
                String[] endlesslocations = {};
                return getWords(mode, numOfLetters, endlesslocations);
            case PROGRAMMER:
                return getWords(mode, numOfLetters, DEFAULT_DICT_PROGRAMMER);
            default:
                // TODO: exception handling
                return null;
        }
    }

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     * @param numOfLetters
     *            The total number of letters that needs to be typed. This
     *            number includes spaces.
     * @param fileLocation
     *            The location of the dictionary file.
     */
    public static ArrayList<String> getWords(int mode, int numOfLetters,
            String fileLocation) {
        return getWords(mode, numOfLetters, new String[] { fileLocation });
    }

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     * @param numOfLetters
     *            The total number of letters that needs to be typed. This
     *            number includes spaces.
     * @param fileLocations
     *            An array of path to the dictionary files. Each path can be
     *            either absolute path or relative path.
     */
    public static ArrayList<String> getWords(int mode, int numOfLetters,
            String[] fileLocations) {
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> allWords = new ArrayList<String>();
        Random rand = new Random();

        try {
            for (String fileLocation : fileLocations) {
                File dictionaryFile = new File(fileLocation);
                Scanner scanner = new Scanner(dictionaryFile);

                // Read everything from the files
                while (scanner.hasNext())
                    allWords.add(scanner.nextLine());
            }

            int letterCount = 0;
            while (letterCount < numOfLetters) {
                int random = rand.nextInt(allWords.size());

                // Add the word to the ArrayList and increment letter count
                words.add(allWords.get(random));
                letterCount += allWords.get(random).length();

                // Add 1 more taking account of the space character
                if (letterCount + 1 < numOfLetters)
                    letterCount++;
            }
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Dictionary file was not found!", "Missing File",
                    JOptionPane.ERROR_MESSAGE);
        }

        return words;
    }
}