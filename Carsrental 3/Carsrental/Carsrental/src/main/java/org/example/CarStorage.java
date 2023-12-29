package org.example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
@Component
public class CarStorage {

    private List<Cars> cars = new ArrayList<>();


    public void addCar(Cars car) {
        cars.add(car);
    }

    public List<Cars> getAllCars() {
        return cars;
    }

    public Cars getCarByVin(String vin) {
        for (Cars car : cars) {
            if (car.getVin().equals(vin)) {
                return car;
            }
        }
        return null; // Samochód o podanym VIN nie istnieje
    }

}
