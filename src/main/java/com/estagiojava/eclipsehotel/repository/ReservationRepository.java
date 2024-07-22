package com.estagiojava.eclipsehotel.repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estagiojava.eclipsehotel.model.Reservation;
import com.estagiojava.eclipsehotel.model.Reservation.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
  List<Reservation> findAllByCheckinBetween(LocalDate startDate, LocalDate endDate);
  List<Reservation> findAllByStatus(ReservationStatus status);
}

