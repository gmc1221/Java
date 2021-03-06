package main;
import org.json.JSONArray;
import org.json.JSONObject;

public class Leaderboards {

	JSONObject boards;

	
	public Leaderboards(JSONObject obj){
		
		this.boards = obj;
		
	}
	
	public int getHighScore(){
		
		JSONArray scores = boards.getJSONArray("highscores");
		
		int highscore = 0;
		
		for(int i = 0; i < scores.length(); i++){
			System.out.println(scores.getJSONObject(i).getInt("score"));
			if(scores.getJSONObject(i).getInt("score") > highscore){
				highscore = scores.getJSONObject(i).getInt("score");
			}
		}
		System.out.println(highscore);
		return highscore;
	}
	
	public void addScore(int score){
		
		JSONArray scores = boards.getJSONArray("highscores");
		
		JSONObject obj = new JSONObject();
		obj.put("score", score);
				
		scores.put(obj);
		
		boards.put("highscores", scores);
	}
	
	public JSONObject get(){
		return boards;
	}
	
}
