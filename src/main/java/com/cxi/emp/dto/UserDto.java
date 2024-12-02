package com.cxi.emp.dto;


import java.time.LocalDateTime;

import com.cxi.emp.enums.UserRole;

public record UserDto(
  Integer id,
  String name,
  String email,
  String organization,
  Boolean emailVerified,
  String password,
  UserRole role,
  String verificationToken,
  LocalDateTime TokenExpiry
){}
