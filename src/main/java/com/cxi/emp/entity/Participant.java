package com.cxi.emp.entity;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("PARTICIPANT")
public class Participant extends User{

  @ManyToMany
  @JoinTable(
  name = "event_participants",
  joinColumns = @JoinColumn(name = "participant_id"),
  inverseJoinColumns = @JoinColumn(name = "event_id")
  )
  private List<Event> registredEvents;

  public List<Event> getRegistredEvents() {
    return registredEvents;
  }

  public void setRegistredEvents(List<Event> registredEvents) {
    this.registredEvents = registredEvents;
  }


}
