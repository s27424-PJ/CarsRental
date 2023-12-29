package org.example;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class RentalStorage {

    private List<Rental> rentals = new ArrayList<>();


    public void addRental(Rental rental) {
        rentals.add(rental);
    }
    public List<Rental> getAllRents() {
        return rentals;
    }

    public boolean getCarByVin(String vin) {
        for (Rental rental : rentals) {
            if (rental.getCar().getVin().equals(vin)) {
                return true;
            }
        }
        return false; // Samochód o podanym VIN nie istnieje
    }
}
