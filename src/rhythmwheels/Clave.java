package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Clave sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class Clave extends Sound
{

    public Clave()
    {
        super("clave");
    }

    @Override
    public void paintMe(Graphics g)
    {
        //super.paint(g);
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.fillOval(7 * WIDTH / 16, HEIGHT / 4 - 2, WIDTH / 8 + 1, WIDTH / 8 + 1);
        g.drawArc(3 * WIDTH / 8 + 1, 3 * HEIGHT / 8 - 2, WIDTH / 4, WIDTH / 4, 0, -180);
        g.drawLine(3 * WIDTH / 8 + 2, HEIGHT / 2 - 4, 5 * WIDTH / 8 - 1, HEIGHT / 2 - 4);
        g.translate(-p.x, -p.y);
    }
}
