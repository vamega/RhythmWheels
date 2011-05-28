package RhythmWheels;

import java.awt.Graphics;

public class Mouth extends Sound
{

    public Mouth()
    {
        super("mouth");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.fillOval(7 * w / 16, h / 4 + 4, w / 8 + 1, w / 8 + 1);
        g.translate(-p.x, -p.y);
    }
}
