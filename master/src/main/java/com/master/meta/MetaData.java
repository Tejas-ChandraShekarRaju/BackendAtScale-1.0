package com.master.meta;

public class MetaData {
	
	static MetaData md = new MetaData();
	
	private MetaData() {
		
	}
	
	private int sum = 0;
	
	private int LRUI = 0;
	private int LRUN = 0;
	
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
  	
	

}
