package DISNY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Spellchecking 
{
	    private Set<String> dictionary;
	    
	    //If quick (checking if a word is in the dictionary) are a priority, a HashSet or TreeSet might be more suitable. 
	    //If you need to efficiently search for words with a common prefix, a Trie could be a good choice. 
	    //If you need to preserve insertion order or need positional access to elements, an ArrayList might be appropriate.
	    
	    public Spellchecking() throws FileNotFoundException {
	            dictionary = new HashSet<>();  // Initialize the HashSet
	            Scanner sc = new Scanner(new File("/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/dictionary.txt"));  
	            while (sc.hasNext()) {
	                String w = sc.next().toLowerCase();  
	                dictionary.add(w);  // Add word to the dictionary 
	            }
	            sc.close();  
	            
	    }
	    
	    public boolean probe(String word) {
	        return dictionary.contains(word.toLowerCase());  
	    }
	    
	    
	    // Recommends new word
	    public List<String> recommendations(String testWord) {
	        List<String> recommendationArrayList = new ArrayList<>();
	        for (String correctWord : dictionary) {
	        	int dist = computeEditDistance(correctWord, testWord);
	                if (dist > 0 && dist<3) {
	                	if (probeTranspositions(correctWord, testWord) || probeSubstitutions(correctWord, testWord) || probeInsertions(correctWord, testWord)||probeDeletions(correctWord, testWord)) {
	                		recommendationArrayList.add(correctWord);
	                        }
	                }
	             
	        }
	        return recommendationArrayList;
	    }
	    
	    

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
	            	 if (Character.toLowerCase(s.charAt(i - 1)) == Character.toLowerCase(t.charAt(j - 1))) {  // convert to lowercase
	                     jp[i][j] = jp[i - 1][j - 1];
	                } else {
	                    jp[i][j] = 1 + Math.min(jp[i - 1][j], Math.min(jp[i][j - 1], jp[i - 1][j - 1]));
	                }
	            }
	        }
	        return jp[k][l];
	    }

	    
	    private boolean probeTranspositions(String correctWord, String testWord) {
	      //  System.out.println(o+ "in trans");
	        if (correctWord.length() != testWord.length()) {
	            return false;
	        }
	        int n = correctWord.length();
	        int t = 0;
	        while (t < n-1) {
	            if (Character.toLowerCase(correctWord.charAt(t)) == Character.toLowerCase(testWord.charAt(t+1)) && correctWord.charAt(t+1) == testWord.charAt(t)) {
	                return true;
	            }
	            t += 1;
	        }
	      //  System.out.println("IN TRANS");
	        return false;
	    }
	    
	    private boolean probeSubstitutions(String correctWord, String testWord) {
	     //   System.out.println(correctWord + "in subs");

	        if (correctWord.length() != testWord.length()) {
	            return false;
	        }
	        int n = correctWord.length();
	        int sum = 0;
	        int i = 0;
	        while (i < n) {
	        	 if (Character.toLowerCase(correctWord.charAt(i)) != Character.toLowerCase(testWord.charAt(i))) { // convert to lowercase
	                 sum++;
	             }
	            if (sum > 2) {
	                return false;
	            }
	            i += 1;
	        }
	     //   System.out.println("IN SUBS");

	        return sum >= 1 ;
	    }

	    private boolean probeDeletions(String correctWord, String testWord) {
	        if (correctWord.length() < testWord.length() - 2) {
	            return false;
	        }
	        int n = correctWord.length();
	        int m = testWord.length();
	        int i = 0, j = 0, deletions = 0;
	        while (i < n && j < m) {
	            if (Character.toLowerCase(correctWord.charAt(i)) != Character.toLowerCase(testWord.charAt(j))) {
	                if (deletions < 2) {
	                    j++; // insert a character
	                    deletions++;
	                } else if (i != j) {
	                    return false;
	                } else {
	                    i++; // delete a character
	                }
	            } else {
	                i++;
	                j++;
	            }
	        }
	        // handle any remaining operations
	        while (j < m && deletions < 2) {
	            j++;
	            deletions++;
	        }
	        return i == n && j == m;
	    }


	    private boolean probeInsertions(String correctWord, String testWord) {
	        if (correctWord.length() > testWord.length() + 2) {
	            return false;
	        }
	        int n = testWord.length();
	        int m = correctWord.length();
	        int i = 0, j = 0, insertions = 0;
	        while (i < n && j < m) {
	            if (Character.toLowerCase(correctWord.charAt(j)) != Character.toLowerCase(testWord.charAt(i))) {
	                if (insertions < 2) {
	                    j++; // insert a character
	                    insertions++;
	                } else if (i != j) {
	                    return false;
	                } else {
	                    i++; // delete a character
	                }
	            } else {
	                i++;
	                j++;
	            }
	        }
	        // handle any remaining insertions
	        while (j < m && insertions < 2) {
	            j++;
	            insertions++;
	        }
	        return i == n && j == m;
	    }

	    
	    public boolean checkandSuggestWords(String w) throws FileNotFoundException {

	        Spellchecking spellchecker = new Spellchecking();
	        
	        if (spellchecker.probe(w)) {
	            System.out.println("The spelling of the word is correct");
	            return true;
	        } else {
	            List<String> recommendationArrayList = spellchecker.recommendations(w);
	            if (recommendationArrayList.isEmpty()) {
	                System.out.println("\nThe word is not spelled correctly and there are no recommendation for correction.\n");
	            } else {
	                System.out.println("\nThe word is not spelled correctly. recommendation for correction:\n");
	                for (String suggestion : recommendationArrayList) {
	                    System.out.println("- " + suggestion);
	                }
	                System.out.println("");
	            }
	            return false;
	        }
//	        sc.close();
	    }
}
