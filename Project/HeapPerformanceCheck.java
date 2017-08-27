import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class HeapPerformanceCheck {
	public static void main(String[] args) {
		String filename = args[0];
		HeapPerformanceCheck heapPerformaceCheck = new HeapPerformanceCheck();
		InputReader ip = new InputReader();
		Vector<Integer> input = ip.readInputFile(filename);
		HashMap<Integer, Integer> hash = ip.generateFrequency(input);
		
		//binary heap
		long start = System.currentTimeMillis();
		for(int i=0;i<10;i++){
			heapPerformaceCheck.buildHuffmanBinaryHeap(hash);
		}
		System.out.println("Time using binary heap (millisecond):" + (System.currentTimeMillis() - start)/10);
		
		//4-way cache heap
		start = System.currentTimeMillis();
		for(int i=0;i<10;i++){
			heapPerformaceCheck.buildHuffmanFourCache(hash);
		}
		System.out.println("Time using 4-way heap (millisecond):" + (System.currentTimeMillis() - start)/10);
		
		//pairing heap
		start = System.currentTimeMillis();
		for(int i=0;i<10;i++){
			heapPerformaceCheck.buildHuffmanUsingPairingHeap(hash);
		}
		System.out.println("Time using pairing heap (millisecond):" + (System.currentTimeMillis() - start)/10);
	}

	//Method to build Huffman Tree Using Binary Heap
	public BinaryNode buildHuffmanBinaryHeap(HashMap<Integer, Integer> hash){
		BinaryHeap heap = new BinaryHeap(hash.size());
		for(Map.Entry<Integer, Integer> entry : hash.entrySet()){
			BinaryNode node = new BinaryNode(entry.getKey(), entry.getValue(), null, null);
			heap.insert(node);
		}
		
		while(heap.count > 1){
			BinaryNode node1 = heap.extractMin();
			BinaryNode node2 = heap.extractMin();
			heap.insert(new BinaryNode(-1, node1.frequency + node2.frequency, 
					node1.frequency < node2.frequency ? node1:node2, node1.frequency < node2.frequency ? node2:node1));
		}
		BinaryNode  root = heap.extractMin();
		return root;
	}	

	//Method to build Huffman Tree Using 4-way Cache Heap	
	public BinaryNode buildHuffmanFourCache(HashMap<Integer, Integer> hash){
		FourCacheHeap heap = new FourCacheHeap(hash.size());
		for(Map.Entry<Integer, Integer> entry : hash.entrySet()){
			BinaryNode node = new BinaryNode(entry.getKey(), entry.getValue(), null, null);
			heap.insert(node);
		}
		
		while(heap.count > 1){
			BinaryNode node1 = heap.extractMin();
			BinaryNode node2 = heap.extractMin();
			heap.insert(new BinaryNode(-1, node1.frequency + node2.frequency, 
					node1.frequency < node2.frequency ? node1:node2, node1.frequency < node2.frequency ? node2:node1));
		}
		BinaryNode  root = heap.extractMin();
		return root;
	}	

	//Method to build Huffman Tree Using Pairing Heap	
	public BinaryNode buildHuffmanUsingPairingHeap(HashMap<Integer, Integer> hash){
		PairingHeap pairingHeap = new PairingHeap();
		for(Map.Entry<Integer, Integer> entry : hash.entrySet()){
			BinaryNode node = new BinaryNode(entry.getKey(), entry.getValue(), null, null);
			pairingHeap.insert(node);
		}
		
		while(!pairingHeap.isEmpty()){
			BinaryNode node1 = pairingHeap.extractMin();
			BinaryNode node2 = pairingHeap.extractMin();
			if(node1 !=null && node2!=null){
				pairingHeap.insert(new BinaryNode(-1, node1.frequency + node2.frequency, 
						node1.frequency < node2.frequency ? node1:node2, node1.frequency < node2.frequency ? node2:node1));				
			}else{
				if(node1==null){
					return node2;
				}else{
					return node1;
				}
			}
		}
		return null;
	}	
	
}
