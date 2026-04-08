package com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}