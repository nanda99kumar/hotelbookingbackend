package com.example.Hotel.Booking.System.Entity;

import org.springframework.context.annotation.Bean;
//This class is not used anywhere and not requeired, i used this to learn about constructro ands etter injectiosn
public class School {
    private int id;
    private String name;


    public School(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public School(int id) {
        this.id=id;
    }



}