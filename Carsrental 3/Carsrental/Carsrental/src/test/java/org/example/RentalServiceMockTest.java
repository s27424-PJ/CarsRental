package org.example;

import org.example.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceMockTest {
    @Mock
    private CarStorage carStorage;
    @Mock
    private RentalStorage rentalStorage;
    @InjectMocks
    private RentalService rentalService;

    @Test
    void shouldCalculateEstimatedPrice() {
        //Given
        Cars value = new Cars("make", "model", "vin", Type.Sport);
        //when(carStorage.getCarByVin("vin")).thenReturn(value);
        when(carStorage.getCarByVin(anyString())).thenReturn(value);
        LocalDate dateFrom = LocalDate.of(2023, 9, 26);
        LocalDate dateTo = LocalDate.of(2023, 9, 28);
        double estimatePrice = rentalService.estimatePrice("vin", dateFrom, dateTo);
        assertThat(estimatePrice).isEqualTo(300);
    }

    @Test
    void shouldCalculateEstimatedPrice2() {
        Cars car = new Cars("XPP", "x", "1243", Type.Classic);
        carStorage.addCar(car);
        LocalDate dateFrom = LocalDate.of(2023, 9, 29);
        LocalDate dateTo = LocalDate.of(2023, 9, 26);


        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> rentalService.estimatePrice(car.getVin(), dateFrom, dateTo));
    }

    @Test
    public void testIsAvailable_ReturnsFalseWhenCarAlreadyRented() {

        LocalDate dateFrom = LocalDate.now().plusDays(3);
        LocalDate dateTo = LocalDate.now().plusDays(7);
        String vin = "VIN456";
        List<Rental> rentals = new ArrayList<>();
        Cars car = new Cars("Toyota", "Corolla", vin, Type.Classic);
        Rental rental1 = new Rental(1, car, LocalDate.now().plusDays(4), LocalDate.now().plusDays(6));

        Rental rental2 = new Rental(2, car, LocalDate.now().plusDays(8), LocalDate.now().plusDays(10));
        rentals.add(rental1);
        rentals.add(rental2);
        when(rentalStorage.getAllRents()).thenReturn(rentals);
        boolean result = rentalService.isAvailable(vin, dateFrom, dateTo);

        assertFalse(result);
    }
    @Test
    public void testIsAvailable_ReturnsTrueWhenCarisNotRented() {

        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(20);
        String vin = "VIN456";
        List<Rental> rentals = new ArrayList<>();
        Cars car = new Cars("Toyota", "Corolla","VIN456", Type.Classic);
        boolean result = rentalService.isAvailable(vin, dateFrom, dateTo);

        assertFalse(result);
    }
    @ParameterizedTest
    @MethodSource("inputData")
    void shouldHaveOverlappingDates(LocalDate startDate, LocalDate endDate) {

        String vin = "VIN456";
        List<Rental> rentals = new ArrayList<>();
        Cars car = new Cars("Toyota", "Corolla", vin, Type.Classic);
        Rental rental1 = new Rental(1, car, LocalDate.of(2023, 11, 25), LocalDate.of(2023, 11, 30));

        rentals.add(rental1);
        when(rentalStorage.getAllRents()).thenReturn(rentals);
        boolean result = rentalService.isAvailable(vin, startDate, endDate);

        assertFalse(result);
    }
    public static Stream<Arguments> inputData() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 11, 1), LocalDate.of(2023, 12, 6)),
                Arguments.of(LocalDate.of(2023, 11, 15), LocalDate.of(2023, 11, 26)),
                Arguments.of(LocalDate.of(2023, 11, 25), LocalDate.of(2023, 11, 30)),
                Arguments.of(LocalDate.of(2023, 11, 27), LocalDate.of(2023, 11, 28)),
                Arguments.of(LocalDate.of(2023, 11, 29), LocalDate.of(2023, 12, 6))
        );
    }
    @ParameterizedTest
    @MethodSource("inputData2")
    void shouldntHaveOverlappingDates2(LocalDate startDate, LocalDate endDate) {

        String vin = "VIN456";
        List<Rental> rentals = new ArrayList<>();
        Cars car = new Cars("Toyota", "Corolla", vin, Type.Classic);
        Rental rental1 = new Rental(1, car, LocalDate.of(2023, 11, 25), LocalDate.of(2023, 11, 30));

        rentals.add(rental1);
        when(rentalStorage.getAllRents()).thenReturn(rentals);
        boolean result = rentalService.isAvailable(vin, startDate, endDate);

        assertTrue(result);
    }
    public static Stream<Arguments> inputData2() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 5)),
                Arguments.of(LocalDate.of(2023, 11, 20), LocalDate.of(2023, 11, 24)),
                Arguments.of(LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 6)),
                Arguments.of(LocalDate.of(2023, 12, 3), LocalDate.of(2023, 12, 6))

        );
    }
    @Test
    public void testEstimate() {

        Cars car = new Cars("XPP", "x", "1243", Type.Classic);

        carStorage.addCar(car);
        LocalDate dateFrom = LocalDate.of(2023, 9, 29);
        LocalDate dateTo = LocalDate.of(2023, 9, 26);
        when(carStorage.getCarByVin(anyString())).thenReturn(car);
        //When
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> rentalService.estimatePrice(car.getVin(), dateFrom, dateTo));
    }
    @ParameterizedTest
    @MethodSource("incorrectData")
    void shouldThrowExceptionsWhenDatesAreIncorrectInIsAvailable(LocalDate rentalDate, LocalDate returnDate) {
        //GIVEN
        Cars car1 = new Cars("Ford", "Fiesta", "abc123", Type.Classic);
        //WHEN
       
        //THEN
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> rentalService.isAvailable(car1.getVin(), rentalDate, returnDate)
        );
    }

    public static Stream<Arguments> incorrectData() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 11, 27), LocalDate.of(2023, 11, 25)),
                Arguments.of(LocalDate.of(2023, 12, 15), LocalDate.of(2023, 12, 12))
        );
    }

}