package com.cxi.emp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("ORGANIZER")
public class Organizer extends User{
  

  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
  private List<Event> organizedEvents;

  public List<Event> getOrganizedEvents() {
    return organizedEvents;
  }

  public void setOrganizedEvents(List<Event> organizedEvents) {
    this.organizedEvents = organizedEvents;
  }

}
