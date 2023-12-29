package org.example;

import org.example.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Date;
import java.util.stream.Stream;

import org.example.Cars;
import  org.example.Type;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TestRentalService {
    private CarStorage carStorage = new CarStorage();
    private RentalStorage rentalStorage =new RentalStorage();
    private RentalService rentalService = new RentalService(carStorage, rentalStorage);

    @Test
    public void testEstimate() {
        //Given
        Cars car = new Cars("XPP", "x", "1243", Type.Classic);
        carStorage.addCar(car);
        LocalDate dateFrom = LocalDate.of(2023, 9, 29);
        LocalDate dateTo = LocalDate.of(2023, 9, 26);

        //When
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> rentalService.estimatePrice(car.getVin(), dateFrom, dateTo));
    }


    @Test
    public void testAvailable() {
        Cars car = new Cars("XPP", "x", "1243", Type.Classic);
        carStorage.addCar(car);
        User user1 = new User(1);
        LocalDate dateFrom = LocalDate.of(2023, 9, 26);
        LocalDate dateTo = LocalDate.of(2023, 9, 27);

        boolean abc = rentalService.isAvailable(car.getVin(), dateFrom, dateTo);
        assertThat(abc).isTrue();
    }

    @Test
    public void testFutureReservation() {
        Cars car = new Cars("XPP", "x", "1243", Type.Classic);
        LocalDate futureDate = LocalDate.now().plusDays(1);
        LocalDate futureDate2 = LocalDate.now().plusDays(3);
        carStorage.addCar(car);
        boolean canReserve = rentalService.isAvailable(car.getVin(), futureDate, futureDate2);
        double estimatePrice = rentalService.estimatePrice(car.getVin(), futureDate, futureDate2);

        assertThat(canReserve).isTrue();

    }

    @Test
    public void testPastReservation() {

        LocalDate pastDate = LocalDate.now().minusDays(2);
        LocalDate pastDate2 = LocalDate.now().minusDays(1);

        boolean canReserve = rentalService.isAvailable("vin", pastDate, pastDate2);
        assertThat(canReserve).isTrue();
    }
    @Test
    void shouldCalculateEstimatedPrice(){

        Cars value = new Cars("make","model","vin", Type.Sport);
        //when(carStorage.getCarByVin("vin")).thenReturn(value);
        carStorage.addCar(value);
        LocalDate dateFrom = LocalDate.of(2023, 9, 26);
        LocalDate dateTo = LocalDate.of(2023, 9, 28);
        double estimatePrice= rentalService.estimatePrice("vin",dateFrom,dateTo);
        assertThat(estimatePrice).isEqualTo(300);
    }

    @Test
    void Carexsist(){
        Cars car = new Cars("make","model","vin", Type.Sport);
        carStorage.addCar(car);
        boolean carExists = rentalService.carExists("vin");
        assertThat(carExists).isTrue();
    }
    @Test
    void CarNotexsist(){
        boolean carExists = rentalService.carExists("vin");
        assertThat(carExists).isFalse();
    }

}