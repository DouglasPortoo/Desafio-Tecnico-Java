package com.estagiojava.eclipsehotel.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estagiojava.eclipsehotel.model.Room;
import com.estagiojava.eclipsehotel.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

  private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

  @Autowired
  private RoomService roomService;

  @GetMapping
  public ResponseEntity<Object> getAllRooms() {
    logger.info("Fetching all rooms");
    try {
      var result = roomService.findAll();
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("Error fetching all rooms: {}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getRoomById(@PathVariable UUID id) {
    logger.info("Fetching room with id: {}", id);
    try {
      var result = roomService.findById(id);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("Error fetching room with id {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<Object> createRoom(@RequestBody Room room) {
    logger.info("Creating new room");
    try {
      var result = roomService.save(room);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("Error creating room: {}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateRoom(@PathVariable UUID id, @RequestBody Room room) {
    logger.info("Updating room with id: {}", id);
    try {
      var result = roomService.update(id, room);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("Error updating room with id {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteCustomer(@PathVariable UUID id) {
    logger.info("Deleting room with id: {}", id);
    try {
      roomService.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      logger.error("Error deleting room with id {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
