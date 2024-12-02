package com.cxi.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxi.emp.dto.UserDto;
import com.cxi.emp.service.UserService;


import java.net.InetAddress;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cxi.emp.entity.User;
import com.cxi.emp.repository.UserRepository;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class AuthController {

  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @PostMapping("/signup")
  public String signUp(@ModelAttribute UserDto userDto, Model model) {
    if(userService.isEmailRegistred(userDto.email())){
      model.addAttribute("error", "Email is already registred.");
      return "signup";
    }
    if(!isValidEmailDomain(userDto.email())){
      model.addAttribute("error", "Invalid email domain.");
      return "signup";
    }
    try{
      userService.registerUser(userDto);
      return "redirect:/signin";
    } catch(Exception e){
      model.addAttribute("error", "Invalid informations");
      return "signup";
    }
  }

  @GetMapping("/signin")
  public String signIn(){
    return "signin";
  }


  @GetMapping("/signup")
  public String signUp(){
    return "signup";
  }

  private boolean isValidEmailDomain(String email){
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
        return ResponseEntity.ok("Email verified successfully! You can now safely close this tab.");
      } else {
        return ResponseEntity.badRequest().body("Token expired. Please register again.");
      }
    }
    return ResponseEntity.badRequest().body("Invalid token.");
  }

}
