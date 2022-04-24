import archive.PurchasesArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import shop.*;
import shop.EShopController;
import storage.NoItemInStorage;
import storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;


public class EShopControllerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    int[] itemCount = {10,10,4,5,10,2};


    Item[] storageItems = {
            new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
            new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
            new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
            new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
            new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
            new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
    };

    @BeforeEach
    public void init(){
        System.setOut(new PrintStream(outContent));
        EShopController.startEShop();
        for(int i = 0; i<storageItems.length; i++){
            EShopController.storage.insertItems(storageItems[i], itemCount[i]);
        }
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    //add item
    //remove item
    //*do order
    //*empty order
    @Test
    public void addItem() throws NoItemInStorage {
        ShoppingCart cart = new ShoppingCart();
        for (Item i: storageItems)
        cart.addItem(i);
        try {
            EShopController.purchaseShoppingCart(cart, "Libuse Novakova","Kosmonautu 25, Praha 8");
            String expected = "Item with ID 1 added to the shopping cart.\r\n" +
                    "Item with ID 2 added to the shopping cart.\r\n" +
                    "Item with ID 3 added to the shopping cart.\r\n" +
                    "Item with ID 4 added to the shopping cart.\r\n" +
                    "Item with ID 5 added to the shopping cart.\r\n" +
                    "Item with ID 6 added to the shopping cart.\r\n";
            assertEquals(expected, outContent.toString());
        }catch (NoItemInStorage e){};
    }

    @Test
    public void removeItem() throws NoItemInStorage {
        ShoppingCart RemoveCart = new ShoppingCart();
        for (Item i: storageItems)
            RemoveCart.addItem(i);
        RemoveCart.removeItem(6);
        try {
            EShopController.purchaseShoppingCart(RemoveCart, "Libuse Novakova","Kosmonautu 25, Praha 8");
            String expected = "Item with ID 1 added to the shopping cart.\r\n" +
                            "Item with ID 2 added to the shopping cart.\r\n" +
                            "Item with ID 3 added to the shopping cart.\r\n" +
                            "Item with ID 4 added to the shopping cart.\r\n" +
                            "Item with ID 5 added to the shopping cart.\r\n" +
                            "Item with ID 6 added to the shopping cart.\r\n";
            assertEquals(expected, outContent.toString());
        }catch (NoItemInStorage e){};
    }

    @Test
    public void emptyItem() throws NoItemInStorage {
        ShoppingCart EmptyCart = new ShoppingCart();
        try {
            EShopController.purchaseShoppingCart(EmptyCart, "Libuse Novakova","Kosmonautu 25, Praha 8");
            String expected = "Error: shopping cart is empty\r\n";
            assertEquals(expected, outContent.toString());
        }catch (NoItemInStorage e){};
    }

    @Test
    public void NonExistItem() throws NoItemInStorage {
        ShoppingCart RemoveCart = new ShoppingCart();
        for (Item i: storageItems)
            RemoveCart.addItem(i);
        RemoveCart.removeItem(99);
        try {
            EShopController.purchaseShoppingCart(RemoveCart, "Libuse Novakova","Kosmonautu 25, Praha 8");
            String expected = "Item with ID 1 added to the shopping cart.\r\n" +
                    "Item with ID 2 added to the shopping cart.\r\n" +
                    "Item with ID 3 added to the shopping cart.\r\n" +
                    "Item with ID 4 added to the shopping cart.\r\n" +
                    "Item with ID 5 added to the shopping cart.\r\n" +
                    "Item with ID 6 added to the shopping cart.\r\n";
            assertEquals(expected, outContent.toString());
        }catch (NoItemInStorage e){};
    }

    @Test
    public void addSameExistItem() throws NoItemInStorage {
        ShoppingCart Cart = new ShoppingCart();
        Cart.addItem(storageItems[0]);
        Cart.addItem(storageItems[0]);
        try {
            EShopController.purchaseShoppingCart(Cart, "Libuse Novakova","Kosmonautu 25, Praha 8");
            String expected = "Item with ID 1 added to the shopping cart.\r\n" +
                    "Item with ID 1 added to the shopping cart.\r\n";
            assertEquals(expected, outContent.toString());
        }catch (NoItemInStorage e){};
    }

    @Test
    public void ArchiveTest() throws NoItemInStorage {
        PurchasesArchive archive = Mockito.mock(PurchasesArchive.class);
        EShopController.SetArchive(archive);
        ShoppingCart cart = new ShoppingCart();
        for (Item i: storageItems)
            cart.addItem(i);
            cart.removeItem(0);
        try {
            EShopController.purchaseShoppingCart(cart, "Libuse Novakova","Kosmonautu 25, Praha 8");
            verify(archive).putOrderToPurchasesArchive(Mockito.any());
        }catch (NoItemInStorage e){};
    }

    @Test
    public void StorageTest() throws NoItemInStorage {
        Storage storage = Mockito.mock(Storage.class);
        EShopController.SetStorage(storage);
        ShoppingCart cart = new ShoppingCart();
        for (Item i: storageItems)
            cart.addItem(i);
        cart.removeItem(0);
        try {
            EShopController.purchaseShoppingCart(cart, "Libuse Novakova","Kosmonautu 25, Praha 8");
            verify(storage).processOrder(Mockito.any());
        }catch (NoItemInStorage e){};
    }

    @ParameterizedTest
    @MethodSource("DataProvider")
    public void startEShopTest(boolean p1){
        assertSame(p1, true);
    }

    static Stream<Arguments> DataProvider() {
        EShopController.startEShop();
        Storage it3 = EShopController.storage;
        PurchasesArchive it4 = EShopController.archive;
        ArrayList<ShoppingCart> it5 = EShopController.carts;
        ArrayList<Order> it6 = EShopController.orders;
        return Stream.of(arguments(it3!=null), arguments(it4!=null), arguments(it5!=null), arguments(it6!=null));
    }
}
