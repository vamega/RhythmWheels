package rwredone;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class OpenHighConga extends Sound
{

    public OpenHighConga()
    {
        super("openhighconga");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Font f = g.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(new Font("Monospaced", Font.BOLD, 30));
        g.drawString("^", 16, 32);
        g.setFont(f);
        g.translate(-p.x, -p.y);
    }
}
