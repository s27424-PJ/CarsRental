package org.example;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
public class CarStorageTest {
    private CarStorage carStorage = new CarStorage();
    @Test
    void shouldHaveEntriesInStorage(){
        //Given
        carStorage.addCar(new Cars("a","a","13",Type.Classic));
        carStorage.addCar(new Cars("a","a","13",Type.Classic));
        //When
        List<Cars> allCars = carStorage.getAllCars();
        assertThat(allCars).hasSize(3);
    }
    @Test
    void shouldBeenEmpty(){

        //When
        List<Cars> allCars = carStorage.getAllCars();
        assertThat(allCars).isEmpty();
    }
    @Test
    void shouldSearchVIN() {
        Cars car = new Cars("Make", "model", "abc", Type.Classic);

        carStorage.addCar(car);
        Cars vincheck = carStorage.getCarByVin("abc");

        assertThat(vincheck).isNotNull();
        assertThat(vincheck.getVin()).isEqualTo("abc");
    }
}
