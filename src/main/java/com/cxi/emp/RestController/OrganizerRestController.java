package com.cxi.emp.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxi.emp.dto.EventDto;
import com.cxi.emp.dto.EventMapper;
import com.cxi.emp.entity.Organizer;
import com.cxi.emp.repository.EventRepository;
import com.cxi.emp.repository.OrganizerRepository;
import com.cxi.emp.service.EventService;

@RestController
@RequestMapping("/api/organizer")
public class OrganizerRestController{
  @Autowired
  private EventService eventService;
  @Autowired
  private OrganizerRepository organizerRepository;
  @Autowired
  private EventRepository eventRepository;

    @PostMapping("/event-form")
    public ResponseEntity<String> createEvent(@RequestBody EventDto eventDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((com.cxi.emp.entity.User) authentication.getPrincipal()).getUsername();
        Organizer organizer = organizerRepository.findByEmail(username);

        if (organizer != null) {
            eventDto = new EventDto(
                eventDto.id(),
                eventDto.title(),
                eventDto.description(),
                eventDto.date(),
                eventDto.time(),
                eventDto.place(),
                eventDto.capacity(),
                eventDto.price(),
                eventDto.type(),
                organizer
            );
        } else {
            return ResponseEntity.badRequest().body("Organizer not found.");
        }

        eventService.saveEvent(EventMapper.mapToEvent(eventDto));
        return ResponseEntity.ok("Event created successfully.");
    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Integer id) {
        if (eventRepository.existsById(id)) {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Event not found.");
        }
    }

    @PutMapping("/update-event/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Integer id, @RequestBody EventDto eventDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((com.cxi.emp.entity.User) authentication.getPrincipal()).getUsername();
        Organizer organizer = organizerRepository.findByEmail(username);

        if (organizer != null) {
            eventDto = new EventDto(
                id,
                eventDto.title(),
                eventDto.description(),
                eventDto.date(),
                eventDto.time(),
                eventDto.place(),
                eventDto.capacity(),
                eventDto.price(),
                eventDto.type(),
                organizer
            );
        } else {
            return ResponseEntity.badRequest().body("Organizer not found.");
        }

        eventService.saveEvent(EventMapper.mapToEvent(eventDto));
        return ResponseEntity.ok("Event updated successfully.");
    }

}
