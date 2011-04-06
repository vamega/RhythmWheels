package RhythmWheels;

import java.awt.Graphics;

public class HiHat extends Sound
{

    public HiHat()
    {
        super("hihat");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * w / 8 - 5, h / 4 - 3, w / 4 + 10, w / 4 - 2);
        g.drawLine(w / 2 - 1, h / 2 - 4, w / 2 - 1, h / 2 + 2);
        g.translate(-p.x, -p.y);
    }
}
