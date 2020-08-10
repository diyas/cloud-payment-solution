package com.fp.cloud.main.repository;

import com.fp.cloud.main.domain.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserViewRepo extends JpaRepository<UserView, Long> {
    UserView findByUsername(String username);
    UserView findByUsernameAndClientId(String userId, String clientId);

}
