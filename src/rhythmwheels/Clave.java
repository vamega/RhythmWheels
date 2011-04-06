package RhythmWheels;

import java.awt.Graphics;

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
        g.fillOval(7 * w / 16, h / 4 - 2, w / 8 + 1, w / 8 + 1);
        g.drawArc(3 * w / 8 + 1, 3 * h / 8 - 2, w / 4, w / 4, 0, -180);
        g.drawLine(3 * w / 8 + 2, h / 2 - 4, 5 * w / 8 - 1, h / 2 - 4);
        g.translate(-p.x, -p.y);
        //Graphics2D g2 = (Graphics2D)g;
        //g2.scale(1.0/.8, 1.0/.8);
    }
}
