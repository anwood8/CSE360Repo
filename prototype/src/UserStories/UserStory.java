package UserStories;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class UserStory {
	public static ArrayList<UserStory> stories = new ArrayList<UserStory>();
	
	public ArrayList<String> keywords;
	public ArrayList<String> acceptanceCriteria;
	public String story;
	public UserStory(String story, String keywords) {
		this.story = story;
		this.keywords = convertStringToArrayList(keywords);
	}
	
	private ArrayList<String> convertStringToArrayList(String in) {
		ArrayList<String> list = new ArrayList<String>();
		String[] split = in.split(",");
		for(int i = 0;i<split.length;i++) {
			list.add(split[i]);
		}
		return list;
	}
	
	public static void saveStories() {
		try {
			FileWriter outputFile = new FileWriter("UserData/save.csv");
			CSVWriter writer = new CSVWriter(outputFile);
			String header[] = {"story", "keywords"};
			writer.writeNext(header);
			
			for(int i = 0;i<stories.size();i++) {
				UserStory story = stories.get(i);
				String keywordStr = "";
				for(int j = 0;j<story.keywords.size();j++) {
					keywordStr+=story.keywords.get(j)+",";
				}
				String[] data = {story.story, keywordStr};
				writer.writeNext(data);
			}
			writer.close();
				
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<UserStory> loadStories() {
		//stories = new ArrayList<UserStory>();
		//stories.add(new UserStory("As a leader, I want to build a wall so I can idk reason reason reason","wall,construction,build"));
		return stories;
	}
}
