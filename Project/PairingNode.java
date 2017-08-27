

public class PairingNode {
	public BinaryNode node;
	public PairingNode leftChild;
	public PairingNode next;
	public PairingNode prev;
	
	public PairingNode(BinaryNode node){
		this.node=node;
		leftChild=null;
		next=null;
		prev=null;
	}
}
