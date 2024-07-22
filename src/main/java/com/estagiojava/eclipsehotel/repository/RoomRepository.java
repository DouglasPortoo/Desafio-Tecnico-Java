package com.estagiojava.eclipsehotel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estagiojava.eclipsehotel.model.Room;

public interface RoomRepository extends JpaRepository<Room, UUID> {
  
}
