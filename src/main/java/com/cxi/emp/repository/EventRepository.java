package com.cxi.emp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxi.emp.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
}
