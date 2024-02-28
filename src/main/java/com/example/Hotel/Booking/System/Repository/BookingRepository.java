package com.example.Hotel.Booking.System.Repository;

import com.example.Hotel.Booking.System.Entity.Booking;
import com.example.Hotel.Booking.System.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomAndFromDateLessThanEqualAndToDateGreaterThanEqual(
            Room room, LocalDate fromDate, LocalDate toDate
    );
}
