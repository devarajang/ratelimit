/**
 * 
 */
package com.wavecrestpayments.ratelimit;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

/**
 * @author deva
 *
 */
@Component
public class TrafficDataGenerator {
	
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	
	private ZSetOperations<String, Long> opsForZSet;
	
	ExecutorService executor = Executors.newFixedThreadPool(10);
	
	Set<Long> orderedSet = new TreeSet<Long>();
	
	public Future generateTraffic() {
		
		opsForZSet = redisTemplate.opsForZSet();
		Runnable runnableTask = () -> {
		    try {
		    	int i = 10000;
		    	while(i-- > 0) {
		    		
			        TimeUnit.MILLISECONDS.sleep(25);
			        Calendar calendar = Calendar.getInstance();
					calendar = DateUtils.truncate(calendar, Calendar.HOUR_OF_DAY);
					Calendar calendar1 = Calendar.getInstance();
					calendar1 = DateUtils.truncate(calendar1, Calendar.MINUTE);
					long minutes = (calendar1.getTimeInMillis() - calendar.getTimeInMillis())/(60*1000);
					
					minutes=(minutes/10)*10;
					
					calendar.add(Calendar.MINUTE, (int) minutes);
					Random random = new Random();
					long value =  random.nextInt(3822123) + 123456;
					orderedSet.add(value);
					System.out.println("responsetime:" + calendar.getTimeInMillis() +  " , " + value);
					opsForZSet.add("responsetime:" + calendar.getTimeInMillis() , value, value);
		    	}
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		};
		 
		return executor.submit(runnableTask);
		
	}
	
	public long get90Percentile() {
//		System.out.println(orderedSet.toArray()[9000]);
		opsForZSet = redisTemplate.opsForZSet();
		 Calendar calendar = Calendar.getInstance();
			calendar = DateUtils.truncate(calendar, Calendar.HOUR_OF_DAY);
			
			
			Calendar calendar1 = Calendar.getInstance();
			calendar1 = DateUtils.truncate(calendar1, Calendar.MINUTE);
			
			System.out.println(calendar1.getTime());
			
			long minutes = (calendar1.getTimeInMillis() - calendar.getTimeInMillis())/(60*1000);
			
			minutes=(minutes/10)*10;
			
			calendar.add(Calendar.MINUTE, (int) minutes);
			Long zCard = opsForZSet.zCard("responsetime:1556309400000");
			zCard = (long) (zCard * .9);
			Set<Long> range = opsForZSet.range("responsetime:1556309400000", zCard, zCard);
			System.out.println(range);
		return (long) range.toArray()[0];
	}
	
}
