package com.cxi.emp.service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cxi.emp.dto.UserDto;
import com.cxi.emp.dto.UserDtoMapper;
import com.cxi.emp.entity.Organizer;
import com.cxi.emp.entity.Participant;
import com.cxi.emp.entity.User;
import com.cxi.emp.enums.UserRole;
import com.cxi.emp.repository.OrganizerRepository;
import com.cxi.emp.repository.ParticipantRepository;
import com.cxi.emp.repository.UserRepository;

@Service
public class UserService{
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ParticipantRepository participantRepository;
  @Autowired
  private OrganizerRepository organizerRepository;
  @Autowired
  private EmailService emailService;
  @Autowired
  private AuthenticationManager authenticationManager;

  public void registerUser(UserDto userDto){
    if(userDto.role() == UserRole.PARTICIPANT){
      Participant participant = UserDtoMapper.mapToParticipant(userDto);
      String token = participant.getVerificationToken();
      participantRepository.save(participant);
      String verificationLink = "http://localhost:8080/verify-email?token=" + token;
      emailService.sendVerificationEmail(userDto.email(), verificationLink);
    } else if(userDto.role() == UserRole.ORGANIZER){
      Organizer organizer = UserDtoMapper.mapToOrganizer(userDto);
      String token = organizer.getVerificationToken();
      organizerRepository.save(organizer);
      String verificationLink = "http://localhost:8080/verify-email?token=" + token;
      emailService.sendVerificationEmail(userDto.email(), verificationLink);
    } else{
      throw new IllegalArgumentException("Unsupported role: " + userDto.role());
    }
    
  }

  // public void signIn(String email, String password){
  // User user = userRepository.findByEmail(email)
  //               .orElseThrow(() -> new UsernameNotFoundException("Email does not exist"));

  //       if (!user.isEmailVerified()) {
  //        throw new IllegalStateException("Email is not verified");
  //       }

  //       try {
  //           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
  //       } catch (BadCredentialsException e) {
  //           throw new IllegalArgumentException("Invalid email or password");
  //       }

  // }

  public Optional<User> findByVerificationToken(String token){
    return userRepository.findByVerificationToken(token);
  }

  public boolean isEmailRegistred(String email){
    return userRepository.findByEmail(email).isPresent();
  }
}
