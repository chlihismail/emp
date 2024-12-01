package com.cxi.emp.dto;

import com.cxi.emp.entity.Event;

public class EventMapper{
  public static Event mapToEvent(EventDto eventDto){
    Event event = new Event();
    event.setId(eventDto.id());
    event.setTitle(eventDto.title());
    event.setDescription(eventDto.description());
    event.setDate(eventDto.date());
    event.setTime(eventDto.time());
    event.setPlace(eventDto.place());
    event.setCapacity(eventDto.capacity());
    event.setPrice(eventDto.price());
    event.setType(eventDto.type());
    event.setOrganizer(eventDto.organizer());
    return event;
  }
}
