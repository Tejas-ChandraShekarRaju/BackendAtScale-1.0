package com.master.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class MetaDataTest {
	
	
	@Test
	public void givenMultiThread_whenNonSyncMethod() {
	    ExecutorService service = Executors.newFixedThreadPool(10000);
	    MetaData md = MetaData.getInstance();

	    IntStream.range(0, 1000)
	      .forEach(count -> service.submit(md::calculate));
	    try {
			service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println(md.getSum());
	    assertEquals(1000, md.getSum());
	}

}
