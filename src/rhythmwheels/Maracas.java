package RhythmWheels;

//package rhythmwheel;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Maracas extends Sound
{

    public Maracas()
    {
        super("maracas");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Font f = g.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString("~", 16, 28);
        g.setFont(f);
        g.translate(-p.x, -p.y);
    }
}
