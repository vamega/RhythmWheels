package RhythmWheels;

import java.awt.Graphics;

public class Rest extends Sound
{

    public Rest()
    {
        super("rest");
    }

    @Override
    public void cycleVolume()
    {
    }

    @Override
    public void paintMe(Graphics g)
    {
        //super.paint(g);
        //Graphics2D g2= (Graphics2D) g;
        /*if (lowRes)
        {
        g2.scale(scaleFactor, scaleFactor);
        }*/
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawLine(WIDTH / 2 - 5, HEIGHT / 2 - 2, WIDTH / 2 + 5, HEIGHT / 2 - 2);
        g.translate(-p.x, -p.y);
        /*if (lowRes)
        {
        g2.scale(1.0/scaleFactor, 1.0/scaleFactor);
        }*/

    }
}
