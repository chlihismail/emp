package com.cxi.emp.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cxi.emp.enums.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private LocalDate date;
  @Column(nullable = false)
  private LocalTime time;
  @Column(nullable = false)
  private String place;
  @Column(nullable = false)
  private Integer capacity;
  @Column(nullable = false)
  private Integer price;
  @Enumerated(EnumType.STRING)
  private EventType type;
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "organizer_id", nullable = false)
  private Organizer organizer;
  @ManyToMany(mappedBy = "registredEvents")
  private List<Participant> participants;

  public Integer getId() {
    return Id;
  }
  public void setId(Integer id) {
    Id = id;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public LocalDate getDate() {
    return date;
  }
  public void setDate(LocalDate date) {
    this.date = date;
  }
  public String getPlace() {
    return place;
  }
  public void setPlace(String place) {
    this.place = place;
  }
  public Integer getCapacity() {
    return capacity;
  }
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }
  public EventType getType() {
    return type;
  }
  public void setType(EventType type) {
    this.type = type;
  }
  public LocalTime getTime() {
    return time;
  }
  public void setTime(LocalTime time) {
    this.time = time;
  }
  public Organizer getOrganizer() {
    return organizer;
  }
  public void setOrganizer(Organizer organizer) {
    this.organizer = organizer;
  }
  public List<Participant> getParticipants() {
    return participants;
  }
  public void setParticipants(List<Participant> participants) {
    this.participants = participants;
  }
   public Integer getPrice() {
    return price;
  }
  public void setPrice(Integer price) {
    this.price = price;
  }



  
}
