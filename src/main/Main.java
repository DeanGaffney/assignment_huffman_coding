package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Node;
import utils.HuffmanCodingTree;
import utils.TestString;

public class Main {

	public static void main(String[] args) {
		HuffmanCodingTree huffmanCodingTree = new HuffmanCodingTree();
		TestString testString = new TestString("this is a test string");
		char[] charArray = testString.getTestString().toCharArray();
		System.out.println(charArray);

		//make single nodes first.
		makeTrees(testString,charArray,huffmanCodingTree);
		for(int i = 0; i < huffmanCodingTree.getHuffmanCodingTree().size();i++)System.out.println(huffmanCodingTree.getHuffmanCodingTree().get(i).toString());
		Collections.sort(huffmanCodingTree.getHuffmanCodingTree(),Collections.reverseOrder());
		System.out.println();
		for(int i = 0; i < huffmanCodingTree.getHuffmanCodingTree().size();i++)System.out.println(huffmanCodingTree.getHuffmanCodingTree().get(i).toString());
		System.out.println("Compressing called....");
		String compressed = huffmanCodingTree.compress(testString.getTestString());
		System.out.println("Compression bit size:" + compressed.length());
		System.out.println(compressed);
		System.out.println();
		System.out.println("Decompressing called....");
		String decompressed = huffmanCodingTree.decompress(compressed);
		System.out.println(decompressed);
	}

	//searches for similar letters and makes a single tree out of the nodes.
	private static void makeTrees(TestString testString,char[] charArray,HuffmanCodingTree huffmanCodingTree){
		ArrayList<Character> letters = new ArrayList<Character>();
		for(int i = 0; i < charArray.length;i++){
			if(!letters.contains(charArray[i])){ // if it doesn't contain it its a new letter
				int letterCount = countLetterOccurence(charArray[i],charArray);
				letters.add(charArray[i]); // add it to the array.
				huffmanCodingTree.getHuffmanCodingTree().add(new Node(letterCount,charArray[i], null, null));
			}
		}
	}

	//counts the amount of times a letter has occurred.
	private static int countLetterOccurence(char letter,char[] charArray){
		int letterCount = 0;
		for(int i = 0; i < charArray.length;i++){
			if(letter == charArray[i])letterCount++;
		}
		return letterCount;
	}


}
