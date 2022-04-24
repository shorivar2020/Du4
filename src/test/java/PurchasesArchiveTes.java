import archive.ItemPurchaseArchiveEntry;
import archive.PurchasesArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shop.*;
import storage.NoItemInStorage;
import storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class PurchasesArchiveTes {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    StandardItem it = new StandardItem(13, "IceCream", 100, "Frozen", 2);

    PurchasesArchive purchasesArchive;
    HashMap<Integer, ItemPurchaseArchiveEntry> itemPurchaseArchive = new HashMap<>();

    ArrayList<Order> orderArchive = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    Order order = new Order(new ShoppingCart(items), "Smith", "Praha");

    @BeforeEach
    public void setPurchasesArchive(){
        System.setOut(new PrintStream(outContent));
        itemPurchaseArchive.put(it.getID(), new ItemPurchaseArchiveEntry(it));
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, orderArchive);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    public void printItemPurchaseStatisticsTest(){
        purchasesArchive.printItemPurchaseStatistics();
        String expected = "ITEM PURCHASE STATISTICS:\r\nITEM  Item   ID " + it.getID() + "   NAME " + it.getName() +  "   CATEGORY " + it.getCategory()  + "   PRICE "+ it.getPrice() +"   LOYALTY POINTS " + it.getLoyaltyPoints() + "   HAS BEEN SOLD " + 1 + " TIMES";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void getHowManyTimesHasBeenItemSoldTest(){
        ItemPurchaseArchiveEntry i = itemPurchaseArchive.get(13);
        assertEquals(i.getCountHowManyTimesHasBeenSold(), itemPurchaseArchive.get(13).getCountHowManyTimesHasBeenSold());
    }

    @Test
    public void putOrderToPurchasesArchiveTest(){
        items.add(it);
        purchasesArchive.putOrderToPurchasesArchive(order);
        assertEquals(it, itemPurchaseArchive.get(13).getRefItem());
    }
    @Test
    public void putOrderToPurchasesArchiveTestHowManyTimesSpend(){
        items.add(it);
        purchasesArchive.putOrderToPurchasesArchive(order);
        purchasesArchive.putOrderToPurchasesArchive(order);
        assertEquals(3, itemPurchaseArchive.get(13).getCountHowManyTimesHasBeenSold());
    }
    @Test
    public void InitGetCountHowManyTimesHasBeenSoldItemPurchaseArchive(){
        ItemPurchaseArchiveEntry i = new ItemPurchaseArchiveEntry(it);
        assertEquals(1, i.getCountHowManyTimesHasBeenSold());
    }

    @Test
    public void InitItemPurchaseArchive(){
        ItemPurchaseArchiveEntry i = new ItemPurchaseArchiveEntry(it);
        assertEquals(it, i.getRefItem());
    }
    @Test
    public void mockOrderArchiveTest(){
        ArrayList<Order> orderArchive = Mockito.mock(ArrayList.class);
        orderArchive.add(order);
        verify(orderArchive).add(Mockito.any());
    }

    @Test
    public void removeMockOrderArchiveTest(){
        ArrayList<Order> orderArchive = Mockito.mock(ArrayList.class);
        orderArchive.remove(order);
        verify(orderArchive).remove(Mockito.any());
    }

    @Test
    public void sizeMockOrderArchiveTest(){
        ArrayList<Order> orderArchive = Mockito.mock(ArrayList.class);
        orderArchive.size();
        verify(orderArchive).size();
    }

    @Test
    public void mockItemPurchaseArchiveEntry(){
        ItemPurchaseArchiveEntry i = Mockito.mock(ItemPurchaseArchiveEntry.class);
        i.getRefItem();
        verify(i).getRefItem();
    }
    @Test
    public void mockCountItemPurchaseArchiveEntry(){
        ItemPurchaseArchiveEntry i = Mockito.mock(ItemPurchaseArchiveEntry.class);
        i.getCountHowManyTimesHasBeenSold();
        verify(i).getCountHowManyTimesHasBeenSold();
    }

    @Test
    public void mockDoubleCountItemPurchaseArchiveEntry(){
        ItemPurchaseArchiveEntry i = Mockito.mock(ItemPurchaseArchiveEntry.class);
        i.getCountHowManyTimesHasBeenSold();
        i.getCountHowManyTimesHasBeenSold();
        verify(i, times(2)).getCountHowManyTimesHasBeenSold();
    }
}
