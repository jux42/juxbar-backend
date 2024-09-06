package com.jux.juxbar.controller;

import com.jux.juxbar.configuration.AppName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController

public class AppNameController {

    private final AppName appName;

    public AppNameController(AppName appName) {
        this.appName = appName;
    }


    @GetMapping("/appname")
    String getAppName() {
        log.info(appName.getName());
        if(appName.getName() == null) {
            return "???";
        }
        return this.appName.getName();
    }


}
