package rhythmwheels;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A class to represent the Low Floor Tom sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class LowFloorTom extends Sound
{

    public LowFloorTom()
    {
        super("lowfloortom");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(Math.PI, WIDTH / 2.0, HEIGHT / 2.0);
        Font f = g2.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        g2.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2.drawString("^", 16, 45);
        g2.setFont(f);
        g2.rotate(-Math.PI, WIDTH / 2.0, HEIGHT / 2.0);
        g.translate(-p.x, -p.y);
    }
}
