package com.slave.node.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.slave.node.constants.Constants;
import com.slave.node.models.Chunk;
import com.slave.node.response.*;

import com.slave.node.storage.DataStore;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/api")
public class MainContoller {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MainContoller.class);
	
	private DataStore dataStore = new DataStore();
	private static boolean isAlive = true;
	
	@PostMapping("/words")
	public BaseResponse saveWords(@RequestBody Chunk c)
	{
		LOGGER.info("Entry to Saving words");
		BaseResponse response = new BaseResponse();
		
		try {
			dataStore.mergeStore(c.getStartIndex(), c.getEndIndex(), c.getCreatedTime(), c.getWords(),c.getNodeId());
			response.setExecutionStatus(Constants.Success);
		}
		catch(Exception e)
		{
			response.setExecutionStatus(Constants.Failure);
			response.setErrorDetails(e.getLocalizedMessage());
			LOGGER.error(e.getMessage());
		}
		
		
		return response;
	}
	
	@GetMapping("/chunk")
	public BaseResponse getChunk()
	{
		LOGGER.info("Entry to get Chunks");
		ChunkResponse cresp = new ChunkResponse();
		try {
			cresp.setWords(dataStore.getWords());
			cresp.setExecutionStatus(Constants.Success);
		}
		catch(Exception e)
		{
			cresp.setExecutionStatus(Constants.Failure);
			cresp.setErrorDetails(e.getLocalizedMessage());
			LOGGER.info(e.getMessage());
		}
		
		return cresp;
	}
	
	@PatchMapping("/node/enableDisable")
	@ApiOperation(value="enable or disable a partiicular node")
	public @ResponseBody String enableDisableNode( @RequestParam boolean action)
	{
		synchronized(this){
			isAlive = action;	
		}
		
		
		return "Success";
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(value="delete a word")
	public @ResponseBody BaseResponse deleteWord(@RequestParam String word)
	{
		LOGGER.info("Entry to delete word");
		BaseResponse response = new BaseResponse();
		response.setExecutionStatus(dataStore.deleteWord(word));
		return response;
		  
	}

}
