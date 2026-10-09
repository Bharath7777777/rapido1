package com.alpha.customer.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.customer.dto.Fareresponsedto;
import com.alpha.customer.dto.Selectridedto;
import com.alpha.customer.entity.Coordinates;
import com.alpha.customer.repository.Coordinaterepository;

import tools.jackson.databind.JsonNode;

@Service
public class Coordinateservice {
	@Autowired
	private Coordinaterepository corepo;
	@Autowired
	private RestTemplate resttemplate;
	@Autowired
	private RedisTemplate<String,String> redistemplate;
	@Autowired
	private RedisService rediservice; 
	
	
	public Fareresponsedto selectriderservice(Selectridedto sdto,int id) {
		Coordinates source=new Coordinates();
		source.setLatittude(sdto.getSourcecoordinate().getLatittude());
		source.setLongitude(sdto.getSourcecoordinate().getLongitude());
		Coordinates destination=new Coordinates();
		destination.setLatittude(sdto.getDestcoordinate().getLatittude());
		destination.setLongitude(sdto.getDestcoordinate().getLongitude());
		corepo.save(source);
		corepo.save(destination);
	
		double sourcelatitude=sdto.getSourcecoordinate().getLatittude();
		double sourcelongitude=sdto.getSourcecoordinate().getLongitude();
		double destinationlatitude=sdto.getDestcoordinate().getLatittude();
		double destinationlongitude=sdto.getDestcoordinate().getLongitude();
		String token = "pk.ed0ebe59f7a6d7e4937a2d6c6c0e697e";
		String url = "https://us1.locationiq.com/v1/directions/driving/" + sourcelongitude + "," + sourcelatitude + ";"
				+ destinationlongitude + "," + destinationlatitude + "?key=" + token + "&overview=false";
		JsonNode response=resttemplate.getForObject(url,JsonNode.class);
		double distanceinm=response.get("routes").get(0).get("distance").asDouble();
		double distance=distanceinm/1000.0;
		double Bikefare=distance*10;
		double Autofare=distance*20;
		double cabfare=distance*30;
		HashMap<String, Double> fare=new HashMap<>();
		fare.put("bike",Bikefare);
		fare.put("auto", Autofare);
		fare.put("cab", cabfare);
		Fareresponsedto fdto=new Fareresponsedto();
		fdto.setSource(source);
		fdto.setDestination(destination);
		fdto.setDistance(distance);
		fdto.setFare(fare);
		
		rediservice.saveData(id, sourcelatitude, sourcelongitude, destinationlatitude, destinationlongitude, distance, fare);
		
		
		return fdto;
		
		
	}
}
