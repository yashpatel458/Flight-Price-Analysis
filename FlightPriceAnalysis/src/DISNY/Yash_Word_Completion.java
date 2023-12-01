package DISNY;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Yash_Word_Completion {
    private TrieNode root = new TrieNode();
  
    public void Word_Completor(String prefix) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileReader("/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/dictionary.txt"))) 
        {
            while (sc.hasNext()) {
                String w = sc.next().toLowerCase();
                insertWord(w);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
            System.exit(1);
        }

        TrieNode prefixNode = searchPrefix(prefix.toLowerCase());
        if (prefixNode != null) {
        	System.out.print("\n-----Word Completion-----");
            System.out.println("\nCompletions for the word \"" + prefix + "\" are...");
            printCompletions(prefixNode, prefix);
        } else {
            System.out.println("\nNo completions found for \"" + prefix + "\"");
        }
    }

    private void insertWord(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
        	int index = (ch >= 'a' && ch <= 'z') ? ch - 'a' : (ch >= 'A' && ch <= 'Z') ? ch - 'A' + 26 : -1;
            if (index >= 0 && index < 52) {
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
        }
        node.setEndOfWord(true);
    }

    private TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
        	int index = (ch >= 'a' && ch <= 'z') ? ch - 'a' : (ch >= 'A' && ch <= 'Z') ? ch - 'A' + 26 : -1;
            if (index >= 0 && index < 52 && node.children[index] != null) {
                node = node.children[index];
            } else {
                return null;
            }
        }
        return node;
    }

    private void printCompletions(TrieNode node, String prefix) {
        if (node.isEndOfWord()) {
            System.out.println(prefix);
        }

        for (char ch : node.getChildren()) {
            printCompletions(node.getChild(ch), prefix + ch);
        }
    }

    private static class TrieNode {
        private TrieNode[] children = new TrieNode[52];
        private boolean isEndOfWord;

        public boolean contains(char ch) {
            int index = (ch >= 'a' && ch <= 'z') ? ch - 'a' : (ch >= 'A' && ch <= 'Z') ? ch - 'A' + 26 : -1;
            return index >= 0 && index < 52 && children[index] != null;
        }

        public void addChild(char ch) {
            int index = (ch >= 'a' && ch <= 'z') ? ch - 'a' : (ch >= 'A' && ch <= 'Z') ? ch - 'A' + 26 : -1;
            if (index >= 0 && index < 52) {
                children[index] = new TrieNode();
            }
        }

        public TrieNode getChild(char ch) {
        	int index = (ch >= 'a' && ch <= 'z') ? ch - 'a' : (ch >= 'A' && ch <= 'Z') ? ch - 'A' + 26 : -1;
            return (index >= 0 && index < 52) ? children[index] : null;
        }

        public char[] getChildren() {
            StringBuilder chars = new StringBuilder();
            for (int i = 0; i < 52; i++) {
                if (children[i] != null) {
                    chars.append((i < 26) ? (char) ('a' + i) : (char) ('A' + i - 26));
                }
            }
            return chars.toString().toCharArray();
        }

        public boolean isEndOfWord() {
            return isEndOfWord;
        }

        public void setEndOfWord(boolean endOfWord) {
            isEndOfWord = endOfWord;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Yash_Word_Completion wordCompletion = new Yash_Word_Completion();
        wordCompletion.Word_Completor("pre");
    }
}
