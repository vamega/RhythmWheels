package RhythmWheels;

import java.awt.Graphics;

public class Tube extends Sound
{

    public Tube()
    {
        super("tube");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        // Vertical
        g.drawLine(3 * w / 8 + 3, h / 4, 3 * w / 8 + 3, h / 2);
        g.drawLine(5 * w / 8 - 3, h / 4, 5 * w / 8 - 3, h / 2);
        g.drawOval(3 * w / 8 + 3, h / 4 - 2, 7, 5);

        // Horizontal
        g.drawLine(3 * w / 8 + 3, h / 2, 5 * w / 8 - 3, h / 2);
        g.translate(-p.x, -p.y);
    }
}
