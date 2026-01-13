package com.frank.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig
{
	/**
	 * RedisTemplate配置
	 * redis序列化的工具配置類，下面這個請一定開啟配置
	 * 127.0.0.1:6379> keys *
	 * 1) "ord:102" 序列化過
	 * 2) "\xac\xed\x00\x05t\x00\aord:102" 野生，沒有序列化過
	 *
	 * this.redisTemplate.opsForValue(); //提供了操作string類型的所有方法
	 * this.redisTemplate.opsForList(); // 提供了操作list類型的所有方法
	 * this.redisTemplate.opsForSet(); //提供了操作set的所有方法
	 * this.redisTemplate.opsForHash(); //提供了操作hash表的所有方法
	 * this.redisTemplate.opsForZSet(); //提供了操作zset的所有方法
	 * @param redisConnectionFactor
	 * @return
	 */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactor)
    {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactor);
        //設定key序列化方式string
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //設定value的序列化方式json，使用GenericJackson2JsonRedisSerializer取代預設序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}


