/**
 * 
 */
package com.wavecrestpayments.ratelimit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author deva
 *
 */
@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String redisHost;
	
	@Bean
	public RedisTemplate<String, Long> redisTemplate() {
	   RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
	   RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost);
	   JedisConnectionFactory factory = new JedisConnectionFactory(config);
	   redisTemplate.setConnectionFactory(factory);
	   redisTemplate.setEnableTransactionSupport(true);
	   return redisTemplate;
	} 
}
