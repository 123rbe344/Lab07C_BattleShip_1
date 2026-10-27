public class Ship
{
    private int size;
    private int[] rows;
    private int[] cols;
    private int hits = 0;

    public Ship(int size, int startRow, int startCol, boolean horizontal)
    {
        this.size = size;
        rows = new int[size];
        cols = new int[size];
        for (int i = 0; i < size; i++)
        {
            rows[i] = horizontal ? startRow : startRow + i;
            cols[i] = horizontal ? startCol + i : startCol;
        }
    }
    public int getSize()
    {
        return size;
    }
    public int getHits()
    {
        return hits;
    }
    public int[] getRows()
    {
        return rows;
    }
    public int[] getCols()
    {
        return cols;
    }
    public boolean occupies(int row, int col)
    {
        for (int i = 0; i < size; i++)
        {
            if (rows[i] == row && cols[i] == col)
            {
                return true;
            }
        }
        return false;
    }
    public void hit()
    {
        if (hits < size)
        {
            hits++;
        }
    }
    public boolean isSunk()
    {
        return hits == size;
    }
}
