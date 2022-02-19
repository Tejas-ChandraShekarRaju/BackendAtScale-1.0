package com.master.meta;

import java.util.ArrayList;
import java.util.List;




public class MetaData {
	

	
	private int sum = 0;
	private int LRUI = 0;
	private int LRUN = 0;
	private List<String> nodeUris = new ArrayList<>(5);

	
	static MetaData md = new MetaData();
	
	private MetaData() {
		for(int i=0;i<5;i++)
		{
			this.nodeUris.add("http://localhost:808"+(i+1)+"/api");
		}
	}
	
	
	
	public static  MetaData getInstance()
	{
		return md;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum= sum;
	}
	
	public synchronized void calculate() {
        setSum(getSum() + 1);
    }

	public synchronized int getLRUI() {
		return LRUI;
	}
	
	public synchronized void setLRUI(int lRUI) {
		LRUI = lRUI;
	}
	
	public synchronized int getLRUN() {
		return LRUN;
	}
	
	public synchronized void setLRUN(int lRUN) {
		LRUN = lRUN;
	}



	public List<String> getNodeUris() {
		return nodeUris;
	}
	
	


  	
	

}
