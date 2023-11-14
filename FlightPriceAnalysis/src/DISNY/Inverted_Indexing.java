package DISNY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class Inverted_Indexing {

    // trie data structure to store the keys
    private TrieNode root;

    // a list of documents to be indexed
    private List<String> documents;

    // two-dimensional array to store the index
    private int[][] index;

    public Inverted_Indexing(List<String> documents) {
        this.documents = documents;
        this.index = new int[documents.size()][];
        this.root = new TrieNode();
        buildIndex();
    }

    // method to build the inverted index
    private void buildIndex() {
        for (int i = 0; i < documents.size(); i++) {
            String[] words = documents.get(i).split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    addIndex(word.toLowerCase(), i);
                }
            }
        }
    }

    // method to add the index to the trie data structure
    private void addIndex(String word, int documentId) {
        TrieNode node = root;
        for (char CH : word.toCharArray()) {
            node = node.getChildren().computeIfAbsent(CH, k -> new TrieNode());
        }
        if (node.getIndexes() == null) {
            node.setIndexes(new ArrayList<>());
        }
        node.getIndexes().add(documentId);
    }

    // method to search the index
    public List<Integer> searchIndex(String word) {
        TrieNode node = root;
        for (char CH : word.toCharArray()) {
            node = node.getChildren().get(CH);
            if (node == null) {
                return null;
            }
        }
        return node.getIndexes();
    }

    // trie node class
    private static class TrieNode {
        private Map<Character, TrieNode> children = new HashMap<>();
        private List<Integer> indexes;

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public List<Integer> getIndexes() {
            return indexes;
        }

        public void setIndexes(List<Integer> indexes) {
            this.indexes = indexes;
        }
    }
}
