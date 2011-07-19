package RhythmWheels;

import java.awt.Graphics;

public class Hup extends Sound
{
    public Hup()
    {
        super("hup");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        // Vertical
        g.drawLine(3 * WIDTH / 8 + 2, HEIGHT / 4, 3 * WIDTH / 8 + 2, HEIGHT / 2);
        g.drawLine(5 * WIDTH / 8 - 2, HEIGHT / 4, 5 * WIDTH / 8 - 2, HEIGHT / 2);

        // Horizontal
        g.drawLine(3 * WIDTH / 8 + 2, 3 * HEIGHT / 8, 5 * WIDTH / 8 - 2, 3 * HEIGHT / 8);
        g.translate(-p.x, -p.y);
    }
}
