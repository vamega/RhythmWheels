package RhythmWheels;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class Maracas extends Sound
{

    public Maracas()
    {
        super("maracas");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Font f = g.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString("~", 16, 28);
        g.setFont(f);
        g.translate(-p.x, -p.y);
    }
}
