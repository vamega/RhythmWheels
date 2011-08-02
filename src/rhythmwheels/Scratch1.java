package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Scratch1 sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class Scratch1 extends Sound
{

    public Scratch1()
    {
        super("scratch1");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * WIDTH / 8, HEIGHT / 4, WIDTH / 4, WIDTH / 4);
        g.drawLine(WIDTH / 2 - 1, HEIGHT / 2 - 4, WIDTH / 2 - 1, HEIGHT / 2 + 2);
        g.translate(-p.x, -p.y);
    }
}
