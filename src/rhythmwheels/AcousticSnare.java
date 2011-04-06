package RhythmWheels;

import java.awt.Graphics;

public class AcousticSnare extends Sound
{

    public AcousticSnare()
    {
        super("acousticsnare");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawRect(3 * w / 8, h / 4 - 1, w / 4 + 3, w / 4 + 3);
        g.fillOval(7 * w / 16 + 1, h / 4 + 3, w / 8 + 1, w / 8 + 1);
        g.translate(-p.x, -p.y);
    }
}
