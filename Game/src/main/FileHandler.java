package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileHandler {

	String filename;
	File file;
	
	public FileHandler(String name){
		this.filename = name;
		file = new File(filename);
		
		System.out.println(file.exists());
		
		if(!file.exists()){
			
			try{
				
				JSONObject obj = new JSONObject();
				JSONArray entries = new JSONArray();
				JSONObject entry = new JSONObject();
				
				entry.put("score", 0);
				
				entries.put(entry);
				
				obj.put("highscores", entries);
				
				String json = obj.toString();

				FileWriter fw = new FileWriter(file);
				
				
				char[] chars = json.toCharArray();
				
				fw.write(chars);
				
				fw.close();
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public JSONObject read(){
		
		JSONObject out = null;
        
		String str = "";
		
		try {
			FileReader fr = new FileReader(file);
			
			
			str = new String(Files.readAllBytes(Paths.get(filename)));
			
			fr.close();
			
			out = new JSONObject(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return out;
	}
	
	public void write(JSONObject obj){

		String json = obj.toString();

		FileWriter fw;
		try {
			
			fw = new FileWriter(file);
		
			char[] chars = json.toCharArray();
			
			fw.write(chars);
			
			fw.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
