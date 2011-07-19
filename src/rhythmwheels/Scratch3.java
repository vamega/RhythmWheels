package rhythmwheels;

import java.awt.Graphics;

public class Scratch3 extends Sound
{

    public Scratch3()
    {
        super("scratch3");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * WIDTH / 8, HEIGHT / 4, WIDTH / 4, WIDTH / 4);
        // Bottom line
        g.drawLine(WIDTH / 2 - 1, HEIGHT / 2 - 4, WIDTH / 2 - 1, HEIGHT / 2 + 2);
        // Left line
        g.drawLine(WIDTH / 2 - 5, HEIGHT / 3 + 1, WIDTH / 2 - 9, HEIGHT / 3 - 1);
        // Right line
        g.drawLine(WIDTH / 2 + 2, HEIGHT / 3 + 1, WIDTH / 2 + 7, HEIGHT / 3 - 1);
        g.translate(-p.x, -p.y);
    }
}
