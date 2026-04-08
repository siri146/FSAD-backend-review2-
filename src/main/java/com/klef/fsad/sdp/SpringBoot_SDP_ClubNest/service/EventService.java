package com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.Event;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.User;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.Registration;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.repository.EventRepository;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.repository.UserRepository;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.repository.RegistrationRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RegistrationRepository regRepo;

    // ✅ Register user to event
    public String registerUser(Long userId, Long eventId) {

        User user = userRepo.findById(userId).orElse(null);
        Event event = eventRepo.findById(eventId).orElse(null);

        if (user == null || event == null) {
            return "User or Event not found";
        }

        Registration reg = new Registration(user, event);
        regRepo.save(reg);

        return "Registered Successfully!";
    }

    // ✅ Get number of users registered
    public int getRegisteredCount(Long eventId) {
        return regRepo.countByEventId(eventId);
    }

    // ✅ Get seats left
    public int getSeatsLeft(Long eventId) {
        Event event = eventRepo.findById(eventId).orElse(null);

        if (event == null) {
            return 0;
        }

        int registered = regRepo.countByEventId(eventId);
        return event.getTotalSeats() - registered;
    }
}