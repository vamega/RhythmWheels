package RhythmWheels;

//package rhythmwheel;
import java.applet.AudioClip;
import java.awt.*;

public class LowFloorTom extends Sound
{

    public LowFloorTom()
    {
        super("lowfloortom");
    }

    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(Math.PI, w / 2.0, h / 2.0);
        Font f = g2.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        g2.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2.drawString("^", 16, 45);
        g2.setFont(f);
        g2.rotate(-Math.PI, w / 2.0, h / 2.0);
        g.translate(-p.x, -p.y);
    }
}
