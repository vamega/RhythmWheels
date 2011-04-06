package RhythmWheels;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Neck extends Sound
{

    public Neck()
    {
        super("neck");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawArc(3 * w / 8, h / 4, w / 4, w / 4, 0, -180);
        g.drawLine(3 * w / 8, 3 * h / 8 - 1, 5 * w / 8 - 1, 3 * h / 8 - 1);
        g.translate(-p.x, -p.y);
    }
}
