import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class decoder {
  	private static String DECODEDFILE = "decoded.txt";
  	private static String ENCODEDFILE = "";
  	private static String CODETABLE = "";
  	
  	public static void main(String[] args) {
  		try{
  			if(args.length<2){
  				if(args.length==1){
  					System.out.println("No input provided for code table file.");
  				}else{
  					System.out.println("No input provided for code table & encoded file.");
  				}
  				return;
  			}
	  		ENCODEDFILE=args[0];
	  		CODETABLE=args[1];		
			long start = System.currentTimeMillis();
	  		decoder decode = new decoder();
			BinaryNode rootNode = decode.buildDecodedHuffmanTree();
			decode.generateDecodedFile(rootNode);
			long end = System.currentTimeMillis();
			System.out.println("Decoded file created: " + DECODEDFILE);
			System.out.println("Time for decoding (milliseconds): "+(end-start));
  		}catch(Exception e){
  			System.out.println("File not found at the specified location: " + args[0] + "  "+ args[1]);
  		}
	}
  	
	public BinaryNode buildDecodedHuffmanTree(){
		InputReader ip = new InputReader();
		HashMap<Integer, String> codeTable = ip.readCodeTableFile(CODETABLE);
		BinaryNode rootNode = new BinaryNode();
		for(Map.Entry<Integer, String> entry : codeTable.entrySet()){
			BinaryNode node = rootNode;
			char[] charArr = entry.getValue().toCharArray();
			for(char digit : charArr){
				if(digit == '0' && node.left == null){
					node.left = new BinaryNode();
					node = node.left;
				}else if(digit == '0'){
					node = node.left; 
				}else if(digit == '1' && node.right == null){
					node.right = new BinaryNode();
					node = node.right;
				}else{
					node = node.right;
				}
			}
			node.number = entry.getKey();
		}
		return rootNode;
	}

	public void generateDecodedFile(BinaryNode rootNode){
  		try {  			
            File encodedFile = new File(ENCODEDFILE);
            InputStream inputStream = new BufferedInputStream(new FileInputStream(encodedFile));
            byte[] encodedArr = new byte[(int)encodedFile.length()];
            int totalBytesRead = 0;            
            while(totalBytesRead < encodedArr.length)
            {
                int remaining = encodedArr.length - totalBytesRead;
                int bytesRead = inputStream.read(encodedArr, totalBytesRead, remaining);
                if (bytesRead > 0)
                {
                    totalBytesRead += bytesRead;
                }
            }
            inputStream.close();
            FileWriter fileWriter = new FileWriter(DECODEDFILE);
            BufferedWriter out = new BufferedWriter(fileWriter);
            BinaryNode node = rootNode;
            TraverseBit traverseBit = new TraverseBit(encodedArr);
            while(traverseBit.hasNext())
            {
                if(traverseBit.getNext())
                	node = node.right;
                else 
                	node = node.left;
                if(node.left == null && node.right == null)
                {
                    out.write(node.number+"\n");
                    node = rootNode;
                }
            }
            out.close();  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
	}		
	
    class TraverseBit
    {
        byte[] byteArray;
        int arrIndex = 0;
        int bitInd = 0;
        char[] byteVal = {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};

        public TraverseBit(byte[] byteArray)
        {
            this.byteArray = byteArray;
        }

        boolean hasNext() 
        {
            return !(bitInd == 8 && arrIndex == (byteArray.length-1));
        }
        
        boolean getNext()
        {
            if(bitInd==8) 
            {
            	arrIndex++;
                bitInd=0;
            }
            return (byteArray[arrIndex]&byteVal[bitInd++]) > 0;
        }        
    }	

}
