package com.pawkorchagin.translate.repository.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class RepositoryConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasoursce.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.script-file-path}")
    private String scriptFilePath;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
}
