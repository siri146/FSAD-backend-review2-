package com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    int countByEventId(Long eventId);
}