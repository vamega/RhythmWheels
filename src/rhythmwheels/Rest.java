package RhythmWheels;

import java.awt.Graphics;

public class Rest extends Sound
{

    public Rest()
    {
        super("rest");
        volumeLevel = 1;
    }

    @Override
    public void changeVolume()
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
        g.drawLine(w / 2 - 5, h / 2 - 2, w / 2 + 5, h / 2 - 2);
        g.translate(-p.x, -p.y);
        /*if (lowRes)
        {
        g2.scale(1.0/scaleFactor, 1.0/scaleFactor);
        }*/

    }
}
