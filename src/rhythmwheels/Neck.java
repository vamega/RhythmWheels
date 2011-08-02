package rhythmwheels;

import java.awt.*;

/**
 * A class to represent the Neck sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class Neck extends Sound
{

    public Neck()
    {
        super("neck");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawArc(3 * WIDTH / 8, HEIGHT / 4, WIDTH / 4, WIDTH / 4, 0, -180);
        g.drawLine(3 * WIDTH / 8, 3 * HEIGHT / 8 - 1, 5 * WIDTH / 8 - 1, 3 * HEIGHT / 8 - 1);
        g.translate(-p.x, -p.y);
    }
}
