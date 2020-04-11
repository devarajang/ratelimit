package com.wavecrestpayments.ratelimit;

import java.util.concurrent.Future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author deva
 *
 */

@SpringBootApplication
public class RatelimitApplication {

	
	
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(RatelimitApplication.class, args);
		TrafficDataGenerator generator = context.getBean(TrafficDataGenerator.class);
//		Future future = generator.generateTraffic();
//		while(future.get() != null) {
//			Thread.sleep(1000L);
//		} 3546784
		System.out.println(generator.get90Percentile());
	}
	
	

}
