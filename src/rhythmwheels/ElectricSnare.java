package RhythmWheels;

import java.awt.Graphics;

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
