package RhythmWheels;

import java.awt.Graphics;

public class Clap extends Sound
{

    public Clap()
    {
        super("clap");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        // Top left to bottom right
        //g2.drawLine(7*w/16, 5*h/16, 9*w/16, 7*h/16);
        g.drawLine(3 * WIDTH / 8 + 1, HEIGHT / 4 + 1, 5 * WIDTH / 8 - 1, HEIGHT / 2 - 1);
        g.drawLine(3 * WIDTH / 8 + 1, HEIGHT / 2 - 1, 5 * WIDTH / 8 - 1, HEIGHT / 4 + 1);
        g.translate(-p.x, -p.y);
    }
}
