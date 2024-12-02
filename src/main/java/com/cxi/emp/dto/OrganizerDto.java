package com.cxi.emp.dto;

import java.util.List;

import com.cxi.emp.entity.Event;
import com.cxi.emp.enums.UserRole;

public record OrganizerDto(
  Integer id,
  String name,
  String email,
  String organization,
  Boolean emailVerified,
  UserRole role,
  List<Event> organizedEvents
){}
