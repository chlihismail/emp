package com.cxi.emp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxi.emp.dto.EventDto;
import com.cxi.emp.dto.EventMapper;
import com.cxi.emp.entity.Event;
import com.cxi.emp.entity.Organizer;
import com.cxi.emp.repository.EventRepository;
import com.cxi.emp.repository.OrganizerRepository;
import com.cxi.emp.service.EventService;

@Controller
@RequestMapping("/")
public class OrganizerController{

  @Autowired
  private EventService eventService;
  @Autowired
  private OrganizerRepository organizerRepository;
  @Autowired
  private EventRepository eventRepository;
  
  @GetMapping("/event-form")
  public String eventForm(){
    return "event-form";
  }

  @PostMapping("/event-form")
  public String createEvent(@ModelAttribute EventDto eventDto, Model model) {
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
          );      } else {
          return "redirect:/error";
      }
      eventService.saveEvent(EventMapper.mapToEvent(eventDto));
      
      return "redirect:/";
  }

  @PostMapping("/delete-event/{id}")
    public String deleteEvent(@PathVariable Integer id) {
        eventService.deleteEvent(id);
        return "redirect:/";
    }

   @GetMapping("/update-event/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Optional<Event> event = eventRepository.findById(id);
        model.addAttribute("event", event);
        return "update-event";
    }

    @PostMapping("/update-event")
    public String updateEvent(@ModelAttribute EventDto eventDto) {
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
          );      } else {
          return "redirect:/error";
      }
      eventService.saveEvent(EventMapper.mapToEvent(eventDto));
      
      return "redirect:/";
    }
}
