package com.yazaki.yazaki;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import com.smattme.MysqlExportService;
import com.yazaki.yazaki.domain.config.authentication.JwtAuthenticationFilter;
import com.yazaki.yazaki.domain.scheduled.db.MySqlPropertiesProvider;
import com.yazaki.yazaki.domain.scheduled.report.SimpleReportExporter;
import com.yazaki.yazaki.domain.scheduled.report.SimpleReportFiller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
public class YazakiApplication {

    @Autowired
    private MySqlPropertiesProvider mySqlPropertiesProvider;

    public static void main(String[] args) {
        SpringApplication.run(YazakiApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BeanWrapperImpl getBeanWrapperImpl() { return new BeanWrapperImpl(); }

    @Bean
    public ModelMapper getModelMapper() { return new ModelMapper(); }

    @Bean
    public JwtAuthenticationFilter getJwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    @Primary
    public CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public MysqlExportService getMysqlExportService() {

        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.DB_NAME, mySqlPropertiesProvider.getDbName());
        properties.setProperty(MysqlExportService.DB_USERNAME, mySqlPropertiesProvider.getUsername());
        properties.setProperty(MysqlExportService.DB_PASSWORD, mySqlPropertiesProvider.getPassword());
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
        properties.setProperty(MysqlExportService.TEMP_DIR, new File("backup").getPath());

       return new MysqlExportService(properties);
    }

}
