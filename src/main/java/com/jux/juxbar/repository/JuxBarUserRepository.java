package com.jux.juxbar.repository;

import com.jux.juxbar.model.JuxBarUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuxBarUserRepository extends JpaRepository<JuxBarUser, Long> {

    JuxBarUser findByUsername(String username);

}
