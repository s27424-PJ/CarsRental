package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
@SpringBootApplication
public class Main implements CommandLineRunner {
    private  final CarStorage carStorage;
    private final RentalStorage rentalStorage;
    private final RentalService rentalService;
    public Main(CarStorage carStorage, RentalStorage rentalStorage, RentalService rentalService){
        this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
        this.rentalService = rentalService;
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    public void exec(){
        User user1 = new User(1);

        Cars car1 = new Cars("Audi", "x", "123", Type.Classic);
        Cars car2 = new Cars("XPP", "x", "1243", Type.Classic);
        carStorage.addCar(car1);
        carStorage.addCar(car2);
        LocalDate dateFrom = LocalDate.of(2023, 9, 23);
        LocalDate dateTo = LocalDate.of(2023, 9, 26);
        LocalDate dateFrom2 = LocalDate.of(2023, 9, 27);
        LocalDate dateTo2 = LocalDate.of(2023, 9, 28);

        LocalDate dateFrom3 = LocalDate.of(2023, 9, 28);
        LocalDate dateTo3 = LocalDate.of(2023, 9, 26);
        System.out.println("Pierwsza data: " + dateFrom);


        Rental rent = rentalService.rent(user1.getId(), car2.getVin(), dateFrom, dateTo);

        Rental rent2 = rentalService.rent(user1.getId(), car2.getVin(), dateFrom2, dateTo2);
        Rental rent3 = rentalService.rent(user1.getId(), car2.getVin(), dateFrom, dateTo3);

        System.out.println(carStorage.getAllCars());

        System.out.println(rentalStorage.getAllRents());
        //sprawdzanie czy w tym przydziale jest samochod dostepny
        System.out.println(rentalService.isAvailable(car1.getVin(),dateFrom,dateTo));
        System.out.println(rentalService.isAvailable(car2.getVin(),dateFrom,dateTo));
        rentalService.estimatePrice(car1.getVin(), dateFrom, dateTo);
        System.out.println(rentalService.estimatePrice(car2.getVin(), dateFrom, dateTo));
        rentalService.isAvailable(car2.getVin(), dateFrom, dateTo);
    }

    @Override
    public void run(String... args) throws Exception {
        exec();
    }
}
