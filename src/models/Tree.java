package models;

import java.util.ArrayList;

public class Tree {
	private int weight;
	private char nodeCharacter;
	private ArrayList<Character> tree;
	
	public Tree(int weight,char nodeCharacter){
		this.weight = weight;
		this.tree = null;
		this.nodeCharacter = nodeCharacter;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public char getNodeCharacter(){
		return nodeCharacter;
	}

	@Override
	public String toString() {
		return "Tree [weight=" + weight + ", nodeCharacter=" + nodeCharacter
				+ "]";
	}
	
	
}
