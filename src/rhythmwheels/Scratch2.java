package rhythmwheels;

import java.awt.Graphics;

public class Scratch2 extends Sound
{

    public Scratch2()
    {
        super("scratch2");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * WIDTH / 8, HEIGHT / 4, WIDTH / 4, WIDTH / 4);
        g.drawLine(WIDTH / 2 - 1, HEIGHT / 2 - 9, WIDTH / 2 - 1, HEIGHT / 4 - 2);
        g.drawLine(WIDTH / 2 - 1, HEIGHT / 2 - 4, WIDTH / 2 - 1, HEIGHT / 2 + 2);

        g.translate(-p.x, -p.y);
    }
}
