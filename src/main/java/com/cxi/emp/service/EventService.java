package com.cxi.emp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxi.emp.dto.EventDto;
import com.cxi.emp.dto.EventMapper;
import com.cxi.emp.entity.Event;
import com.cxi.emp.repository.EventRepository;

@Service
public class EventService{
  @Autowired
  private EventRepository eventRepository;

  public List<Event> getAllEvents(){
    return  eventRepository.findAll();
  }

  public void saveEvent(Event event){
    eventRepository.save(event);
  }

  public void deleteEvent(Integer id){
    Event event = eventRepository.findById(id)
    .orElseThrow();

    eventRepository.delete(event);
  }
  public void updateEvent(Event event) {
    eventRepository.save(event);
  }
}

