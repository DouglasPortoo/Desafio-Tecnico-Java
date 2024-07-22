package com.estagiojava.eclipsehotel.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estagiojava.eclipsehotel.model.Reservation;
import com.estagiojava.eclipsehotel.model.Reservation.ReservationStatus;
import com.estagiojava.eclipsehotel.service.ReservationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @PostMapping
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        UUID roomID = reservation.getRoom().getId();
        LocalDate checkin = reservation.getCheckin();
        LocalDate checkout = reservation.getCheckout();

        try {

            if (checkin.isBefore(LocalDate.now())) {
                logger.warn("Check-in date {} is before the current date", checkin);
                return ResponseEntity.badRequest().body("Check-in date is before the current date");
            }

            if (checkout.isBefore(checkin) || checkout.isEqual(checkin)) {
                logger.warn("Check-out date {} is before the check-in date {}", checkout, checkin);
                return ResponseEntity.badRequest().body("Check-out date is before the check-in date");
            }

            List<Reservation> tudo = reservationService.findAll();

            List<Reservation> reservas = tudo.stream().filter(r -> r.getRoom().getId().equals(roomID))
                    .collect(Collectors.toList());

            for (Reservation r : reservas) {
                if (r.getStatus() == ReservationStatus.IN_USE || r.getStatus() == ReservationStatus.SCHEDULED) {
                    if (checkin.isBefore(r.getCheckout()) && checkout.isAfter(r.getCheckin())) {
                        logger.warn("Room {} is already reserved between {} and {}", roomID, r.getCheckin(),
                                r.getCheckout());
                        return ResponseEntity.badRequest().body("Room is already reserved between " + r.getCheckin()
                                + " and " + r.getCheckout());
                    }
                }
            }

            reservation.setStatus(ReservationStatus.SCHEDULED);
            Reservation savedReservation = reservationService.save(reservation);
            logger.info("Reservation created: {}", savedReservation);
            return ResponseEntity.ok(savedReservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/close/{id}")
    public ResponseEntity<Object> closeReservation(@PathVariable UUID id) {
        logger.info("Closing reservation with id: {}", id);
        Optional<Reservation> reservationOpt = reservationService.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            if (reservation.getStatus() == ReservationStatus.FINISHED ||
                    reservation.getStatus() == ReservationStatus.CANCELED ||
                    reservation.getStatus() == ReservationStatus.ABSENCE) {
                logger.warn("Attempted to update final status reservation: {}", reservation);
                return ResponseEntity.status(409).build();
            }
            reservation.setStatus(ReservationStatus.FINISHED);
            logger.info("Reservation closed: {}", reservation);
            return ResponseEntity.ok(reservationService.save(reservation));
        }
        logger.warn("Reservation not found with id: {}", id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/date-range")
    public ResponseEntity<Object> findAllByCheckinBetween(@RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        try {
            logger.info("Finding reservations between {} and {}", startDate, endDate);
            List<Reservation> reservations = reservationService.findAllByCheckinBetween(startDate, endDate);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/occupied-rooms")
    public ResponseEntity<Object> findOccupiedRooms() {
        try {
            logger.info("Finding all occupied rooms");
            List<Reservation> reservations = reservationService.findAllByStatus(ReservationStatus.IN_USE);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
