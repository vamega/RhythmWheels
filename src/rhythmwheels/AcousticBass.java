package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the AcousticBass sound
 * @author Varun Madiath (vamega@gmail.com)
 */

public class AcousticBass extends Sound
{

    public AcousticBass()
    {
        super("acousticbass");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * WIDTH / 8 - 1, HEIGHT / 4 - 1, WIDTH / 4 + 3, WIDTH / 4 + 3);
        g.fillOval(7 * WIDTH / 16, HEIGHT / 4 + 3, WIDTH / 8 + 1, WIDTH / 8 + 1);
        g.translate(-p.x, -p.y);
    }
}
