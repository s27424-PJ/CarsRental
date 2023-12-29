package org.example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import java.util.List;
import java.time.LocalDate;
@Service
public class RentalService {
    private final CarStorage carStorage;
    private final RentalStorage rentalStorage;

    public RentalService(CarStorage carStorage, RentalStorage rentalStorage) {
       this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
    }

    public Rental rent(int id, String vin, LocalDate dateFrom, LocalDate dateTo) {
        List<Cars> cars = carStorage.getAllCars();
        if (dateTo.isBefore(dateFrom)) {

            throw new RuntimeException("Data końcowa jest wcześniejsza niż data początkowa");
        }
        for (Cars car : cars) {

            if (car.getVin().equals(vin)) {

                if (!isAvailable(vin, dateFrom, dateTo)) {
                    return null; // Samochód jest już wypożyczony w tym terminie
                }

                Rental rental = new Rental(id, car, dateFrom, dateTo);
                rentalStorage.addRental(rental);
                return rental;
            }
        }

        return null; // Samochód o podanym VIN nie istnieje
    }

    public boolean carExists(String vin) {
        Cars car = carStorage.getCarByVin(vin);
        return car != null;
    }

    public boolean isAvailable(String vin, LocalDate dateFrom, LocalDate dateTo) {
        if (dateTo.isBefore(dateFrom)) {
            throw new RuntimeException("Data końcowa jest wcześniejsza niż data początkowa");

        }
        List<Rental> rentals = rentalStorage.getAllRents();

        for (Rental rental : rentals) {
            if (rental.getCar().getVin().equals(vin)) {
                LocalDate existingDateFrom = rental.getDateFrom();
                LocalDate existingDateTo = rental.getDateTo();

                if (!(dateTo.isBefore(existingDateFrom) || dateFrom.isAfter(existingDateTo))) {
                    System.out.println("Samochód jest już wypożyczony w tym terminie");
                    return false;
                }

            }
        }

        return true;
    }


    public double estimatePrice(String vin, LocalDate dateFrom, LocalDate dateTo) {
        if (!carExists(vin)) {
            return -1.0;
        }
        if (dateTo.isBefore(dateFrom)) {
            throw new RuntimeException("Data końcowa jest wcześniejsza niż data początkowa");
        }
        Cars car = carStorage.getCarByVin(vin);
        long diffInDays = java.time.temporal.ChronoUnit.DAYS.between(dateFrom, dateTo);

        double dailyRate = 100.0;
        double estimatedPrice = dailyRate * diffInDays * car.getType().getMultiplier();
        return estimatedPrice;
    }



}
