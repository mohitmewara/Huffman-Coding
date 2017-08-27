import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class encoder {
	
  	private static String ENCODEDFILE = "encoded.bin";
  	private static String CODETABLE = "code_table.txt";
  	private static String INPUTFILE = "";
	
	public static void main(String[] args) {
		try {
			if(args.length<1){
				System.out.println("No input provided for Input Filename.");
				return;
			}
			INPUTFILE = args[0];
			long start = System.currentTimeMillis();
			encoder encoder = new encoder();
			InputReader ip = new InputReader();
			Vector<Integer> input = ip.readInputFile(INPUTFILE);
			HashMap<Integer, Integer> hash = ip.generateFrequency(input);
			BinaryNode huffmanRoot = encoder.buildHuffmanFourCache(hash);
			HashMap<Integer, String> codeTable = encoder.generateHuffmanCodes(huffmanRoot);
			encoder.codeTableWriter(codeTable);
			encoder.encodedBinaryWriter(codeTable,input);
			long end = System.currentTimeMillis();
			
			System.out.println("Encoded file created: " + ENCODEDFILE);
			System.out.println("Code Table file created: " + CODETABLE);
			System.out.println("Time for encoding (milliseconds): "+(end-start));			
		} catch (Exception e) {
			System.out.println("File not found at the specified location: " + args[0]);
		}
	}	
	
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
	
    public HashMap<Integer, String> generateHuffmanCodes(BinaryNode node) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        generateHuffmanCodes(node, map, "");
        return map;
     }


     public void generateHuffmanCodes(BinaryNode node, Map<Integer, String> map, String code) {
         if (node.left == null && node.right == null) {
             map.put(node.number, code);
             return;
         }    
         generateHuffmanCodes(node.left, map, code + '0');
         generateHuffmanCodes(node.right, map, code + '1' );
     }
     
  	public void codeTableWriter(Map<Integer, String> codeMap) {
  		BufferedWriter bw = null;
  		FileWriter fw = null;
  		StringBuilder content =new StringBuilder("");
  		try {
  			for(Map.Entry<Integer, String> code : codeMap.entrySet()){
  				content.append(code.getKey());
  				content.append(" ");
  				content.append(code.getValue());
  				content.append("\n");
  			}
  			fw = new FileWriter(CODETABLE);
  			bw = new BufferedWriter(fw);
  			bw.write(content.toString());
  		} catch (IOException e) {
  			e.printStackTrace();
  		} finally {
  			try {
  				if (bw != null){
  					bw.close();
  				}

  				if (fw != null){
  					fw.close();
  				}

  			} catch (IOException ex) {
  				ex.printStackTrace();
  			}

  		}
  	}
  	
  	public void encodedBinaryWriter(Map<Integer, String> codeMap, Vector<Integer> input) {
  		FileOutputStream outputStream= null;
  		File file;
  		try {
			file = new File(ENCODEDFILE);
			outputStream = new FileOutputStream(file);
			
			if(!file.exists()){
				file.createNewFile();
			}
			
			StringBuilder test = new StringBuilder("");
			for(int in : input){
				String code = codeMap.get(in);
				test.append(code);
			}
			
			int index=0;
			byte[] byteArray = new byte[test.length()/8];
			int count=0;
			while(index < test.length()-7){
			       byte nextByte = 0x00;
			       for(int i=0;i<8; i++){
			           nextByte = (byte) (nextByte << 1);
			           nextByte += test.charAt(index+i)=='0'?0x0:0x1;
			       }
			       byteArray[count] = nextByte;
			       count++;
			       index+=8;
			}
			outputStream.write(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
  	}
}
