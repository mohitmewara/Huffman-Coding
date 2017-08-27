

public class FourCacheHeap {
	public BinaryNode[] arr;
	public int count;
	public int capacity;
	public int degree=4;
	
	public FourCacheHeap(int cap){
		this.capacity = cap+3;
		this.count = 0;
		this.arr = new BinaryNode[capacity];
	}
	
	public void insert(BinaryNode node){
		if(count == capacity-3){
			resizeHeap();
		}
		int index = ++count+2;
		while(index >3 && node.frequency < arr[(index/4)+2].frequency){
			arr[index] = arr[(index/4)+2];
			index = (index/4)+2;
		}
		arr[index] = node;
	}
	
	public void resizeHeap(){
		BinaryNode[] temp = arr;
		arr = new BinaryNode[2*capacity];
		for(int i=0;i<temp.length;i++){
			arr[i] = temp[i];
		}
	}
	
	public BinaryNode getMin(){
		if(count == 0){
			return null;
		}
		return arr[0];
	}
	
	public BinaryNode extractMin(){
		if(count == 0){
			return null;
		}
		BinaryNode node = arr[3];
		arr[3] = arr[count+2];
		arr[count+2] = null;
		count--;
		heapifyDown(1);
		return node;
	}
	
	public void heapifyDown(int index){
		int child;
		BinaryNode temp = arr[index+2];
		while((4*index) <= count+2){
			child = 4*index;
			int minChild = child;
			int value = arr[child].frequency;
			if(child < count+2 && arr[child+1].frequency < arr[child].frequency){
				minChild = child+1;
				value = arr[child+1].frequency;
			}
			if(child+1 < count+2 && arr[child+2].frequency < value){
				minChild = child+2;
				value = arr[child+2].frequency;				
			}
			if(child+2 < count+2 && arr[child+3].frequency < value){
				minChild = child+3;
				value = arr[child+3].frequency;
			}			
			
			if(temp.frequency > value){
				arr[index+2] = arr[minChild];
			}else{
				break;
			}
			index = minChild-2;
		}
		arr[index+2] = temp;
	}	
}
