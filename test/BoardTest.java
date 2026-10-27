import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class BoardTest
{
    private Board board;

    @BeforeEach
    public void setUp()
    {
        board = new Board(new Random(42));
    }

    @Test
    public void testNewBoardIsBlank()
    {
        for (int row = 0; row < Board.SIZE; row++)
        {
            for (int col = 0; col < Board.SIZE; col++)
            {
                assertEquals(CellState.BLANK, board.getCell(row, col));
            }
        }
        assertFalse(board.allSunk());
    }

    @Test
    public void testRandomPlacementPlacesAllShipsWithoutOverlap()
    {
        for (int trial = 0; trial < 200; trial++)
        {
            board.placeShipsRandomly();
            assertEquals(5, board.getShips().size());
            int occupied = 0;
            for (int row = 0; row < Board.SIZE; row++)
            {
                for (int col = 0; col < Board.SIZE; col++)
                {
                    if (board.getShipAt(row, col) != null)
                    {
                        occupied++;
                    }
                }
            }
            assertEquals(17, occupied);
        }
    }

    @Test
    public void testAddShipRejectsOverlapAndOutOfBounds()
    {
        assertTrue(board.addShip(new Ship(3, 0, 0, true)));
        assertFalse(board.addShip(new Ship(2, 0, 2, false)));
        assertFalse(board.addShip(new Ship(4, 0, 8, true)));
        assertTrue(board.addShip(new Ship(2, 1, 0, true)));
    }

    @Test
    public void testFireMissAndHit()
    {
        board.addShip(new Ship(2, 4, 4, true));
        assertEquals(ShotResult.MISS, board.fire(0, 0));
        assertEquals(CellState.MISS, board.getCell(0, 0));
        assertEquals(ShotResult.HIT, board.fire(4, 4));
        assertEquals(CellState.HIT, board.getCell(4, 4));
    }

    @Test
    public void testFireSunkAndAllSunk()
    {
        board.addShip(new Ship(2, 0, 0, true));
        board.addShip(new Ship(2, 5, 5, false));
        board.fire(0, 0);
        assertEquals(ShotResult.SUNK, board.fire(0, 1));
        board.fire(5, 5);
        assertEquals(ShotResult.ALL_SUNK, board.fire(6, 5));
        assertTrue(board.allSunk());
    }

    @Test
    public void testFireSameCellTwiceThrows()
    {
        board.fire(3, 3);
        assertThrows(IllegalStateException.class, () -> board.fire(3, 3));
    }
}
