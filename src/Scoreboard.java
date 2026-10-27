public class Scoreboard
{
    public static final int MISSES_PER_STRIKE = 5;
    public static final int STRIKES_TO_LOSE = 3;
    private int missCount;
    private int strikeCount;
    private int totalMiss;
    private int totalHit;

    public Scoreboard()
    {
        reset();
    }
    public void reset()
    {
        missCount = 0;
        strikeCount = 0;
        totalMiss = 0;
        totalHit = 0;
    }
    public void recordHit()
    {
        totalHit++;
        missCount = 0;
    }
    public boolean recordMiss()
    {
        missCount++;
        totalMiss++;
        if (missCount == MISSES_PER_STRIKE)
        {
            strikeCount++;
            missCount = 0;
            return true;
        }
        return false;
    }
    public boolean isLost()
    {
        return strikeCount >= STRIKES_TO_LOSE;
    }
    public int getMissCount()
    {
        return missCount;
    }
    public int getStrikeCount()
    {
        return strikeCount;
    }
    public int getTotalMiss()
    {
        return totalMiss;
    }
    public int getTotalHit()
    {
        return totalHit;
    }
}
