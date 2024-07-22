package com.estagiojava.eclipsehotel.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;

  private LocalDate checkin;
  private LocalDate checkout;

  @Enumerated(EnumType.STRING)
  private ReservationStatus status;

  public enum ReservationStatus {
    SCHEDULED,
    IN_USE,
    ABSENCE,
    FINISHED,
    CANCELED
  }

  public UUID getId() {
    return id;
  }   

  public Customer getCustomer() {
    return customer;
  }


  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public LocalDate getCheckin() {
    return checkin;
  }

  public void setCheckin(LocalDate checkin) {
    this.checkin = checkin;
  }

  public LocalDate getCheckout() {
    return checkout;
  }

  public void setCheckout(LocalDate checkout) {
    this.checkout = checkout;
  }

  public ReservationStatus getStatus() {
    return status;
  }

  public void setStatus(ReservationStatus status) {
    this.status = status;
  }

}
