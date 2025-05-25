package br.dev.ridanfaust.controleestoque.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataInitializationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final DataSource dataSource;

    @Value("classpath:db/init-data.sql")
    private Resource dataScript;

    public DataInitializationListener(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(dataScript);
        databasePopulator.execute(dataSource);
    }
}
