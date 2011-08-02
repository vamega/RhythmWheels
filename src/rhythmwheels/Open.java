package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Open sound
 * @author Varun Madiath (vamega@gmail.com)
 */
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
        g.drawOval(3 * WIDTH / 8, HEIGHT / 4, WIDTH / 4, WIDTH / 4);
        g.translate(-p.x, -p.y);
    }
}
