package DISNY;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class Word_Completion {
	private static TreeSet<String> dictionary;

	public void Word_Completor(String prefix) throws FileNotFoundException {
		dictionary = new TreeSet<>(); // Initialize the TreeSet
		try (Scanner sc = new Scanner(
				new FileReader("/Users/yashpatel/eclipse-workspace/FlightPriceAnalysis/src/DISNY/dictionary.txt"))) {
			while (sc.hasNext()) {
				String w = sc.next().toLowerCase();
				dictionary.add(w); // Add word to the dictionary
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error reading dictionary file: " + e.getMessage());
			System.exit(1);
		}

		TreeSet<String> completions = getCompletions(prefix);
		System.out.println("\nCompletions for Word \"" + prefix + "\":");
		
		for (String completion : completions) {
			System.out.println(completion);
		}
	}

	public static TreeSet<String> getCompletions(String prefix) {
		TreeSet<String> completions = new TreeSet<>();
		String lowerPrefix = prefix.toLowerCase();
		String upperPrefix = prefix.toUpperCase();
		for (String word : dictionary) {
			if (word.toLowerCase().startsWith(lowerPrefix)) {
				completions.add(word);
			}
		}
		System.out.println("");
		return completions;
	}
}