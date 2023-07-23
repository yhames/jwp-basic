package core.di.factory.example;

import core.annotation.Bean;
import core.annotation.Configuration;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

@Configuration
public class ExampleConfig {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PW = "";

    @Bean
    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(DB_DRIVER);
        ds.setUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PW);
        return ds;
    }
}