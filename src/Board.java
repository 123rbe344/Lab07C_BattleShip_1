import java.util.ArrayList;
import java.util.Random;
public class Board
{
    public static final int SIZE = 10;
    public static final int[] SHIP_SIZES = {5, 4, 3, 3, 2};
    private CellState[][] cells = new CellState[SIZE][SIZE];
    private Ship[][] shipGrid = new Ship[SIZE][SIZE];
    private ArrayList<Ship> ships = new ArrayList<>();
    private Random rnd;

    public Board()
    {
        this(new Random());
    }
    public Board(Random rnd)
    {
        this.rnd = rnd;
        clear();
    }
    public void clear()
    {
        ships.clear();
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                cells[row][col] = CellState.BLANK;
                shipGrid[row][col] = null;
            }
        }
    }
    public void placeShipsRandomly()
    {
        clear();
        for (int size : SHIP_SIZES)
        {
            placeShipRandomly(size);
        }
    }
    private void placeShipRandomly(int size)
    {
        while (true)
        {
            boolean horizontal = rnd.nextBoolean();
            int line = rnd.nextInt(SIZE);
            ArrayList<Integer> starts = new ArrayList<>();
            int run = 0;
            for (int i = 0; i < SIZE; i++)
            {
                int row = horizontal ? line : i;
                int col = horizontal ? i : line;
                run = shipGrid[row][col] == null ? run + 1 : 0;
                if (run >= size)
                {
                    starts.add(i - size + 1);
                }
            }
            if (!starts.isEmpty())
            {
                int start = starts.get(rnd.nextInt(starts.size()));
                if (horizontal)
                {
                    addShip(new Ship(size, line, start, true));
                }
                else
                {
                    addShip(new Ship(size, start, line, false));
                }
                return;
            }
        }
    }
    public boolean addShip(Ship ship)
    {
        int[] rows = ship.getRows();
        int[] cols = ship.getCols();
        for (int i = 0; i < ship.getSize(); i++)
        {
            if (rows[i] < 0 || rows[i] >= SIZE || cols[i] < 0 || cols[i] >= SIZE)
            {
                return false;
            }
            if (shipGrid[rows[i]][cols[i]] != null)
            {
                return false;
            }
        }
        for (int i = 0; i < ship.getSize(); i++)
        {
            shipGrid[rows[i]][cols[i]] = ship;
        }
        ships.add(ship);
        return true;
    }
    public ShotResult fire(int row, int col)
    {
        if (cells[row][col] != CellState.BLANK)
        {
            throw new IllegalStateException("Cell already fired on");
        }
        Ship ship = shipGrid[row][col];
        if (ship == null)
        {
            cells[row][col] = CellState.MISS;
            return ShotResult.MISS;
        }
        cells[row][col] = CellState.HIT;
        ship.hit();
        if (!ship.isSunk())
        {
            return ShotResult.HIT;
        }
        return allSunk() ? ShotResult.ALL_SUNK : ShotResult.SUNK;
    }
    public boolean allSunk()
    {
        for (Ship ship : ships)
        {
            if (!ship.isSunk())
            {
                return false;
            }
        }
        return !ships.isEmpty();
    }
    public CellState getCell(int row, int col)
    {
        return cells[row][col];
    }
    public Ship getShipAt(int row, int col)
    {
        return shipGrid[row][col];
    }
    public ArrayList<Ship> getShips()
    {
        return ships;
    }
}
