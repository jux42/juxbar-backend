package com.jux.juxbar.service;


import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.repository.JuxBarUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final JuxBarUserRepository juxBarUserRepository;
    private final ImageCompressor imageCompressor;


    public JuxBarUser getCurrentUser(String username) {

        return juxBarUserRepository.findByUsername(username);

    }

    public String updateAboutMeText(String username, String aboutMeText) {

        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(username);
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




}
