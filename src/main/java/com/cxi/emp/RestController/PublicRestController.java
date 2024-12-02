package com.cxi.emp.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxi.emp.entity.Event;
import com.cxi.emp.service.EventService;

@RestController
@RequestMapping("/api")
public class PublicRestController{

  @Autowired
  private EventService eventService;

  @GetMapping("/events")
  public ResponseEntity<List<Event>> getAllPublicEvents() {
    List<Event> events = eventService.getAllEvents();
    return ResponseEntity.ok(events);
  }
}
