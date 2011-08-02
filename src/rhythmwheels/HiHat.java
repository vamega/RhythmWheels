package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Hi Hat sound
 * @author Varun Madiath (vamega@gmail.com)
 */
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
        g.drawOval(3 * WIDTH / 8 - 5, HEIGHT / 4 - 3, WIDTH / 4 + 10, WIDTH / 4 - 2);
        g.drawLine(WIDTH / 2 - 1, HEIGHT / 2 - 4, WIDTH / 2 - 1, HEIGHT / 2 + 2);
        g.translate(-p.x, -p.y);
    }
}
