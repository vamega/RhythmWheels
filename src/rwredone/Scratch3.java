package rwredone;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Scratch3 extends Sound
{

    public Scratch3()
    {
        super("scratch3");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        g.drawOval(3 * w / 8, h / 4, w / 4, w / 4);
        // Bottom line
        g.drawLine(w / 2 - 1, h / 2 - 4, w / 2 - 1, h / 2 + 2);
        // Left line
        g.drawLine(w / 2 - 5, h / 3 + 1, w / 2 - 9, h / 3 - 1);
        // Right line
        g.drawLine(w / 2 + 2, h / 3 + 1, w / 2 + 7, h / 3 - 1);
        g.translate(-p.x, -p.y);
    }
}
