package rhythmwheels;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Crash extends Sound
{

    public Crash()
    {
        super("crash");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Font f = g.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(new Font("SansSerif", Font.PLAIN, 26));
        g.drawString("#", 17, 28);
        g.setFont(f);
        g.translate(-p.x, -p.y);
    }
}
