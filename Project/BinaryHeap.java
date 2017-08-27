

public class BinaryHeap {
	public BinaryNode[] arr;
	public int count;
	public int capacity;
	public int degree=2;
	
	public BinaryHeap(int cap){
		this.capacity = cap+1;
		this.count = 0;
		this.arr = new BinaryNode[capacity];
	}
	
	public void insert(BinaryNode node){
		if(count == capacity-1){
			resizeHeap();
		}
		int index = ++count;
		while(index >1 && node.frequency < arr[index/2].frequency){
			arr[index] = arr[index/2];
			index /= 2;
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
		return arr[1];
	}
	
	public BinaryNode extractMin(){
		if(count == 0){
			return null;
		}
		BinaryNode node = arr[1];
		arr[1] = arr[count];
		arr[count--] = null;
		heapifyDown(1);
		return node;
	}
	
	public void heapifyDown(int index){
		int child;
		BinaryNode temp = arr[index];
		while(2*index <= count){
			child = 2 * index;
			if(child < count && arr[child+1].frequency < arr[child].frequency){
				child++;
			}
			
			if(temp.frequency > arr[child].frequency){
				arr[index] = arr[child];
			}else{
				break;
			}
			index = child;
		}
		arr[index] = temp;
	}
}
