package com.master.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


import com.master.request.WordRequest;
import com.master.response.BaseResponse;
import com.master.service.MasterService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/api")
public class MainController implements HandlerInterceptor{
	
	@Autowired
	MasterService masterservice;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MainController.class);
	
    @GetMapping("/hello")
    @ApiOperation(value="")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    	String executionStatus =  webClientBuilder.build()
				.get()
				.uri("http://localhost:8081/api/hello")
				.retrieve()
				.bodyToMono(String.class)
				.block();
    	
    return executionStatus;
    }
    
 
    
    @PostMapping("/words")
    @ApiOperation(value="")
	public @ResponseBody BaseResponse saveWords(@RequestBody WordRequest word)
	{
		return masterservice.saveWords(word.getWords());
	}
	
	@GetMapping("/words")
	@ApiOperation(value="Get all words in DB")
	public @ResponseBody BaseResponse getWords()
	{
		return masterservice.getWords();
	}
	
	@DeleteMapping("/words")
	@ApiOperation(value="Delete all words in DB")
	public @ResponseBody BaseResponse deleteWords()
	{
		return masterservice.deleteWords();
	}
	
	@DeleteMapping("/word/{wordValue}")
	@ApiOperation(value="Delete a particular word in DB")
	public @ResponseBody BaseResponse deleteWord(@PathVariable String wordValue)
	{
		return masterservice.deleteWord(wordValue);
	}
	
	@PatchMapping("/node/{nodeId}")
	@ApiOperation(value="")
	public @ResponseBody BaseResponse enableDisableNode(@PathVariable int nodeId, @RequestParam String action)
	{
		return masterservice.enableDisableNode(nodeId, action);
	}
	
	@GetMapping("/nodes/status")
	@ApiOperation(value="Returns an array od nodeIds of active nodes if type=active, else returns inactive nodes")
	public @ResponseBody BaseResponse getNodesByType(@RequestParam String type)
	{
		return masterservice.getNodesByType(type);
	}
    
	 @Override
	   public boolean preHandle(
			   HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		 LOGGER.info(String.format("Entry to preHandle {%s}",request.getRequestURI()));

		 if(request.getRequestURI().contains("/api/node/") || request.getRequestURI().contains("/api/nodes/status") ) return true;

		 else
		 {
			 boolean res =  masterservice.preHandle();
			 	if(res==false)
			 	{
			 		response.setStatus(500);
			 		response.sendError(500, "Storage services are down");
			 		return false;
			 	}
			 	else
			 	{
			 		return true;
			 	}
			 
		 }

	
	   }
	 


}