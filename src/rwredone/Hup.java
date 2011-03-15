package rwredone;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Hup extends Sound
{

    public Hup()
    {
        super("hup");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        // Vertical
        g.drawLine(3 * w / 8 + 2, h / 4, 3 * w / 8 + 2, h / 2);
        g.drawLine(5 * w / 8 - 2, h / 4, 5 * w / 8 - 2, h / 2);

        // Horizontal
        g.drawLine(3 * w / 8 + 2, 3 * h / 8, 5 * w / 8 - 2, 3 * h / 8);
        g.translate(-p.x, -p.y);
    }
}
