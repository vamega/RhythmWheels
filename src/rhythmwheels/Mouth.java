package rhythmwheels;

import java.awt.Graphics;

public class Mouth extends Sound
{

    public Mouth()
    {
        super("mouth");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.fillOval(7 * WIDTH / 16, HEIGHT / 4 + 4, WIDTH / 8 + 1, WIDTH / 8 + 1);
        g.translate(-p.x, -p.y);
    }
}
