package DISNY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Yash_Spellchecking {
    
	private Set<String> dictionary;
    
    // The constructor initializes the dictionary by reading words from a file (cities.txt) and adding them to the HashSet.
    public Yash_Spellchecking() throws FileNotFoundException {
        dictionary = new HashSet<>();  // Initialize the HashSet        
        Scanner sc = new Scanner(new File("/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/cities.txt"));
        while (sc.hasNext()) {
            String w = sc.nextLine().trim().toLowerCase();
            //System.out.println(w);
            dictionary.add(w);  // Add word to the dictionary
        }
        sc.close();
    }

    // This method checks if a given word exists in the dictionary (HashSet).
    public boolean probe(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    // This method suggests corrections for a misspelled word by calculating the edit distance between the misspelled word and each word in the dictionary.
    // It returns a list of recommended corrections.
    
    public List<String> recommendations(String testWord) {
           List<String> recommendationArrayList = new ArrayList<>();
           //String testWord = t.trim();
        if (dictionary.contains(testWord)) {
            return recommendationArrayList;
        }

       // int minDistance = Integer.MAX_VALUE;
        
      int minDistance = 3;

       for (String correctWord : dictionary) {
            
                int dist = computeEditDistance(correctWord, testWord);
                if (dist < minDistance) {
                    minDistance = dist;
                    recommendationArrayList.clear(); // Clear previous recommendations
                    recommendationArrayList.add(correctWord);
                } else if (dist == minDistance) {
                    recommendationArrayList.add(correctWord);
                }       
        }

        return recommendationArrayList;
    }

    // This method calculates the edit distance between two strings using dynamic programming.
    private int computeEditDistance(String s, String t) {
        int k = s.length();
        int l = t.length();
        int[][] jp = new int[k + 1][l + 1];
        for (int y = 0; y <= k; y++) {
            jp[y][0] = y;
        }
        for (int j = 0; j <= l; j++) {
            jp[0][j] = j;
        }
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= l; j++) {
                if (Character.toLowerCase(s.charAt(i - 1)) == Character.toLowerCase(t.charAt(j - 1))) {
                    jp[i][j] = jp[i - 1][j - 1];
                } else {
                    jp[i][j] = 1 + Math.min(jp[i - 1][j], Math.min(jp[i][j - 1], jp[i - 1][j - 1]));
                }
            }
        }
        return jp[k][l];
    }

    //    This method performs spell checking on a given word.
    //    If the word is non-alphabetic, it displays an error message.
    //    If the word is in the dictionary, it indicates that the spelling is correct.
    //    If the word is not in the dictionary, it suggests corrections based on the edit distance.
    
     public boolean checkandSuggestWords(String w) throws FileNotFoundException {
        Yash_Spellchecking spellchecker = new Yash_Spellchecking();

        if (!w.matches("[a-zA-Z ]+")) {
            // Display an error message for non-alphabetic input
            System.out.println("\n-----Spell Checking-----");
            System.out.println("\nError: Please enter a valid city (containing only letters).\n");
            return false;
        }

        if (spellchecker.probe(w)) {
            System.out.println("\n-----Spell Checking-----");
            System.out.println("\nThe spelling of this city is correct!");
            return true;
        } else {
            List<String> recommendationArrayList = spellchecker.recommendations(w);
            if (recommendationArrayList.isEmpty()) {
                System.out.println("\n-----Spell Checking-----");
                System.out.println("\nThe entered word is not spelled correctly and there are no recommendations for correction.\n");
            } else {
                System.out.println("\n-----Spell Checking-----");
                System.out.println("\nThe entered word is not spelled correctly. Here is a recommendation based on the search:\n");
                for (String suggestion : recommendationArrayList) {
                    System.out.println("- " + suggestion);
                }
                System.out.println("");
            }
            return false;
        }
    }
}
