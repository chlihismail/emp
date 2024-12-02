package com.cxi.emp.controller;


import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxi.emp.entity.Event;
import com.cxi.emp.entity.User;
import com.cxi.emp.service.EventService;
import com.cxi.emp.service.UserService;

@Controller
@RequestMapping("/")
public class PublicController{
  
  @Autowired
  private EventService eventService;
  @Autowired
  private UserService userService;

  @GetMapping
  public String publicPage(Model model, Principal principal){
    List<Event> events = eventService.getAllEvents()
      .stream()
      .collect(Collectors.collectingAndThen(Collectors.toList(), l->{
      Collections.reverse(l);
      return l;}));
    model.addAttribute("events", events);
    if (principal != null) {
      User user = userService.getUserByEmail(principal.getName()).orElseThrow(()->new RuntimeException("User not found"));
      model.addAttribute("user", user);
    }
    return "public";
  }

  @GetMapping("/error")
  public String errorPage(){
    return "error";
  }

}
