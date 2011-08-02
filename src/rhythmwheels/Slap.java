package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Slap sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class Slap extends Sound
{
    public Slap()
    {
        super("slap");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        // Vertical
        g.drawLine(WIDTH / 2, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        // Horizontal
        g.drawLine(3 * WIDTH / 8, 3 * HEIGHT / 8, 5 * WIDTH / 8, 3 * HEIGHT / 8);
        // Top left to bottom right
        //g2.drawLine(7*w/16, 5*h/16, 9*w/16, 7*h/16);
        g.drawLine(3 * WIDTH / 8 + 1, HEIGHT / 4 + 1, 5 * WIDTH / 8 - 1, HEIGHT / 2 - 1);
        g.drawLine(3 * WIDTH / 8 + 1, HEIGHT / 2 - 1, 5 * WIDTH / 8 - 1, HEIGHT / 4 + 1);
        g.translate(-p.x, -p.y);
    }
}
