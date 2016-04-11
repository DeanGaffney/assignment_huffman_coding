package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import models.Node;
import utils.HuffmanCodingTree;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		String file = new Scanner(new File("data/test.txt")).useDelimiter("\\A").next(); //used to read in all file data as string.
		HuffmanCodingTree huffmanCodingTree = new HuffmanCodingTree();
		//TestString testString = new TestString("this is a test string");
		char[] charArray = file.toCharArray();
		System.out.println(charArray);

		//make single nodes first.
		makeTrees(file,charArray,huffmanCodingTree);
		for(int i = 0; i < huffmanCodingTree.getHuffmanCodingTree().size();i++)System.out.println(huffmanCodingTree.getHuffmanCodingTree().get(i).toString());
		Collections.sort(huffmanCodingTree.getHuffmanCodingTree(),Collections.reverseOrder());
		System.out.println();
		for(int i = 0; i < huffmanCodingTree.getHuffmanCodingTree().size();i++)System.out.println(huffmanCodingTree.getHuffmanCodingTree().get(i).toString());
		
		
		//compression/coding
		System.out.println("Compressing called....");
		//compresses file and adds EOF character if needed.
		String compressed = huffmanCodingTree.compress(file);
		System.out.println("Compression bit size:" + compressed.length());
		System.out.println(compressed + "\n");
		int originalCompressionSize = huffmanCodingTree.getOriginalBitSize(); // need original compression to know EOF.
		
		//decompression
		String decompressed = huffmanCodingTree.decompress(compressed,originalCompressionSize);
		System.out.println(decompressed + "\n");
		PrintWriter out  = new PrintWriter("output.txt","UTF-8");
		out.println(decompressed);
		out.close();
		String fileContents = new Scanner(new File("output.txt")).useDelimiter("\\A").next();
	
		System.out.println("This is the contents of the newly saved file:\n" + fileContents);
	}
	
	//searches for similar letters and makes a single tree out of the nodes.
	private static void makeTrees(String file,char[] charArray,HuffmanCodingTree huffmanCodingTree){
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
