package com.seriesxevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeriesXEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeriesXEventsApplication.class, args);
        System.out.println("\n==========================================");
        System.out.println("  SeriesX Events is running!");
        System.out.println("  Open: http://localhost:8082");
        System.out.println("==========================================\n");
    }
}
