package com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}