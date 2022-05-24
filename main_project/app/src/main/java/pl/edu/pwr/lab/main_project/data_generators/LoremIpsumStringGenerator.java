package pl.edu.pwr.lab.main_project.data_generators;

import java.util.Random;

public class LoremIpsumStringGenerator {
	private static final String loremString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
			"sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim " +
			"veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
			"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat " +
			"nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
			"deserunt mollit anim id est laborum.";
	private static final String[] loremWords =  loremString.split(" ");

	public static String getLoremSubstring(int wordsCount){
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < wordsCount; ++i){
			output.append(loremWords[i % loremWords.length]).append(" ");
		}

		return output.toString();
	}

	public static String getLoremRandomWord(){
		Random r = new Random();
		return loremWords[r.nextInt(loremWords.length)];
	}
}
