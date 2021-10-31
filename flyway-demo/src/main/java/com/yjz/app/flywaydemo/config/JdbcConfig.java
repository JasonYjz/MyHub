package com.yjz.app.flywaydemo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.context.annotation.Bean;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class JdbcConfig {

    @Bean
    public JdbcTemplate initJdbc() throws PropertyVetoException, SQLException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql:///spring_database");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        return new JdbcTemplate(dataSource.getConnection());
    }
}
