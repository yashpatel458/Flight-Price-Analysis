package DISNY;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Search_Frequency {

    private static final String CSV_FILE_PATH = "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/Search_Frequency/searchFrequency.csv";

    public String Search_Frequency(String checkWord) throws Exception {
    	Yash_Word_Completion WC = new Yash_Word_Completion();
    	Yash_Spellchecking spellchecker = new Yash_Spellchecking();
        String searchTerm = checkWord;
		boolean check = spellchecker.checkandSuggestWords(searchTerm);
		Scanner scan = new Scanner(System.in);
		while(check == false)
		{
        	System.out.print("\nEnter again : ");
			searchTerm = scan.nextLine().toLowerCase();
			WC.Word_Completor(searchTerm);
			check = spellchecker.checkandSuggestWords(searchTerm);
		}
//    
        Map<String, Integer> wordFreq = loadWordFrequency();
                
//      String searchTerm = spellchecker.checkandSuggestWords();
      searchTerm = searchTerm.toLowerCase();
      int frequency = wordFreq.getOrDefault(searchTerm, 0);
      System.out.print("\n-----Frequency Count-----");
      System.out.println("\n"+searchTerm + " word was searched for " + frequency + " times.");
      wordFreq.put(searchTerm, frequency + 1);
      saveWordFrequency(wordFreq);
		return searchTerm;
              
    }

    private static Map<String, Integer> loadWordFrequency() throws IOException {
        Map<String, Integer> wordFreq = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String word = parts[0];
                int frequency = Integer.parseInt(parts[1]);
                wordFreq.put(word, frequency);
            }
        }
        catch (Exception e)
        {
			System.out.println("");
		}
        return wordFreq;
    }

    private static void saveWordFrequency(Map<String, Integer> wordFreq) throws IOException {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
                String word = entry.getKey();
                int frequency = entry.getValue();
                writer.append(word).append(",").append(Integer.toString(frequency)).append("\n");
            }
        }
    }

    private static String getSearchTerm() {
        Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a term to search: ");
		String word = scanner.next(); 
		return word;
		
    }
}
