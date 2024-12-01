package com.cxi.emp.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cxi.emp.config.TokenGenerator;
import com.cxi.emp.entity.Organizer;
import com.cxi.emp.entity.Participant;

public class UserDtoMapper{
  public static Participant mapToParticipant(UserDto userDto){
    // Boolean emailVerified = (userDto.emailVerified() != null) ? userDto.emailVerified() : false;
    Participant participant = new Participant();
    participant.setId(userDto.id());
    participant.setName(userDto.name());
    participant.setEmail(userDto.email());
    participant.setOrganization(userDto.organization());
    participant.setEmailVerified(userDto.emailVerified() != null ? userDto.emailVerified() : false);
    participant.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
    participant.setRole(userDto.role());
    participant.setRegistredEvents(new ArrayList<>());
    String token = TokenGenerator.generateToken();
    participant.setVerificationToken(token);
    participant.setTokenExpiry(LocalDateTime.now().plusHours(24));
    return participant;
  }

 public static Organizer mapToOrganizer(UserDto userDto){
    // Boolean emailVerified = (userDto.emailVerified() != null) ? userDto.emailVerified() : false;
    Organizer organizer = new Organizer();
    organizer.setId(userDto.id());
    organizer.setName(userDto.name());
    organizer.setEmail(userDto.email());
    organizer.setOrganization(userDto.organization());
    organizer.setEmailVerified(userDto.emailVerified() != null ? userDto.emailVerified() : false);
    organizer.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
    organizer.setRole(userDto.role());
    organizer.setOrganizedEvents(new ArrayList<>());
    String token = TokenGenerator.generateToken();
    organizer.setVerificationToken(token);
    organizer.setTokenExpiry(LocalDateTime.now().plusHours(24));
    return organizer;
  }
}
