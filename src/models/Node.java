package models;



public class Node implements Comparable<Node>{
	private int weight;
	private char nodeCharacter;
	private Node left;
	private Node right;
	
	public Node(int weight,char nodeCharacter,Node left,Node right){
		this.weight = weight;
		this.nodeCharacter = nodeCharacter;
		this.setLeft(left);
		this.setRight(right);
	}
	
	//use this for making huffman tree
	public Node(Node left,Node right){
		this.weight = left.getWeight() + right.getWeight();
		this.setLeft(left);
		this.setRight(right);
		this.nodeCharacter = 0;
	}
	
	public Node (Node node){
		this.weight = node.weight;
		this.setLeft(node.left);
		this.setRight(node.right);
		this.nodeCharacter = 0;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public char getNodeCharacter(){
		return nodeCharacter;
	}

	@Override
	public String toString() {
		return "Node [weight=" + weight + ", nodeCharacter=" + nodeCharacter
				+ "]";
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	@Override
	public int compareTo(Node that) {
		 if(this.weight > that.weight)return +1;
		 if(this.weight < that.weight)return -1;
		 return 0;
	}

	
}
