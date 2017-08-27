


public class PairingHeap {
	private PairingNode root;
	private PairingNode[] elementsArray = new PairingNode[5];
	
	public PairingHeap(){
		root = null;
	}
	
	public boolean isEmpty(){
		return root == null;
	}
	
	public void insert(BinaryNode node){
		PairingNode pairingNode = new PairingNode(node);
		if(root == null){
			root = pairingNode;
		}else{
			root = compareAndAddNodes(root, pairingNode);
		}
	}
	
	public BinaryNode extractMin(){
		if(root==null){
			return null;
		}
		BinaryNode node = root.node;
		if(root.leftChild == null){
			root = null;
		}else{
//			root = combineNodes(root.leftChild);
			root = combineSiblings(root.leftChild);
		}
		return node;
	}
	
	public PairingNode compareAndAddNodes(PairingNode first, PairingNode second){
		if(first == null && second == null){
			return null;
		}
		if(second == null){
			return first;
		}
		if(second.node.frequency < first.node.frequency){
			second.prev = first.prev;
			first.prev = second;
			first.next = second.leftChild;
			if (first.next != null)
				first.next.prev = first;
			second.leftChild = first;
			return second;
		} else {
			second.prev = first;
			first.next = second.next;
			if (first.next != null)
				first.next.prev = first;
			second.next = first.leftChild;
			if (second.next != null)
				second.next.prev = second;
			first.leftChild = second;
			return first;
		}
	}

	private PairingNode combineSiblings(PairingNode firstSibling) {
		if (firstSibling.next == null)
			return firstSibling;
		int numSiblings = 0;
		for (; firstSibling != null; numSiblings++) {
			elementsArray = doubleArray(elementsArray, numSiblings);
			elementsArray[numSiblings] = firstSibling;
			firstSibling.prev.next = null;
			firstSibling = firstSibling.next;
		}
		elementsArray = doubleArray(elementsArray, numSiblings);
		elementsArray[numSiblings] = null;
		int i = 0;
		for (; i + 1 < numSiblings; i += 2)
			elementsArray[i] = compareAndAddNodes(elementsArray[i], elementsArray[i + 1]);
		int j = i - 2;
		if (j == numSiblings - 3)
			elementsArray[j] = compareAndAddNodes(elementsArray[j], elementsArray[j + 2]);
		for (; j >= 2; j -= 2)
			elementsArray[j - 2] = compareAndAddNodes(elementsArray[j - 2], elementsArray[j]);
		return elementsArray[0];
	}

	private PairingNode[] doubleArray(PairingNode[] array, int index) {
		if (index == array.length) {
			PairingNode[] oldArray = array;
			array = new PairingNode[index * 2];
			for (int i = 0; i < index; i++)
				array[i] = oldArray[i];
		}
		return array;
	}	
	
}
