package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Heel sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class Heel extends Sound
{

    public Heel()
    {
        super("heel");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * WIDTH / 8, HEIGHT / 4, WIDTH / 4, WIDTH / 4);
        g.drawLine(3 * WIDTH / 8 + 3, HEIGHT / 2 - 3, 5 * WIDTH / 8 - 3, HEIGHT / 4 + 3);
        g.translate(-p.x, -p.y);
    }
}
