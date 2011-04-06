package RhythmWheels;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Mouth extends Sound
{

    public Mouth()
    {
        super("mouth");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.fillOval(7 * w / 16, h / 4 + 4, w / 8 + 1, w / 8 + 1);
        g.translate(-p.x, -p.y);
    }
}
