package com.batch.script.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
public class DbConfig {
    @Value("${app.db2.datasource.url}")
    private String url;
	@Value("${app.db2.datasource.username}")
    private String username;
	@Value("${app.db2.datasource.password}")
    private String password;
	@Value("${app.db2.datasource.driver-class-name}")
    private String driverName;


    @Bean(name = "dsDb2")
	public DataSource builderDb2Datasource() throws Exception {
		HikariConfig dbConfig = new HikariConfig();
		dbConfig.setJdbcUrl(url);
		dbConfig.setDriverClassName(driverName);
		dbConfig.setUsername(username);
		dbConfig.setPassword(password);
		return new HikariDataSource(dbConfig);
	}

    @Bean(name = "jdbcDb2Template")
    @Primary
	public JdbcTemplate jdbcDb2Template(@Qualifier("dsDb2") DataSource dsDb2) {
		return new JdbcTemplate(dsDb2);
	}

    @Bean(name = "db2Transaction")
    @Primary
    public PlatformTransactionManager db2Transaction(@Qualifier("dsDb2") DataSource dsDb2) throws Exception  {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dsDb2);
        return transactionManager;
    }
}
