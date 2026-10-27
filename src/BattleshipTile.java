import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class BattleshipTile extends JButton
{
    private static final int ICON_SIZE = 40;
    private static final Icon WAVE_ICON = makeIcon("~", new Color(120, 190, 230), new Color(225, 242, 252));
    private static final Icon MISS_ICON = makeIcon("M", new Color(235, 190, 0), new Color(250, 248, 225));
    private static final Icon HIT_ICON = makeIcon("X", new Color(215, 0, 0), new Color(255, 225, 220));
    private int row;
    private int col;

    public BattleshipTile(int row, int col)
    {
        super();
        this.row = row;
        this.col = col;
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setPreferredSize(new Dimension(ICON_SIZE + 6, ICON_SIZE + 6));
        show(CellState.BLANK);
    }
    public int getRow()
    {
        return row;
    }
    public int getCol()
    {
        return col;
    }
    public void show(CellState state)
    {
        Icon icon = WAVE_ICON;
        if (state == CellState.MISS)
        {
            icon = MISS_ICON;
        }
        else if (state == CellState.HIT)
        {
            icon = HIT_ICON;
        }
        setIcon(icon);
        setDisabledIcon(icon);
        setEnabled(state == CellState.BLANK);
    }
    private static Icon makeIcon(String symbol, Color fg, Color bg)
    {
        BufferedImage img = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(bg);
        g.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
        g.setColor(fg);
        g.setFont(new Font("SansSerif", Font.BOLD, symbol.equals("~") ? 34 : 26));
        FontMetrics fm = g.getFontMetrics();
        int x = (ICON_SIZE - fm.stringWidth(symbol)) / 2;
        int y = (ICON_SIZE - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(symbol, x, y);
        g.dispose();
        return new ImageIcon(img);
    }
}
