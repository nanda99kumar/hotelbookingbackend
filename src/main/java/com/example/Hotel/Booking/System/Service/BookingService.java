package com.example.Hotel.Booking.System.Service;

import com.example.Hotel.Booking.System.Entity.Booking;
import com.example.Hotel.Booking.System.Entity.Room;
import com.example.Hotel.Booking.System.Entity.School;
import com.example.Hotel.Booking.System.Repository.BookingRepository;
import com.example.Hotel.Booking.System.Repository.RoomRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public ResponseEntity<String> bookRoom(Booking booking) {

        //School scholl=new School();
        Room room = booking.getRoom();
        LocalDate fromDate = booking.getFromDate();
        LocalDate toDate = booking.getToDate();
        Room availableRoom = findNextAvailableRoom(fromDate, toDate);
        String toEmail=booking.getEmail();
        String mobileNumber=booking.getMobileNumber();

        if (availableRoom == null) {
            return ResponseEntity.badRequest().body("No available rooms for the selected dates");
        }

        booking.setRoom(availableRoom);

        try {
            // Attempt to save the booking
            Booking booked = bookingRepository.save(booking);

            // Check if the booking was successful (i.e., if 'booked' is not null)
            if (booked != null) {
                String body = "Dear " + booking.getCustomerName() + ". \n\n  Room booked successfully with ID:" + booked.getId() + "\n\n";
                body=body+" Here are the details of your booking from "+booking.getFromDate()+" to "+booking.getToDate()+ " \n\n";
                //body=body+"Room ID: "+room.getId()+"\n\n" ;
                body=body+"Registered Email"+booked.getEmail() +"\n\n" ;
                body=body+"Registered Name"+booked.getCustomerName()+"\n\n" ;
                body=body+ "Mobile Number"+booked.getMobileNumber()+"\n\n" ;

                body = body + "Thanks for choosing Nanda Hotel Booking Application. Have a pleasure day!! Kindly choose us again";

                // Send the email only if the booking is successful
                emailService.sendEmail(toEmail, "Booking successful", body);


               // System.out.println("in booking service" + mobileNumber );
             //   smsService.sendSMS(mobileNumber,body);


                return ResponseEntity.ok().body("Room booked successfully with ID:" + booked.getId());
            } else {
                // Handle the case where the booking was not successful
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Booking failed");
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during the booking process
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Booking failed");
        }





    }
    private Room findNextAvailableRoom(LocalDate fromDate, LocalDate toDate) {
        List<Room> allRooms = roomRepository.findAll();

        for (Room room : allRooms) {
            if (isRoomAvailable(room, fromDate, toDate)) {
                return room;
            }
        }

        return null; // No available rooms found
    }

    private boolean isRoomAvailable(Room room, LocalDate fromDate, LocalDate toDate) {
        if (!room.isAvailable()) {
            return false; // Room is marked as unavailable
        }

        List<Booking> existingBookings = bookingRepository.findByRoomAndFromDateLessThanEqualAndToDateGreaterThanEqual(room, toDate, fromDate);

        return existingBookings.isEmpty();
    }
}