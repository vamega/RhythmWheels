package RhythmWheels;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class AcousticBass extends Sound
{

    public AcousticBass()
    {
        super("acousticbass");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * w / 8 - 1, h / 4 - 1, w / 4 + 3, w / 4 + 3);
        g.fillOval(7 * w / 16, h / 4 + 3, w / 8 + 1, w / 8 + 1);
        g.translate(-p.x, -p.y);
    }
}
