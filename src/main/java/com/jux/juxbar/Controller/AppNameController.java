package com.jux.juxbar.Controller;

import com.jux.juxbar.Configuration.AppName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class AppNameController {

    private final AppName appName;

    public AppNameController(AppName appName) {
        this.appName = appName;
    }


    @GetMapping("/appname")
    String getAppName(){
        System.out.println(appName.getName());
        return this.appName.getName();
    }


}
