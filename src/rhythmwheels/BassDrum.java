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
        g.drawOval(3 * WIDTH / 8 - 4, HEIGHT / 4 - 4, WIDTH / 4 + 8, WIDTH / 4 + 8);
        // Inner circle
        g.drawOval(3 * WIDTH / 8, HEIGHT / 4, WIDTH / 4, WIDTH / 4);
        g.translate(-p.x, -p.y);
    }
}
