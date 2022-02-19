package com.master.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


import org.junit.jupiter.api.Test;

import com.master.models.Node;

public class MasterServiceImplTest {
	
	
	MasterServiceImpl masterservice = new MasterServiceImpl();
	
	@Test
	public void createMergePackageTest()
	{
		String[] arr = new String[50];
		String[] arr2 = new String[64];
		String[] arr3 = new String[2];
		
		for(int i=0;i<50;i++)
		{
			arr[i] = "item"+i;
			arr2[i] = "item"+i;
		}
		List<Node> nodesData = masterservice.createMergePackage(0, 0, arr);
		
		System.out.println(nodesData.size());
		
		assertEquals(2,nodesData.size());
		
		nodesData = masterservice.createMergePackage(0, 0, arr2);
		
		System.out.println(nodesData.size());
		
		assertEquals(3,nodesData.size());
		System.out.println("endIndex"+nodesData.get(2).getChunk().getEndIndex());
		assertEquals(14,nodesData.get(2).getChunk().getEndIndex());
		
		nodesData = masterservice.createMergePackage(0, 0, arr);
		System.out.println("endIndex"+nodesData.get(1).getChunk().getEndIndex());
		assertEquals(25,nodesData.get(1).getChunk().getEndIndex());
	
	}

}
