package com.estagiojava.eclipsehotel.service;

import java.util.UUID;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estagiojava.eclipsehotel.model.Room;
import com.estagiojava.eclipsehotel.repository.RoomRepository;

@Service
public class RoomService {

  @Autowired
  private RoomRepository roomRepository;

  public List<Room> findAll() {
    return roomRepository.findAll();
  }

  public Room findById(UUID id) {
    return roomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Room not found"));
  }

  public Room save(Room room) {
    return roomRepository.save(room);
  }

  public Room update(UUID id, Room room) {
      Room roomToUpdate = findById(id);
  
      roomToUpdate.setPrice(room.getPrice());
      roomToUpdate.setType(room.getType());
      roomToUpdate.setRoomNumber(room.getRoomNumber());
  
      return roomRepository.save(roomToUpdate);
  }

  public void deleteById(UUID id) {
    roomRepository.deleteById(id);
  }
}
