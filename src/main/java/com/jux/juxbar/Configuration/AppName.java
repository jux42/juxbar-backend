package com.jux.juxbar.Configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data


@ConfigurationProperties(prefix = "appname")
public class AppName {
    @Value("${appname}")
    private String name;

}
