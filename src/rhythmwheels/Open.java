package RhythmWheels;

import java.awt.Graphics;

public class Open extends Sound
{

    public Open()
    {
        super("open");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * w / 8, h / 4, w / 4, w / 4);
        g.translate(-p.x, -p.y);
    }
}
