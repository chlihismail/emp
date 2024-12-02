package com.cxi.emp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxi.emp.entity.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Integer>{
  Organizer findByEmail(String email);
}
