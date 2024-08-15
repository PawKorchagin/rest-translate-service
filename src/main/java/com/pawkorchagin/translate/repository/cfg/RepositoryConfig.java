package com.pawkorchagin.translate.repository.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Configuration class for the repository layer. This class holds the database 
 * connection details and other related configuration properties, which are 
 * injected from the application's properties file.
 */
@Component
@Getter
public class RepositoryConfig {

    /** 
     * The URL of the database to which the application connects.
     */
    @Value("${spring.datasource.url}")
    private String url;

    /** 
     * The fully qualified class name of the JDBC driver to be used for database connection.
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /** 
     * The file path to the SQL script that initializes the database schema.
     */
    @Value("${spring.datasource.script-file-path}")
    private String scriptFilePath;

    /** 
     * The username for the database connection.
     */
    @Value("${spring.datasource.username}")
    private String username;

    /** 
     * The password for the database connection.
     */
    @Value("${spring.datasource.password}")
    private String password;
}
