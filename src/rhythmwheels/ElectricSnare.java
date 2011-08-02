package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Electic Snare sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class ElectricSnare extends Sound
{

    public ElectricSnare()
    {
        super("electricsnare");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawRect(3 * WIDTH / 8, HEIGHT / 4 - 1, WIDTH / 4 + 3, WIDTH / 4 + 3);
        g.translate(-p.x, -p.y);
    }
}
