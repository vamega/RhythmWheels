package RhythmWheels;

import java.awt.Graphics;

public class BassDrum extends Sound
{

    public BassDrum()
    {
        super("bassdrum");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        // Outer circle
        g.drawOval(3 * w / 8 - 4, h / 4 - 4, w / 4 + 8, w / 4 + 8);
        // Inner circle
        g.drawOval(3 * w / 8, h / 4, w / 4, w / 4);
        g.translate(-p.x, -p.y);
    }
}
