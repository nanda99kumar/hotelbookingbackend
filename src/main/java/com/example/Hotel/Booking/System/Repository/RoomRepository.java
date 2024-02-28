package com.example.Hotel.Booking.System.Repository;

import com.example.Hotel.Booking.System.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.isAvailable = true AND NOT EXISTS " +
            "(SELECT b FROM Booking b WHERE b.room = r AND :toDate >= b.fromDate AND :fromDate <= b.toDate)")
    Optional<Room> findFirstAvailableRoom(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    boolean existsByRoomNumber(String roomNumber);
}
