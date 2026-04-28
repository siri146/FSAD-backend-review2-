package com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.*;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.repository.*;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {

    @Autowired
    private RegistrationRepository regRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EventRepository eventRepo;

    // ✅ REGISTER USER TO EVENT
    @PostMapping
    public Map<String, Object> register(@RequestBody Map<String, Object> data) {

        System.out.println("🔥 REGISTER API HIT: " + data); // DEBUG

        Map<String, Object> response = new HashMap<>();

        try {
            String email = (String) data.get("email");
            Object eventObj = data.get("eventId");

            if (email == null || eventObj == null) {
                response.put("status", "error");
                response.put("message", "Email or Event ID missing");
                return response;
            }

            Long eventId = Long.valueOf(eventObj.toString());

            User user = userRepo.findByEmail(email);
            Event event = eventRepo.findById(eventId).orElse(null);

            if (user == null) {
                response.put("status", "error");
                response.put("message", "User not found");
                return response;
            }

            if (event == null) {
                response.put("status", "error");
                response.put("message", "Event not found");
                return response;
            }

            // ✅ Check duplicate registration
            List<Registration> existing = regRepo.findAll();
            for (Registration r : existing) {
                if (r.getUser().getId().equals(user.getId()) &&
                    r.getEvent().getId().equals(eventId)) {

                    response.put("status", "error");
                    response.put("message", "Already registered");
                    return response;
                }
            }

            Registration reg = new Registration(user, event);
            regRepo.save(reg);

            response.put("status", "success");
            response.put("message", "Registered successfully");

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Server error: " + e.getMessage());
        }

        return response;
    }

    // ✅ GET REGISTRATIONS BY EMAIL
    @GetMapping
    public Map<String, Object> getRegistrations(@RequestParam String email) {

        System.out.println("📥 GET API HIT: " + email); // DEBUG

        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> result = new ArrayList<>();

        User user = userRepo.findByEmail(email);

        if (user == null) {
            response.put("registrations", result);
            return response;
        }

        List<Registration> regs = regRepo.findAll();

        for (Registration r : regs) {
            if (r.getUser().getId().equals(user.getId())) {
                Map<String, String> map = new HashMap<>();
                map.put("eventTitle", r.getEvent().getTitle());
                result.add(map);
            }
        }

        response.put("registrations", result);
        return response;
    }

    // ✅ DELETE REGISTRATION
    @DeleteMapping
    public Map<String, String> delete(@RequestParam String email, @RequestParam Long eventId) {

        System.out.println("🗑 DELETE API HIT: " + email + " -> " + eventId); // DEBUG

        Map<String, String> response = new HashMap<>();

        User user = userRepo.findByEmail(email);

        if (user == null) {
            response.put("message", "User not found");
            return response;
        }

        List<Registration> regs = regRepo.findAll();

        for (Registration r : regs) {
            if (r.getUser().getId().equals(user.getId()) &&
                r.getEvent().getId().equals(eventId)) {

                regRepo.delete(r);
                response.put("message", "Deleted successfully");
                return response;
            }
        }

        response.put("message", "Registration not found");
        return response;
    }
}