package com.master.service;

import java.util.Arrays;

public class Util {
	
	public static String[] getWordsByMaxLimit(int limit, String[] words)
	{
		
		if(words.length < limit)
		{
			return words;
		}
		
		else 
		{
			return Arrays.copyOfRange(words,words.length-limit,words.length);
		}
	}

	

}
