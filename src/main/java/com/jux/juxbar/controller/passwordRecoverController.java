package com.jux.juxbar.controller;


import com.jux.juxbar.component.SecretQuestionChecker;
import com.jux.juxbar.model.JuxBarUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class passwordRecoverController {

    private final SecretQuestionChecker secretQuestionChecker;


    @PostMapping("recover/{username}")
    public ResponseEntity<String> recoverPassword(@PathVariable String username,
                                                  @RequestParam String secretQuestion,
                                                  @RequestParam String secretAnswer,
                                                  @RequestParam String password) {

        return ResponseEntity.ok(secretQuestionChecker.checkSecretAnswer(username, secretQuestion, secretAnswer, password));

    }
}
