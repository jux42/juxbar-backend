package com.jux.juxbar.Controller;

import com.jux.juxbar.Configuration.AppName;
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
    String getAppName(){
        log.info(appName.getName());
        return this.appName.getName();
    }


}
