package com.cherrypicks.myproject.api.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement
public class DataSourceConfig {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String driverClassName;
	private String url;
	private String userName;
	private String password;
	private Integer initialSize;
	private Integer maxActive;
	private Integer minIdle;
	private Integer maxIdle;

	@Bean
	@Primary
	public DataSource dataSource() throws Exception {
		return tomcatPoolingDataSource();
	}
	
	/**
	 * DataSourceProperties类添加@Component和@ConfigurationProperties(prefix =
	 * "spring.datasource")注解使用的时候用@Autowired注入 意义相同
	 */
//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSourceConfig getDataSourceConfig() {
//		return new DataSourceConfig();
//	}

	private DataSource tomcatPoolingDataSource() throws Exception {
		final org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setInitialSize(initialSize); // 连接池启动时创建的初始化连接数量（默认值为0）
		dataSource.setMaxActive(maxActive); // 连接池中可同时连接的最大的连接数
		dataSource.setMaxIdle(maxActive); // 连接池中最大的空闲的连接数，超过的空闲连接将被释放，如果设置为负数表示不限
		dataSource.setMinIdle(minIdle); // 连接池中最小的空闲的连接数，低于这个数量会被创建新的连接
		dataSource.setMaxWait(180000); // 最大等待时间，当没有可用连接时，连接池等待连接释放的最大时间，超过该时间限制会抛出异常，如果设置-1表示无限等待
		dataSource.setRemoveAbandonedTimeout(180); // 超过时间限制，回收没有用(废弃)的连接
		dataSource.setRemoveAbandoned(true); // 超过removeAbandonedTimeout时间后，是否进 行没用连接（废弃）的回收
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setValidationQueryTimeout(180000);
		dataSource.setValidationInterval(180000);
		dataSource.setTimeBetweenEvictionRunsMillis(180000); // 检查无效连接的时间间隔 设为3分钟
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception {
		return new JdbcTemplate(dataSource());
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Logger getLogger() {
		return logger;
	}
	
}
