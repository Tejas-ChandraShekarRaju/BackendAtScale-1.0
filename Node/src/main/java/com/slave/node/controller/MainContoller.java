package com.slave.node.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.slave.node.constants.Constants;
import com.slave.node.models.Chunk;
import com.slave.node.response.*;

import com.slave.node.storage.DataStore;

import io.swagger.annotations.ApiOperation;

import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableSwagger2
@RestController
@RequestMapping("/api")
public class MainContoller implements HandlerInterceptor{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MainContoller.class);
	
	private DataStore dataStore = new DataStore();
	private static boolean isAlive = true;
	
	@Value("${node.id}")
	private int nodeId;
	
	
	 @Override
	   public boolean preHandle(
	      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		   
		   LOGGER.info("Entry to prehandle method"+request.getRequestURI());
		 String requestUri = request.getRequestURI();
	      synchronized(this)
	      {
	    	  if(requestUri.equalsIgnoreCase("/api/node/enableDisable")) return true;
	    	  
	    	  else if(isAlive) 
	    	  {
	    		  
	    		  return true;
	    	  }
	    	  else
	    	  {
	    		  response.setStatus(400);
	    		  return false;
	    	  }
	      }
	   }
	 
	
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
	public @ResponseBody BaseResponse enableDisableNode( @RequestParam boolean action)
	{
		BaseResponse response = new BaseResponse();
		try {
		synchronized(this){
			isAlive = action;	
		}
		response.setExecutionStatus(Constants.Success);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setExecutionStatus(Constants.Failure);
		}
		
		return response;
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
	
	@DeleteMapping("/words")
	@ApiOperation(value="delete all words")
	public @ResponseBody BaseResponse deleteWords()
	{
		LOGGER.info("Entry to delete all words");
		BaseResponse response = new BaseResponse();
		response.setExecutionStatus(dataStore.deleteWords());
		return response;
		  
	}
	
	@GetMapping("/status")
	public StatusResponse getStatus()
	{
		LOGGER.info("Entry to get Status");
		StatusResponse response =  new StatusResponse();
		response.setIsAlive(isAlive);
		response.setNodeId(nodeId);
		return response;
	}
	
	  
	
}
