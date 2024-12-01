package com.cxi.emp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cxi.emp.dto.EventDto;
import com.cxi.emp.dto.UserDto;
import com.cxi.emp.entity.Event;
import com.cxi.emp.entity.User;
import com.cxi.emp.repository.UserRepository;
import com.cxi.emp.service.EventService;
import com.cxi.emp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PublicController{
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private EventService eventService;

  @GetMapping
  public String publicPage(Model model){
    List<Event> events = eventService.getAllEvents();
    model.addAttribute("events", events);
    return "public";
  }

  



  

  
 
}
