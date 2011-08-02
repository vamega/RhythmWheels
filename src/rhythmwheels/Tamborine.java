package rhythmwheels;

import java.awt.Graphics;

/**
 * A class to represent the Tamborine sound.
 * @author Varun Madiath <vamega@gmail.com>
 */
public class Tamborine extends Sound
{

    public Tamborine()
    {
        super("tamborine");
    }

    @Override
    public void paintMe(Graphics g)
    {
        g.translate(p.x, p.y);
        g.setColor(SOUND_COLOR);
        int cx = 3 * WIDTH / 8 - 2;
        int cy = HEIGHT / 4 - 2;
        int cw = WIDTH / 4 + 4;
        int ch = WIDTH / 4 + 4;
        g.drawOval(cx, cy, cw, ch);
        // top circle
        g.fillOval(cx + cw / 2 - 3, cy - 4, 7, 7);
        // bottom
        g.fillOval(cx + cw / 2 - 3, cy + ch - 3, 7, 7);
        // left
        g.fillOval(cx - 4, cy + ch / 2 - 3, 7, 7);
        // right
        g.fillOval(cx + cw - 3, cy + ch / 2 - 3, 7, 7);


        g.translate(-p.x, -p.y);
    }
}
