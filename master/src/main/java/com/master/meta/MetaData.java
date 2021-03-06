package com.master.meta;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.constant.Constants;
import com.master.dto.SlaveConfigDto;






public class MetaData {
	

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MetaData.class);
	private int sum = 0;
	private int LRUI = 0;
	private int LRUN = 0;
	private List<String> nodeUris = new ArrayList<>(5);
	private String defaulturl = "https://gist.githubusercontent.com/Tejas-ChandraShekarRaju/7a876c1e7b7076be91e150382371a545/raw/bb4417c26b36304585995acbf665ee1ac578f465/slaveconfig.json";
	//@Value("${slaveconfig.url:defaultslaveConfigUrl}")
	private String slaveConfigUrl = defaulturl;
	private String configSlaveStatus = "NA";//Constants.Failure;
	private String slaveStatus = "ACTIVE";

	
	static MetaData md = new MetaData();
	
	private MetaData() {
		 
		  getSlaveConfig();
		
	}





	private void getSlaveConfig() {
		
		try {
			LOGGER.info(String.format("Entered getSlaveConfig with url {%s}", slaveConfigUrl));
			SlaveConfigDto config =  WebClient.builder()
			        .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer ->{
			                ObjectMapper mapper = new ObjectMapper();
			                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			                configurer.customCodecs().decoder(new Jackson2JsonDecoder(mapper, MimeTypeUtils.parseMimeType(MediaType.TEXT_PLAIN_VALUE)));
			                }).build())
			        .build()
			        .get()
			        .uri(slaveConfigUrl)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(SlaveConfigDto.class)
					.block();
		  System.out.println(config == null);
		  config.getSlaveNodes().forEach(node ->{
			  nodeUris.add(node.getNodeUrl()+"/api");
		  });
		this.configSlaveStatus = Constants.Success;
		LOGGER.info("Exiting getSlaveConfig successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.configSlaveStatus = Constants.Failure;
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





	public  String getConfigSlaveStatus() {
		return configSlaveStatus;
	}





	public synchronized String getSlaveStatus() {
		return slaveStatus;
	}





	public synchronized void setSlaveStatus(String slaveStatus) {
		this.slaveStatus = slaveStatus;
	}


	
	


  	
	

}
