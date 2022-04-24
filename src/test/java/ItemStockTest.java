import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import shop.Item;
import storage.ItemStock;
import shop.StandardItem;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ItemStockTest {

    int id = 1;
    String name = "Milk";
    float price = 100;
    String category = "Drinks";
    int loyal = 1;
    ItemStock stock;
    Item ref;

    @BeforeEach
    public void setItemStock(){
        ref = new StandardItem(id, name, price, category, loyal);
        stock = new ItemStock(ref);
    }

    @Test
    public void RefTest(){
        assertEquals(stock.getItem(), ref);
    }

    @Test
    public void CountTest(){
        assertEquals(stock.getCount(), 0);
    }


    @ParameterizedTest(name = "{index} increase {1} should be equal to {2}")
    @MethodSource("DataProviderPlus")
    public void IncreaseItemCountTest(int p, int r) {
        stock = new ItemStock(ref);
        stock.IncreaseItemCount(p);
        assertEquals(stock.getCount(), r);
    }

    @ParameterizedTest(name = "{index} increase {1} should be equal to {2}")
    @MethodSource("DataProviderMinus")
    public void decreaseItemCountTest(int p, int r) {
        stock = new ItemStock(ref);
        stock.decreaseItemCount(p);
        assertEquals(stock.getCount(), r);
    }

    @ParameterizedTest(name = "{index} increase {1} should be equal to {2}")
    @MethodSource("DataProviderPlusPlus")
    public void IncreaseIncreaseItemCountTest(int p, int p2, int r) {
        stock = new ItemStock(ref);
        stock.IncreaseItemCount(p);
        stock.IncreaseItemCount(p2);
        assertEquals(stock.getCount(), r);
    }

    @ParameterizedTest(name = "{index} increase {1} should be equal to {2}")
    @MethodSource("DataProviderMinusMinus")
    public void decreaseDecreaseItemCountTest(int p, int p2, int r) {
        stock = new ItemStock(ref);
        stock.decreaseItemCount(p);
        stock.decreaseItemCount(p2);
        assertEquals(stock.getCount(), r);
    }

    @ParameterizedTest(name = "{index} increase {1} should be equal to {2}")
    @MethodSource("DataProviderPlusMinus")
    public void IncreaseDecreaseItemCountTest(int p, int p2, int r) {
        stock = new ItemStock(ref);
        stock.IncreaseItemCount(p);
        stock.decreaseItemCount(p2);
        assertEquals(stock.getCount(), r);
    }

    static Stream<Arguments> DataProviderPlus() {
        return Stream.of(arguments(1, 1));
    }

    static Stream<Arguments> DataProviderPlusPlus() {
        return Stream.of(arguments(1, 1, 2));
    }

    static Stream<Arguments> DataProviderMinus() {
        return Stream.of(arguments(1, -1));
    }

    static Stream<Arguments> DataProviderMinusMinus() {
        return Stream.of(arguments(1, 1, -2));
    }

    static Stream<Arguments> DataProviderPlusMinus() {
        return Stream.of(arguments(2, 1, 1));
    }
}
