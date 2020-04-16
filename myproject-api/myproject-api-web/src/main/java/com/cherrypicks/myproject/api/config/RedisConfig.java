package com.cherrypicks.myproject.api.config;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private int database;
    private String host;
    private int port;
    private String password;
    private int poolMaxActive;
    private int poolMaxWait;
    private int poolMaxIdle;
    private int poolMinIdle;
    private int timeout;
    private int numTestsPerEvictionRun;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private boolean ssl;
    
    @Bean
    @SuppressWarnings("rawtypes")
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        if(StringUtils.isNotBlank(password)) {
        	redisStandaloneConfiguration.setPassword(password);
        }
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMinIdle(poolMinIdle);
        poolConfig.setMaxTotal(poolMaxActive);
        poolConfig.setMaxWaitMillis(poolMaxWait);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        
        LettuceClientConfigurationBuilder clientConfigBuilder = LettucePoolingClientConfiguration
									        		.builder()
									        		.commandTimeout(Duration.ofMillis(timeout))
									                .poolConfig(poolConfig);
									                
        if (ssl){
        	clientConfigBuilder.useSsl();
        }
        
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfigBuilder.build());
    }



	public int getDatabase() {
		return database;
	}



	public void setDatabase(int database) {
		this.database = database;
	}



	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public int getPoolMaxActive() {
		return poolMaxActive;
	}



	public void setPoolMaxActive(int poolMaxActive) {
		this.poolMaxActive = poolMaxActive;
	}



	public int getPoolMaxWait() {
		return poolMaxWait;
	}



	public void setPoolMaxWait(int poolMaxWait) {
		this.poolMaxWait = poolMaxWait;
	}



	public int getPoolMaxIdle() {
		return poolMaxIdle;
	}



	public void setPoolMaxIdle(int poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}



	public int getPoolMinIdle() {
		return poolMinIdle;
	}



	public void setPoolMinIdle(int poolMinIdle) {
		this.poolMinIdle = poolMinIdle;
	}



	public int getTimeout() {
		return timeout;
	}



	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}



	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}



	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}



	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}



	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}



	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}



	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}



	public boolean isSsl() {
		return ssl;
	}



	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}
    
    
}
