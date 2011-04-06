package RhythmWheels;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Slap extends Sound
{

    public Slap()
    {
        super("slap");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        // Vertical
        g.drawLine(w / 2, h / 4, w / 2, h / 2);
        // Horizontal
        g.drawLine(3 * w / 8, 3 * h / 8, 5 * w / 8, 3 * h / 8);
        // Top left to bottom right
        //g2.drawLine(7*w/16, 5*h/16, 9*w/16, 7*h/16);
        g.drawLine(3 * w / 8 + 1, h / 4 + 1, 5 * w / 8 - 1, h / 2 - 1);
        g.drawLine(3 * w / 8 + 1, h / 2 - 1, 5 * w / 8 - 1, h / 4 + 1);
        g.translate(-p.x, -p.y);
    }
}
