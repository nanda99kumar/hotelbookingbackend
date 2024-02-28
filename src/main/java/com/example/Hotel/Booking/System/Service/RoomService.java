package com.example.Hotel.Booking.System.Service;

import com.example.Hotel.Booking.System.Entity.Room;
import com.example.Hotel.Booking.System.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room saveRoom(Room room) {
        String roomNumber = room.getRoomNumber();

        if (roomRepository.existsByRoomNumber(roomNumber)) {
            return room;
        } else {
            try {
                return roomRepository.save(room);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
