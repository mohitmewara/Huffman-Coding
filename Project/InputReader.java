

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class InputReader {
	
	public Vector<Integer> readInputFile(String fileName){
		Vector<Integer> input = new Vector<Integer>();
		BufferedReader buffer = null;
		FileReader fileReader = null;
		
		try{
			fileReader = new FileReader(fileName);
			buffer = new BufferedReader(fileReader);
			
			String line;
			while((line = buffer.readLine()) != null){
				if(!line.trim().equals("")){
					input.add(Integer.parseInt(line.trim()));				
				}
			}
			
		}catch(FileNotFoundException e){
//			e.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
		finally{
			try{
				if(buffer != null){
					buffer.close();
				}
				if(fileReader != null){
					fileReader.close();
				}
				
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
		return input;		
	}
	
	public HashMap<Integer, Integer> generateFrequency(Vector<Integer> input){
		HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
		for(int key : input){
			if(hash.containsKey(key)){
				hash.put(key, hash.get(key)+1);
			}else{
				hash.put(key, 1);
			}					
		}
		return hash;
	}

	public HashMap<Integer, String> readCodeTableFile(String fileName){
		HashMap<Integer, String> hash = new HashMap<Integer, String>();
		BufferedReader buffer = null;
		FileReader fileReader = null;
		
		try{
			fileReader = new FileReader(fileName);
			buffer = new BufferedReader(fileReader);
			
			String line;
			while((line = buffer.readLine()) != null){
				if(!line.equals("")){
					String[] lineArr = line.split(" ");
					hash.put(Integer.parseInt(lineArr[0]), lineArr[1]);
				}
			}
		}catch(FileNotFoundException fo){
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(buffer != null){
					buffer.close();
				}
				if(fileReader != null){
					fileReader.close();
				}
				
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
		return hash;
	}
	
	
}
