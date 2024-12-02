package com.cxi.emp.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxi.emp.dto.UserDto;
import com.cxi.emp.entity.User;
import com.cxi.emp.repository.UserRepository;
import com.cxi.emp.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthRestController{
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
    if (userService.isEmailRegistred(userDto.email())) {
      return ResponseEntity.badRequest().body("Email is already registered.");
    }
    if (!isValidEmailDomain(userDto.email())) {
      return ResponseEntity.badRequest().body("Invalid email domain.");
    }
    try {
      userService.registerUser(userDto);
      return ResponseEntity.ok("User registered successfully! Please sign in.");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid information provided.");
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<String> signIn(@RequestBody UserDto userDto) {
    try {
      UsernamePasswordAuthenticationToken authenticationToken = 
      new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password());
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return ResponseEntity.ok("Sign-in successful!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid login credentials.");
    }
  }


  @GetMapping("/verify-email")
  public ResponseEntity<String> verifyEmail(@RequestParam String token) {
    Optional<User> userOpt = userService.findByVerificationToken(token);

    if (userOpt.isPresent()) {
      User user = userOpt.get();
      if (user.getTokenExpiry().isAfter(LocalDateTime.now())) {
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);
        return ResponseEntity.ok("Email verified successfully!");
      } else {
        return ResponseEntity.badRequest().body("Token expired. Please register again.");
      }
    }
    return ResponseEntity.badRequest().body("Invalid token.");
  }

  private boolean isValidEmailDomain(String email) {
    String[] emailParts = email.split("@");
    if (emailParts.length != 2) return false;

    String domain = emailParts[1];
    try {
      InetAddress.getByName(domain);
      return true;
    } catch (UnknownHostException e) {
      return false;
    }
  }

}
