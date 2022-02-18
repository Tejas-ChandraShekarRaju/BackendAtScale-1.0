package com.master.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.master.request.Word;
import com.master.response.BaseResponse;
import com.master.service.MasterService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	MasterService masterservice;
	
    @GetMapping("/hello")
    @ApiOperation(value="")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
    }
    
    @PostMapping("/words")
    @ApiOperation(value="")
	public @ResponseBody BaseResponse saveWords(@RequestBody Word word)
	{
		return masterservice.saveWords(word.getWords());
	}
	
	@GetMapping("/words")
	@ApiOperation(value="")
	public @ResponseBody BaseResponse getWords()
	{
		return null;
	}
	
	@DeleteMapping("/words")
	@ApiOperation(value="")
	public @ResponseBody BaseResponse deleteWords()
	{
		return null;
	}
	
	@DeleteMapping("/word/{wordValue}")
	@ApiOperation(value="")
	public @ResponseBody BaseResponse deleteWord(@PathVariable String wordValue)
	{
		return null;
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