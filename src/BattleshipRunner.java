import javax.swing.*;
public class BattleshipRunner
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            BattleshipFrame frame = new BattleshipFrame();
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
