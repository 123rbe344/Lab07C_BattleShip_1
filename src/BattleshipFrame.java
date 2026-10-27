import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class BattleshipFrame extends JFrame
{
    JPanel mainPnl;
    JPanel topPnl;
    JPanel boardPnl;
    JPanel statusPnl;
    JPanel bottomPnl;
    JLabel titleLbl;
    JLabel missLbl;
    JLabel strikeLbl;
    JLabel totalMissLbl;
    JLabel totalHitLbl;
    JButton playAgainBtn;
    JButton quitBtn;
    Font titleFont = new Font("SansSerif", Font.BOLD, 30);
    Font statusFont = new Font("SansSerif", Font.BOLD, 16);
    Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
    private BattleshipTile[][] tiles = new BattleshipTile[Board.SIZE][Board.SIZE];
    private Board board = new Board();
    private Scoreboard score = new Scoreboard();

    public BattleshipFrame()
    {
        super("Battleship");
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout(10, 10));
        mainPnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createTopPanel();
        mainPnl.add(topPnl, BorderLayout.NORTH);
        createBoardPanel();
        mainPnl.add(boardPnl, BorderLayout.CENTER);
        createStatusPanel();
        mainPnl.add(statusPnl, BorderLayout.EAST);
        createBottomPanel();
        mainPnl.add(bottomPnl, BorderLayout.SOUTH);
        add(mainPnl);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGame();
    }
    private void createTopPanel()
    {
        topPnl = new JPanel();
        titleLbl = new JLabel("BATTLESHIP");
        titleLbl.setFont(titleFont);
        titleLbl.setForeground(new Color(0, 60, 120));
        topPnl.add(titleLbl);
    }
    private void createBoardPanel()
    {
        boardPnl = new JPanel();
        boardPnl.setLayout(new GridLayout(Board.SIZE, Board.SIZE));
        TileListener listener = new TileListener();
        for (int row = 0; row < Board.SIZE; row++)
        {
            for (int col = 0; col < Board.SIZE; col++)
            {
                tiles[row][col] = new BattleshipTile(row, col);
                tiles[row][col].addActionListener(listener);
                boardPnl.add(tiles[row][col]);
            }
        }
    }
    private void createStatusPanel()
    {
        statusPnl = new JPanel();
        statusPnl.setLayout(new GridLayout(4, 1, 0, 10));
        statusPnl.setBorder(BorderFactory.createTitledBorder("Status"));
        missLbl = new JLabel();
        strikeLbl = new JLabel();
        totalMissLbl = new JLabel();
        totalHitLbl = new JLabel();
        for (JLabel lbl : new JLabel[]{missLbl, strikeLbl, totalMissLbl, totalHitLbl})
        {
            lbl.setFont(statusFont);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            statusPnl.add(lbl);
        }
    }
    private void createBottomPanel()
    {
        bottomPnl = new JPanel();
        bottomPnl.setLayout(new GridLayout(1, 2, 10, 0));
        playAgainBtn = new JButton("Play Again");
        playAgainBtn.setFont(buttonFont);
        playAgainBtn.addActionListener((ae) -> playAgain());
        quitBtn = new JButton("Quit");
        quitBtn.setFont(buttonFont);
        quitBtn.addActionListener((ae) -> quit());
        bottomPnl.add(playAgainBtn);
        bottomPnl.add(quitBtn);
    }
    private class TileListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            BattleshipTile tile = (BattleshipTile) ae.getSource();
            fireAt(tile.getRow(), tile.getCol());
        }
    }
    private void fireAt(int row, int col)
    {
        if (board.getCell(row, col) != CellState.BLANK)
        {
            return;
        }
        ShotResult result = board.fire(row, col);
        tiles[row][col].show(board.getCell(row, col));
        if (result == ShotResult.MISS)
        {
            boolean strike = score.recordMiss();
            updateStatus();
            if (score.isLost())
            {
                endGame("Strike 3! You ran out of missiles and lost.\nPlay again?", "You Lose");
            }
            else if (strike)
            {
                JOptionPane.showMessageDialog(this, "5 misses in a row - that's a STRIKE!", "Strike", JOptionPane.WARNING_MESSAGE);
            }
            return;
        }
        score.recordHit();
        updateStatus();
        if (result == ShotResult.SUNK)
        {
            int size = board.getShipAt(row, col).getSize();
            JOptionPane.showMessageDialog(this, "You sunk a ship! (size " + size + ")", "Sunk!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (result == ShotResult.ALL_SUNK)
        {
            endGame("You sunk the final ship - you win!\nPlay again?", "You Win!");
        }
    }
    private void endGame(String msg, String title)
    {
        int choice = JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
        {
            newGame();
        }
        else
        {
            System.exit(0);
        }
    }
    private void playAgain()
    {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure? This will end the current game and start a new one.", "Play Again", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
        {
            newGame();
        }
    }
    private void quit()
    {
        String[] options = {"Play Again", "Quit", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, "Would you like to play again instead of quitting?", "Quit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        if (choice == 0)
        {
            newGame();
        }
        else if (choice == 1)
        {
            System.exit(0);
        }
    }
    private void newGame()
    {
        board.placeShipsRandomly();
        score.reset();
        for (int row = 0; row < Board.SIZE; row++)
        {
            for (int col = 0; col < Board.SIZE; col++)
            {
                tiles[row][col].show(CellState.BLANK);
            }
        }
        updateStatus();
    }
    private void updateStatus()
    {
        missLbl.setText("Miss: " + score.getMissCount() + " / " + Scoreboard.MISSES_PER_STRIKE);
        strikeLbl.setText("Strike: " + score.getStrikeCount() + " / " + Scoreboard.STRIKES_TO_LOSE);
        totalMissLbl.setText("Total Miss: " + score.getTotalMiss());
        totalHitLbl.setText("Total Hit: " + score.getTotalHit() + " / 17");
    }
    public Board getBoard()
    {
        return board;
    }
}
