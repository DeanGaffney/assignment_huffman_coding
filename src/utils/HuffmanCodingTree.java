package utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import models.Node;

public class HuffmanCodingTree {
	private int weight;
	private ArrayList<Node> huffmanCodingTree;
	private PriorityQueue<Node> nodeQueue;
	private HashMap<Character,String> characterToCode;
	private HashMap<String,Character> codeToCharacter;
	
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
	
	public String compress(String file){
		for(Node node : huffmanCodingTree)nodeQueue.add(node); //adds all nodes from array list to our priority queue.
		//get two smallest weight sizes and add them together
		Node root = new Node(createSingleTree());
		System.out.println(nodeQueue.size());
		createEncodings(root,new String());
		 String compressed = showCompression(file);
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
	
	public String decompress(String compressedFile){
		String temp = "";
		String decompressed = "";
		
		for(int i = 0; i < compressedFile.length();i++){
			temp+=compressedFile.charAt(i);
			if(codeToCharacter.containsKey(temp)){
				decompressed+=codeToCharacter.get(temp);
				temp = "";
			}
		}
		return decompressed;
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
		
		if(root.getNodeCharacter() != 0){ //chars are checked with integers for null
			System.out.println("\'" + root.getNodeCharacter() + "\'" + " -----> " + code);
			characterToCode.put(root.getNodeCharacter(), code);
			codeToCharacter.put(code,root.getNodeCharacter());
		}
	}
	
	class NodeComparator implements Comparator<Node>{
		@Override
		public int compare(Node node1, Node node2) {
			return node1.getWeight() - node2.getWeight();
		}
	}
}
