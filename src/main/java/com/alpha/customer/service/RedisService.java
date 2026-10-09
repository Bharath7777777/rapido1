package com.alpha.customer.service;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void saveData(int id, double sourcelatitude, double sourcelongitude, double destinationlatitude,
			double destinationlongitude, double distance, HashMap<String, Double> fare) {
		String redisKey = "c_" + id;
		redisTemplate.opsForHash().put(redisKey, "sourceLat", String.valueOf(sourcelatitude));
		redisTemplate.opsForHash().put(redisKey, "sourceLon", String.valueOf(sourcelongitude));
		redisTemplate.opsForHash().put(redisKey, "destinationLat", String.valueOf(destinationlatitude));
		redisTemplate.opsForHash().put(redisKey, "destinationLon", String.valueOf(destinationlongitude));
		redisTemplate.opsForHash().put(redisKey, "distance", String.valueOf(distance));
		redisTemplate.opsForHash().put(redisKey, "bike", String.valueOf(fare.get("bike")));
		redisTemplate.opsForHash().put(redisKey, "auto", String.valueOf(fare.get("auto")));
		redisTemplate.opsForHash().put(redisKey, "cab", String.valueOf(fare.get("cab")));
		redisTemplate.expire(redisKey, Duration.ofDays(7));
	}
	public Map<Object, Object> getData(int id) {

	    String redisKey = "c_" + id;

	    return redisTemplate.opsForHash().entries(redisKey);
	}
	public void deleteData(int id) {

	    String redisKey = "c_" + id;

	    redisTemplate.delete(redisKey);
	}
	public List<String> findNearbyRiders(String vehicletype, double longitude, double latitude) {

		String key = vehicletype.toLowerCase();

		Point pickupPoint = new Point(longitude, latitude);

		Circle circle = new Circle(pickupPoint, new Distance(15, Metrics.KILOMETERS));

		GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().radius(key, circle);

		List<String> riders = new ArrayList<>();

		if (results != null) {
			for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
				riders.add(result.getContent().getName());
			}
		}

		return riders;
	}
	
	
}
