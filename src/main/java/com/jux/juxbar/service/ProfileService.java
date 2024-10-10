package com.jux.juxbar.service;


import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.repository.PersonalCocktailImageRepository;
import com.jux.juxbar.repository.PersonalCocktailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final JuxBarUserRepository juxBarUserRepository;
    private final ImageCompressor imageCompressor;
    private final PersonalCocktailRepository personalCocktailRepository;
    private final PersonalCocktailImageRepository personalCocktailImageRepository;


    public JuxBarUser getCurrentUser(String username) {

        return juxBarUserRepository.findByUsername(username);

    }

    public byte[] getProfilePicture(String username) {
        return juxBarUserRepository.findByUsername(username).getProfilePicture();
    }


    public String updateAboutMeText(String username, String aboutMeText) {

        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(username);
        if (juxBarUser == null) {
            return "user"+username+" does not exist";
        }
        juxBarUser.setAboutMeText(aboutMeText);
        juxBarUserRepository.save(juxBarUser);
        return "'about me' section updated";

    }

    public String  updateProfilePicture(String username, byte[] picture) throws IOException {

                JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(username);
                juxBarUser.setProfilePicture(picture);
                juxBarUserRepository.save(juxBarUser);
                return "profile picture updated";
            }


    public String deleteAccount(String username) {

        try{
            List<PersonalCocktail> personalCocktailsToDelete = personalCocktailRepository.findByOwnerName(username);
            JuxBarUser userToDelete = juxBarUserRepository.findByUsername(username);
            personalCocktailRepository.deleteAll(personalCocktailsToDelete);
            for (PersonalCocktail personalCocktail : personalCocktailsToDelete) {
                personalCocktailImageRepository.delete(personalCocktail.getImageData());
            }
            juxBarUserRepository.delete(userToDelete);

        }catch (Exception e){
            log.error(e.getMessage());
            return e.getMessage();
        }
            return "User deleted... bye bye";

    }
}
