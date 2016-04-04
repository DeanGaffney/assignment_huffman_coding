package utils;
import java.util.ArrayList;

import models.Tree;

public class HuffmanCodingTree {
	private int weight;
	private ArrayList<Tree> huffmanCodingTree;
	
	public HuffmanCodingTree(){
		this.weight = 0;
		huffmanCodingTree = new ArrayList<Tree>();
	}
	
	public void addTree(Tree tree){
		huffmanCodingTree.add(tree);
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public ArrayList<Tree> getHuffmanCodingTree(){
		return huffmanCodingTree;
	}
}
