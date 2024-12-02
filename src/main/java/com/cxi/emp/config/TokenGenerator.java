package com.cxi.emp.config;

import java.util.UUID;

public class TokenGenerator{
  public static String generateToken(){
      return UUID.randomUUID().toString();
  }
}
