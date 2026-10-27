import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShipTest
{
    private Ship horizontal;
    private Ship vertical;

    @BeforeEach
    public void setUp()
    {
        horizontal = new Ship(3, 2, 4, true);
        vertical = new Ship(2, 5, 0, false);
    }

    @Test
    public void testConstructor()
    {
        assertEquals(3, horizontal.getSize());
        assertEquals(0, horizontal.getHits());
        assertFalse(horizontal.isSunk());
    }

    @Test
    public void testOccupiesHorizontal()
    {
        assertTrue(horizontal.occupies(2, 4));
        assertTrue(horizontal.occupies(2, 5));
        assertTrue(horizontal.occupies(2, 6));
        assertFalse(horizontal.occupies(2, 7));
        assertFalse(horizontal.occupies(3, 4));
    }

    @Test
    public void testOccupiesVertical()
    {
        assertTrue(vertical.occupies(5, 0));
        assertTrue(vertical.occupies(6, 0));
        assertFalse(vertical.occupies(7, 0));
        assertFalse(vertical.occupies(5, 1));
    }

    @Test
    public void testSunkAfterAllHits()
    {
        vertical.hit();
        assertFalse(vertical.isSunk());
        vertical.hit();
        assertTrue(vertical.isSunk());
        vertical.hit();
        assertEquals(2, vertical.getHits());
    }
}
