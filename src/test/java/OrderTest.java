import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.Item;
import shop.Order;
import shop.ShoppingCart;
import shop.StandardItem;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderTest {
    StandardItem it3 = new StandardItem(13, "IceCream", 100, "Frozen", 2);
    StandardItem it5 = new StandardItem(14, "IceCream", 101, "Frozen", 1);
    ArrayList<Item> it = new ArrayList<>();
    Order or;
    String customerName = "Smith";
    String customerAddress = "Praha";
    int state = 0;

    @BeforeEach
    public void setOrder(){
        it.add(it3);
        it.add(it5);
        or = new Order(new ShoppingCart(it), customerName, customerAddress);
    }


    @Test
    public void ShoppingCardTest(){
        assertEquals(or.getItems(), it);
    }


    @Test
    public void CustomerNameTest(){
        assertEquals(or.getCustomerName(), customerName);
    }

    @Test
    public void setCustomerAddressTest(){
        assertEquals(or.getCustomerAddress(), customerAddress);
    }

    @Test
    public void stateTest(){
        assertEquals(or.getState(), state);
    }

    @Test
    public void NullShoppingCardTest(){
        assertNotNull(or.getItems());
    }
    @Test
    public  void NullCustomerNameTest(){
        assertNotNull(or.getCustomerName());
    }
    @Test
    public  void NullSetCustomerAddressTest(){
        assertNotNull(or.getCustomerAddress());
    }
}
