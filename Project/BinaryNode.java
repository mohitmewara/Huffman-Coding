

public class BinaryNode {
	public int number;
	public int frequency;
	public BinaryNode left;
	public BinaryNode right;
	
	public BinaryNode(){
		this.number = -1;
		this.frequency = 0;
		this.left = null;
		this.right = null;
	}
	
	public BinaryNode(int number, int frequency, BinaryNode left, BinaryNode right){
		this.number = number;
		this.frequency = frequency;
		this.left = left;
		this.right = right;
	}
	
}
