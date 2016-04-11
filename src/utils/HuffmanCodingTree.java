package utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import models.Node;

public class HuffmanCodingTree {
	private int weight;
	private ArrayList<Node> huffmanCodingTree;
	private Node root;
	private PriorityQueue<Node> nodeQueue;
	private HashMap<Character,String> characterToCode;
	private HashMap<String,Character> codeToCharacter;
	private int originalCompressionSize;

	public HuffmanCodingTree(){
		this.weight = 0;
		huffmanCodingTree = new ArrayList<Node>();
		nodeQueue = new PriorityQueue<Node>(50,new NodeComparator());
		characterToCode = new HashMap<Character,String>();
		codeToCharacter = new HashMap<String,Character>();
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	public int getWeight(){
		return weight;
	}

	public ArrayList<Node> getHuffmanCodingTree(){
		return huffmanCodingTree;
	}

	/*
	 * This method creates a priority queue containing all nodes.
	 * The single tree/root node is then traversed left-right to create the coding for the file and characters.
	 * The method then returns a String containing the code for the file.
	 */
	public String compress(String file){
		for(Node node : huffmanCodingTree)nodeQueue.add(node);   //adds all nodes from array list to our priority queue.
		root = new Node(createSingleTree());					// create single tree node or root note
		System.out.println(nodeQueue.size());
		createEncodings(root,new String());						//create encodings from the single tree.
		String compressed;		
		compressed = (endOfFileCharactersNeeded(showCompression(file))) //add EOF if needed else return normal compression
				? makeEndOfFileCharacter(showCompression(file)) : showCompression(file);
		return compressed;
	}

	/*
	 * go through each character of the file and match it to the corresponding code
	 * in the HashMap thats related to that character. Then display it.
	 */
	public String showCompression(String file){
		String compressed = "";
		for(int i = 0;i < file.length();i++)compressed+=characterToCode.get(file.charAt(i));
		return compressed;
	}
	
	public int getOriginalBitSize(){
		System.out.println("Original compression size is:"+originalCompressionSize);
		return originalCompressionSize;
	}

	//method checks to see if the compressed bits are multiple of 8.
	public boolean endOfFileCharactersNeeded(String compressed){
		originalCompressionSize = compressed.length();
		int charactersNeeded = compressed.length() % 8;
		return (charactersNeeded == 0) ? false:true;
	}

	public void showOriginalBits(String compressed){
		System.out.println("EOF was needed");
		System.out.println("Compression size before EOF:" + compressed.length() + " bits");
	}
	
	
	
	//makes EOF character if needed. (It will already have been checked by previous method).
	public String makeEndOfFileCharacter(String compressed){
		String originalCompresionFile = compressed;
		showOriginalBits(originalCompresionFile);					//just run some log messages
		int remainder = compressed.length() % 8;		//get remainder and see how many chars needed.
		int charactersNeeded = 8 - remainder;
		String eof = "";
		for(int i = 0; i < charactersNeeded; i++){
			String str = "";
			str = (i % 2 == 0) ? "0" : "1";				//decide 0 or 1 from even or odd.
			compressed+=str;
			eof+=str;
		}
		//add to codeTOCharacter map
		System.out.println("EOF ------>" + eof);
		codeToCharacter.put(eof, '#');					//make the EOF character be '#' and add it to map
		return compressed;
	}
	
	/*
	 * By storing the original compression size I can then stop reading the bits
	 * after the original compression bit size has been met and can ignore the 
	 * extra bits needed to make the file 8 bit chunks.
	 */
	public String decompress(String compressedFile,int originalCompressionSize){
		String decompressed = "";							//result to return
		String code = "";									//temporary string to get each code
		
		for(int i = 0; i < originalCompressionSize;i++){	//loop through codings
			code+=compressedFile.charAt(i);					//create a code and check it against the hash map
			if(codeToCharacter.get(code) != null && codeToCharacter.get(code) == '#')break;		//if it's the EOF character break the loop decompression done.
			if(codeToCharacter.containsKey(code)){
				decompressed+=codeToCharacter.get(code);	//if code found,retrieve character,reset code string for next code.
				code = "";
			}
		}
		return decompressed;								//return the decompressed string
	}

	//creates a single tree out of the "forests" nodes that I started with.
	//loops through priority queue and removes lowest,adds together and makes new node.
	public Node createSingleTree(){
		while(nodeQueue.size() > 1){
			Node a = nodeQueue.poll();
			Node b = nodeQueue.poll();
			nodeQueue.add(new Node(a,b));
		}
		return nodeQueue.poll();
	}

	/*
	 * go through the tree and every time you go down a left path 
	 * add a 0 to your code, right path is 1. 
	 * Do until character is found. When found this is your encoding for the character.
	 * Post order traversal!
	 */
	public void createEncodings(Node root,String code){
		if(root == null)return;
		
		createEncodings(root.getLeft(),code+"0");
		createEncodings(root.getRight(),code+"1");

		if(root.getNodeCharacter() != 0){ //chars are checked with 0 for null
			System.out.println("\'" + root.getNodeCharacter() + "\'" + " -----> " + code);
			characterToCode.put(root.getNodeCharacter(), code);
			codeToCharacter.put(code,root.getNodeCharacter());
		}
	}

	//Compares Nodes for Priority Queue
	class NodeComparator implements Comparator<Node>{
		@Override
		public int compare(Node node1, Node node2) {
			return node1.getWeight() - node2.getWeight();
		}
	}
}
