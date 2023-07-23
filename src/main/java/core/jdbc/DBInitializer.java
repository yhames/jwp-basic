package core.jdbc;

import core.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DBInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    @Inject
    private DataSource dataSource;


    public void initialize() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);

        logger.info("Completed Load ServletContext");
    }
}
