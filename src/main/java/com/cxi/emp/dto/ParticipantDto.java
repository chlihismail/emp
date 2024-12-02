package com.cxi.emp.dto;

import java.util.List;

import com.cxi.emp.entity.Event;
import com.cxi.emp.enums.UserRole;

public record ParticipantDto(
  Integer id,
  String name,
  String email,
  String organization,
  Boolean emailVerified,
  UserRole role,
  List<Event> registredEvents
){}
