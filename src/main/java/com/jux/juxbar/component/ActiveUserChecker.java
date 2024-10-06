package com.jux.juxbar.component;

import com.jux.juxbar.service.JuxBarUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveUserChecker {

    private final JuxBarUserService juxBarUserService;

    public boolean checkIfActive(String username){


        return juxBarUserService.getJuxBarUserByUsername(username).isActive();

    }
}
