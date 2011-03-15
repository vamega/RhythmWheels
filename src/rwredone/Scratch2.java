package rwredone;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Scratch2 extends Sound
{

    public Scratch2()
    {
        super("scratch2");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * w / 8, h / 4, w / 4, w / 4);
        g.drawLine(w / 2 - 1, h / 2 - 9, w / 2 - 1, h / 4 - 2);
        g.drawLine(w / 2 - 1, h / 2 - 4, w / 2 - 1, h / 2 + 2);

        g.translate(-p.x, -p.y);
    }
}
