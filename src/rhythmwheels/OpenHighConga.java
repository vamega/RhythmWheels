package rhythmwheels;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * A class to represent the Open High Conga sound
 * @author Varun Madiath (vamega@gmail.com)
 */
public class OpenHighConga extends Sound
{

    public OpenHighConga()
    {
        super("openhighconga");
    }

    @Override
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
