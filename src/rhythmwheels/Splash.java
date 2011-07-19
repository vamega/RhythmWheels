package rhythmwheels;

import java.awt.Graphics;

public class Splash extends Sound
{

    public Splash()
    {
        super("splash");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);

        g.drawLine(3 * WIDTH / 8, HEIGHT / 2 - 2, 5 * WIDTH / 8, HEIGHT / 2 - 2);
        g.drawLine(3 * WIDTH / 8, HEIGHT / 2 - 10, 5 * WIDTH / 8, HEIGHT / 2 - 10);
        g.translate(-p.x, -p.y);
    }
}
