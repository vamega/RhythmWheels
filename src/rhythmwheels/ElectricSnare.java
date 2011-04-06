package RhythmWheels;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class ElectricSnare extends Sound
{

    public ElectricSnare()
    {
        super("electricsnare");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawRect(3 * w / 8, h / 4 - 1, w / 4 + 3, w / 4 + 3);
        g.translate(-p.x, -p.y);
    }
}
