package com.cxi.emp.dto;

import com.cxi.emp.entity.Organizer;
import com.cxi.emp.entity.Participant;

public class UserMapper{

  public static ParticipantDto mapToParticipantDto(Participant participant){
    return new ParticipantDto(
      participant.getId(),
      participant.getName(),
      participant.getEmail(),
      participant.getOrganization(),
      participant.isEmailVerified(),
      participant.getRole(),
      participant.getRegistredEvents()
    );
  }
   public static OrganizerDto mapToOrganizerDto(Organizer organizer){
    return new OrganizerDto(
     organizer.getId(),
     organizer.getName(),
     organizer.getEmail(),
     organizer.getOrganization(),
     organizer.isEmailVerified(),
     organizer.getRole(),
     organizer.getOrganizedEvents()
    );
  }
}
