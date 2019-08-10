package org.money.wiki.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource){
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .baselineVersion(MigrationVersion.EMPTY)
                .load();
    }
}
