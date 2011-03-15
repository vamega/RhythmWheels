package rwredone;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Crash extends Sound
{

    public Crash()
    {
        super("crash");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Font f = g.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(new Font("SansSerif", Font.PLAIN, 26));
        g.drawString("#", 17, 28);
        g.setFont(f);
        g.translate(-p.x, -p.y);
    }
}
