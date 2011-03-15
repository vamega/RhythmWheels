package rwredone;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Heel extends Sound
{

    public Heel()
    {
        super("heel");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * w / 8, h / 4, w / 4, w / 4);
        g.drawLine(3 * w / 8 + 3, h / 2 - 3, 5 * w / 8 - 3, h / 4 + 3);
        g.translate(-p.x, -p.y);
    }
}
