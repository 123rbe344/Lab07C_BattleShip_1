import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest
{
    private Scoreboard score;

    @BeforeEach
    public void setUp()
    {
        score = new Scoreboard();
    }

    @Test
    public void testStartsAtZero()
    {
        assertEquals(0, score.getMissCount());
        assertEquals(0, score.getStrikeCount());
        assertEquals(0, score.getTotalMiss());
        assertEquals(0, score.getTotalHit());
        assertFalse(score.isLost());
    }

    @Test
    public void testHitResetsMissCounter()
    {
        score.recordMiss();
        score.recordMiss();
        score.recordHit();
        assertEquals(0, score.getMissCount());
        assertEquals(2, score.getTotalMiss());
        assertEquals(1, score.getTotalHit());
    }

    @Test
    public void testFiveMissesIsStrike()
    {
        for (int i = 0; i < 4; i++)
        {
            assertFalse(score.recordMiss());
        }
        assertTrue(score.recordMiss());
        assertEquals(1, score.getStrikeCount());
        assertEquals(0, score.getMissCount());
        assertEquals(5, score.getTotalMiss());
    }

    @Test
    public void testThreeStrikesLoses()
    {
        for (int i = 0; i < 15; i++)
        {
            score.recordMiss();
        }
        assertEquals(3, score.getStrikeCount());
        assertTrue(score.isLost());
    }

    @Test
    public void testReset()
    {
        score.recordMiss();
        score.recordHit();
        score.reset();
        assertEquals(0, score.getTotalMiss());
        assertEquals(0, score.getTotalHit());
    }
}
