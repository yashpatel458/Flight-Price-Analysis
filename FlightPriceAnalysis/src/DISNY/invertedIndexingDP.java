package DISNY;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class invertedIndexingDP {

	public static class TreeNode {
		String word;
		int frequency;
		TreeNode left, right;

		public TreeNode() {
			this.word = null;
			this.frequency = 0;
			this.left = this.right = null;
		}

		public void insert(String newWord) {
			if (word == null) {
				// If the node is empty, set the word and initialize frequency
				this.word = newWord;
				this.frequency = 1;
			} else {
				int compareResult = newWord.compareTo(this.word);
				if (compareResult < 0) {
					if (this.left == null) {
						this.left = new TreeNode();
					}
					this.left.insert(newWord);
				} else if (compareResult > 0) {
					if (this.right == null) {
						this.right = new TreeNode();
					}
					this.right.insert(newWord);
				} else {
					// Word already exists in the tree, increase frequency
					this.frequency++;
				}
			}
		}
	}

	private static class AVLNode {
		AVLNode left, right;
		int height;
		String key;
		AVLIndex index;
		String fileName;

		AVLNode(String key, int pageIndex, int position,String fileName) {
			this.key = key;
			this.height = 1;
			this.index = new AVLIndex(pageIndex, position);
			this.fileName = fileName;
		}
	}

	private static class AVLIndex {
		int pageIndex;
		int position;

		AVLIndex(int pageIndex, int position) {
			this.pageIndex = pageIndex;
			this.position = position;
		}
	}

	private AVLNode root;

	public void buildIndex(File file) {

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int pageIndex = 0;
			int position = 0;
			while ((line = br.readLine()) != null) {
				String[] words = line.split("\\s+");
				for (String word : words) {
					// Add the position of the word in the file to the AVL tree
					root = insert(root, word.toLowerCase(), pageIndex, position++, file.getName());
					//System.out.println(file.getName());
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	private AVLNode insert(AVLNode node, String word, int pageIndex, int position,String fileName) {
		if (node == null) {
			return new AVLNode(word, pageIndex, position,fileName);
		}

		if (word.compareTo(node.key) < 0) {
			node.left = insert(node.left, word, pageIndex, position,fileName);
		} else if (word.compareTo(node.key) > 0) {
			node.right = insert(node.right, word, pageIndex, position,fileName);
		} else {
			// If the word is equal, move to the next word
			node.index.pageIndex = pageIndex;
			node.index.position = position;
			return node;
		}

		node.height = 1 + Math.max(height(node.left), height(node.right));

		int balance = getBalance(node);

		if (balance > 1 && word.compareTo(node.left.key) < 0) {
			return rightRotate(node);
		}

		if (balance < -1 && word.compareTo(node.right.key) > 0) {
			return leftRotate(node);
		}

		if (balance > 1 && word.compareTo(node.left.key) > 0) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		if (balance < -1 && word.compareTo(node.right.key) < 0) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		return node;
	}

	private int height(AVLNode node) {
		return (node != null) ? node.height : 0;
	}

	private int getBalance(AVLNode node) {
		return (node != null) ? height(node.left) - height(node.right) : 0;
	}

	private AVLNode rightRotate(AVLNode y) {
		AVLNode x = y.left;
		AVLNode T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		y.height = Math.max(height(y.left), height(y.right)) + 1;
		x.height = Math.max(height(x.left), height(x.right)) + 1;

		return x;
	}

	private AVLNode leftRotate(AVLNode x) {
		AVLNode y = x.right;
		AVLNode T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		x.height = Math.max(height(x.left), height(x.right)) + 1;
		y.height = Math.max(height(y.left), height(y.right)) + 1;

		return y;
	}

	public void searchKeyword(String keyword) {
		AVLNode node = search(root, keyword.toLowerCase());
		if (node == null) {
			System.out.println("Keyword not found: " + keyword);
			return;
		}

		System.out.println("Occurrences of keyword: " + keyword);
		printOccurrences(node);
	}

	private AVLNode search(AVLNode node, String keyword) {
		while (node != null) {
			int comparison = keyword.compareTo(node.key);
			if (comparison == 0) {
				return node;
			} else if (comparison < 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return null;
	}

	private void printOccurrences(AVLNode node) {
		if (node != null) {
			printOccurrences(node.left);
			System.out.println("pageIndex: " + node.index.pageIndex + ", Position: " + node.index.position +"  Filename: "+node.fileName);
			printOccurrences(node.right);
		}
	}
	
	public static void invertedFinal(String pathOfFolder, String key) {
		String saveDir = pathOfFolder;

		TreeNode root = new TreeNode();

		File folder = new File(saveDir);

		for(File f : folder.listFiles()) {

			invertedIndexingDP index = new invertedIndexingDP();
				
			if (f.isFile() && f.getName().endsWith(".txt")){
				System.out.println("--------------------------------------");
				String keyword = key;
				index.buildIndex(f);
				root.insert(keyword);
				index.searchKeyword(keyword);
			}
		}
	}

	

	public static void main(String args[]){
			invertedFinal("/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/DISNY/parsedFiles","price");
		}
}