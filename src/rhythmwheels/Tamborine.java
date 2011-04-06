package RhythmWheels;

import java.awt.Graphics;

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
        int cx = 3 * w / 8 - 2;
        int cy = h / 4 - 2;
        int cw = w / 4 + 4;
        int ch = w / 4 + 4;
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
