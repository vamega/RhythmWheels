package RhythmWheels;

import java.awt.Graphics;
import java.awt.Point;

public class Tip extends Sound
{

    public Tip()
    {
        super("tip");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Point pt1 = new Point(w / 2, 7);
        Point pt2 = new Point(w / 4 + 5, h / 2);
        Point pt3 = new Point(3 * w / 4 - 3, h / 2);
        g.drawLine(pt1.x, pt1.y, pt2.x, pt2.y);
        g.drawLine(pt1.x, pt1.y, pt3.x, pt3.y);
        g.drawLine(pt2.x, pt2.y, pt3.x, pt3.y);
        g.translate(-p.x, -p.y);
    }
}
