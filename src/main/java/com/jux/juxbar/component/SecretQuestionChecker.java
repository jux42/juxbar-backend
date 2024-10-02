package com.jux.juxbar.component;


import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.service.JuxBarUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecretQuestionChecker {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JuxBarUserRepository juxBarUserRepository;
    private final JuxBarUserService juxBarUserService;


    public String checkSecretAnswer(String username,
                                    String secretQuestion,
                                    String secretAnswer,
                                    String password) {
        JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);

        if (secretQuestion.equals(juxBarUser.getSecretQuestion()) &&
                bCryptPasswordEncoder.matches(secretAnswer, juxBarUser.getSecretAnswer())) {
            recoverPassword(juxBarUser, password);
            return "Password successfully changed";
        }

        System.out.println("Submitted Answer: " + secretAnswer);
        System.out.println("Hashed Answer in DB: " + juxBarUser.getSecretAnswer());
        return "You were not allowed to change your password, please contact your administrator";
    }

    private void recoverPassword(JuxBarUser juxBarUser, String password) {
        juxBarUser.setPassword(bCryptPasswordEncoder.encode(password));
        juxBarUserRepository.save(juxBarUser);
    }
}
