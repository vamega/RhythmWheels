package rwredone;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class HiHatO extends Sound
{

    public HiHatO()
    {
        super("hihato");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawLine(3 * w / 8 + 1, h / 4 + 1, 3 * w / 8 + 1, h / 2 - 1);
        g.drawLine(5 * w / 8 - 1, h / 2 - 1, 5 * w / 8 - 1, h / 4 + 1);
        g.translate(-p.x, -p.y);
    }
}
