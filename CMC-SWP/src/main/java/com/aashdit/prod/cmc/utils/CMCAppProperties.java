package com.aashdit.prod.cmc.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "cmc")
@PropertySource(value = "classpath:application.properties")
@Data
public class CMCAppProperties 
{
    private String path;
    private String geoTagFilePath;
    private Boolean auth;
    private Boolean enable;
    private String host;
    private String trust;
    private String port;
    private String protocol;
    private String username;
    private String password;
    private String appPassword;
    private String msgSender;
    private String deletedFilePath;
    
    private String fileUploadPath;
    private String fileSourcePathRH;
    
}


