package com.cxi.emp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxi.emp.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer>{
  Optional<Participant> findByEmail(String email); 
}
