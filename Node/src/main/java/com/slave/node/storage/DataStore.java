package com.slave.node.storage;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.slave.node.constants.Constants;
import com.slave.node.models.Word;

public class DataStore {
	
	List<Word> words = new ArrayList<>(25);
	
	

	public List<Word> getWords() {
		return words;
	}
	
	public DataStore() {
		for(int i=0;i<25;i++)
		{
			Word w = new Word();
			this.words.add(w);
		}
	}

	
	
	public void mergeStore(int startIndex,int endIndex,Date time, String[] words, int nodeId){
	
		int count = 0;
		for(int i=startIndex;i<endIndex && count<words.length;i++)
		{
			this.words.get(i).setCreatedTime(time);
			this.words.get(i).setWord(words[count]);
			this.words.get(i).setNodeId(nodeId);
			count++;
			
		}
	}
	
	public String deleteWord(String word)
	{
		try {
		
			for(Word w:this.words)
			{
				if(w.getWord()!=null && w.getWord().equalsIgnoreCase(word))
				{
					w.setCreatedTime(new Date());
					w.setWord(Constants.Deleted);
				}
			}
		
		
			}
		catch(Exception e)
		{
			e.printStackTrace();
			return Constants.Failure;
		}

	return Constants.Success;
	}
	
	public String deleteWords()
	{
		try {
			for(Word w:this.words)
			{
				w.setWord(Constants.Deleted);
			}
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			return Constants.Failure;
		}
		
		return Constants.Success;
	}
}
