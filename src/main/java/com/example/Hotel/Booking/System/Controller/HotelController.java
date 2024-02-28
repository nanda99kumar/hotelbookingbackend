package com.example.Hotel.Booking.System.Controller;

import com.example.Hotel.Booking.System.Entity.Booking;
import com.example.Hotel.Booking.System.Entity.Room;
import com.example.Hotel.Booking.System.Service.BookingService;
import com.example.Hotel.Booking.System.Service.RoomService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/apicall/hotel")
public class HotelController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;



    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookRoom(@RequestBody Booking booking) throws BadRequestException {
        ResponseEntity<String> responseEntity = bookingService.bookRoom(booking);
        return responseEntity;
    }

    @PostMapping("/addRoom")
    public Room saveRoom(@RequestBody Room room) {
        Room savedRoom = roomService.saveRoom(room);
        return savedRoom;
    }
}
