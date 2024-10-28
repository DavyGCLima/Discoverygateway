package com.sfiss.gateway.gateway_mvc.config;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@ConditionalOnClass({
    org.springframework.data.redis.cache.RedisCacheConfiguration.class,
    RedisCacheManager.class,
    RedisCacheWriter.class,
    RedisConnectionFactory.class
})
@RequiredArgsConstructor
public class RedisCache {

    @Value("${cache.expira.segundos:3600}")
    private long expiraSegundos;

    @Bean
    public RedisCacheWriter redisCacheWriter(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
    }

    @Bean({"autorizacao-cache", "autenticacao-cache"})
    public RedisCacheManager redisCacheManager(RedisCacheWriter redisCacheWriter){
        return new RedisCacheManager(redisCacheWriter, org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(expiraSegundos)));
    }

}
