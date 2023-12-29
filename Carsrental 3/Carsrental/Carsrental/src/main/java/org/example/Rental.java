package org.example;

import java.time.LocalDate;
import java.util.Date;

public class Rental {
    private int id;
    private Cars car;

    private LocalDate dateFrom;

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", car=" + car +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }

    private LocalDate dateTo;

    public Rental(int id, Cars car,  LocalDate dateFrom, LocalDate dateTo) {
        this.id = id;
        this.car = car;

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public Cars getCar() {
        return car;
    }



    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}
