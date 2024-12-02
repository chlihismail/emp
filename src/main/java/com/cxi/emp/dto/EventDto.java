package com.cxi.emp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cxi.emp.entity.Organizer;
import com.cxi.emp.enums.EventType;

public record EventDto(
  Integer id,
  String title,
  String description,
  LocalDate date,
  LocalTime time,
  String place,
  Integer capacity,
  Integer price,
  EventType type,
  Organizer organizer
){}
