package com.cxi.emp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cxi.emp.entity.Event;
import com.cxi.emp.entity.Participant;
import com.cxi.emp.repository.EventRepository;
import com.cxi.emp.repository.ParticipantRepository;

@Service
public class EventService{
  @Autowired
  private EventRepository eventRepository;
  @Autowired
  private ParticipantRepository participantRepository;

  public List<Event> getAllEvents(){
    return  eventRepository.findAll();
  }

  public Optional<Event> getEventById(Integer id){
    return eventRepository.findById(id);
  }

  public void saveEvent(Event event){
    eventRepository.save(event);
  }

  public void deleteEvent(Integer id){
    Event event = eventRepository.findById(id).orElseThrow();
    eventRepository.delete(event);
  }

  public void updateEvent(Event event) {
    eventRepository.save(event);
  }

  public void addParticipantToEvent(Integer eventId){        
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    Participant participant = participantRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")) ;
    Event event = eventRepository.findById(eventId)
    .orElseThrow(() -> new RuntimeException("Event not found"));

    if (event.getParticipants().size() >= event.getCapacity()) {
      throw new RuntimeException("Event is at full capacity");
    }
    if (event.getParticipants().contains(participant)) {
      throw new RuntimeException("You are already a participant in this event");
    }
    event.getParticipants().add(participant);
    participant.getRegistredEvents().add(event);
    event.setCapacity(event.getCapacity() - 1 );
    eventRepository.save(event);
  }
}

