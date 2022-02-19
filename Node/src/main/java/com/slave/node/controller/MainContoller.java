package com.slave.node.controller;

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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/api")
public class MainContoller {
	
	private DataStore dataStore = new DataStore();
	private static boolean isAlive = true;
	
	@PostMapping("/words")
	public BaseResponse saveWords(@RequestBody Chunk c)
	{
		BaseResponse response = new BaseResponse();
		
		try {
			dataStore.mergeStore(c.getStartIndex(), c.getEndIndex(), c.getCreatedTime(), c.getWords());
			response.setExecutionStatus(Constants.Success);
		}
		catch(Exception e)
		{
			response.setExecutionStatus(Constants.Failure);
			response.setErrorDetails(e.getLocalizedMessage());
		}
		
		
		return response;
	}
	
	@GetMapping("/chunk")
	public BaseResponse getChunk()
	{
		ChunkResponse cresp = new ChunkResponse();
		try {
			cresp.setWords(dataStore.getWords());
			cresp.setExecutionStatus(Constants.Success);
		}
		catch(Exception e)
		{
			cresp.setExecutionStatus(Constants.Failure);
			cresp.setErrorDetails(e.getLocalizedMessage());
		}
		
		return cresp;
	}
	
	@PatchMapping("/node/enableDisable")
	@ApiOperation(value="enable or disable a partiicular node")
	public @ResponseBody String enableDisableNode( @RequestParam boolean action)
	{
		isAlive = action;
		
		return "Success";
	}

}
