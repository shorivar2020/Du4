import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import shop.StandardItem;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;


public class StandardItemTest {

    StandardItem it, copyIt;
    int id = 1;
    String name = "Milk";
    float price = 100;
    String category = "Drinks";
    int loyal = 1;

    @BeforeEach
    public void setStandardItem(){
        it = new StandardItem(id, name, price, category, loyal);
        copyIt = it.copy();
    }

    @Test
    public  void idTest(){
        assertEquals(it.getID(), id);
    }

    @Test
    public  void nameTest(){
        assertEquals(it.getName(), name);
    }

    @Test
    public  void costTest(){
        assertEquals(it.getPrice(), price);
    }

    @Test
    public  void categoryTest(){
        assertEquals(it.getCategory(), category);
    }

    @Test
    public  void loyalTest(){
        assertEquals(it.getID(), loyal);
    }

    @Test
    public  void idCopyTest(){
        assertEquals(it.getID(), copyIt.getID());
    }

    @Test
    public  void nameCopyTest(){
        assertEquals(it.getName(), copyIt.getName());
    }

    @Test
    public  void costCopyTest(){
        assertEquals(it.getPrice(), copyIt.getPrice());
    }

    @Test
    public  void categoryCopyTest(){
        assertEquals(it.getCategory(), copyIt.getCategory());
    }

    @Test
    public  void loyalCopyTest(){
        assertEquals(it.getLoyaltyPoints(), copyIt.getLoyaltyPoints());
    }

    @ParameterizedTest(name = "{index} multiple {1} should be equal to {2}")
    @MethodSource("DataProvider")
   public void equalsTest(Object p, Object p2, boolean r) {
        assertEquals(p.equals(p2), r);
   }

    static Stream<Arguments> DataProvider() {
        StandardItem it3 = new StandardItem(13, "IceCream", 100, "Frozen", 2);
        StandardItem it5 = new StandardItem(14, "IceCream", 101, "Frozen", 1);

        return Stream.of(arguments(it3, it5, false));
    }
}
