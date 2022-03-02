package com.master.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.master.models.Chunk;
import com.master.request.WordRequest;
import com.master.response.BaseResponse;
import com.master.service.MasterService;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	MasterService masterservice;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
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
    
    @GetMapping("/hello1")
    @ApiOperation(value="")
    public String hello1(@RequestParam(value = "name", defaultValue = "World") String name) {
    	Chunk c = new Chunk();
    	Date d = new Date();
    	c.setCreatedTime(d);
    	String[] s = new String[15];
    	
    	for(int i=0;i<15;i++)
    	{
    		s[i] = "item"+i;
    	}
    	
    	c.setStartIndex(10);
    	c.setEndIndex(25);
    	c.setWords(s);
    	
    	String executionStatus =  webClientBuilder.build()
    			.post()
				.uri("http://localhost:8081/api/words/0")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(c),Chunk.class)
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
		return null;
	}
	
	@DeleteMapping("/word/{wordValue}")
	@ApiOperation(value="Delete a particular word in DB")
	public @ResponseBody BaseResponse deleteWord(@PathVariable String wordValue)
	{
		return masterservice.deleteWord(wordValue);
	}
	
	@PatchMapping("/node/{nodeId}")
	@ApiOperation(value="")
	public @ResponseBody BaseResponse enableDisableNode(@PathVariable String nodeId, @RequestParam String action)
	{
		return null;
	}
	
	@GetMapping("/nodes/status")
	@ApiOperation(value="Returns an array od nodeIds of active nodes if type=active, else returns inactive nodes")
	public @ResponseBody BaseResponse getNodesByType(@RequestParam String type)
	{
		return null;
	}
    
    


}