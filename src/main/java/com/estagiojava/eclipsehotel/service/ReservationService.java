package com.estagiojava.eclipsehotel.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estagiojava.eclipsehotel.model.Reservation;
import com.estagiojava.eclipsehotel.model.Reservation.ReservationStatus;
import com.estagiojava.eclipsehotel.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(UUID id) {
        return reservationRepository.findById(id);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteById(UUID id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> findAllByCheckinBetween(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findAllByCheckinBetween(startDate, endDate);
    }

    public List<Reservation> findAllByStatus(ReservationStatus status) {
        return reservationRepository.findAllByStatus(status);
    }

}