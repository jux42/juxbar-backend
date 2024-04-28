package com.jux.juxbar.Repository;

import com.jux.juxbar.Model.JuxBarUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuxBarUserRepository extends JpaRepository<JuxBarUser, Long> {

    JuxBarUser findByUsername(String username);
}
