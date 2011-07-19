package rhythmwheels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ImagePanel extends JPanel
{
    final static BasicStroke mediumStroke = new BasicStroke(2.0f);
    private int borderSize = 3;
    int count = 1;
    Sound sound;

    public ImagePanel(Sound s)
    {
        sound = s;
        setForeground(Color.cyan);
        setBackground(RhythmWheel.BACKGROUND_COLOR);
        if (RhythmWheel.isLowRes())
        {
            setPreferredSize(new Dimension(40, 40));
            borderSize = 2;
        }
        else
        {
            setPreferredSize(new Dimension(50, 50));
            borderSize = 3;
        }
        setBorder(new LineBorder(RhythmWheel.BACKGROUND_COLOR, borderSize));
    }

    public void hilite()
    {
        if (RhythmWheel.lowRes)
        {
            Graphics g = getGraphics();
            Color oldColor = g.getColor();
            g.setColor(Color.cyan);
            if (RhythmWheel.lowRes)
            {
                g.drawRect((getWidth() - sound.getWidth()) / 2, 0,
                           (int) (sound.getWidth() * Sound.scaleFactor),
                           (int) (sound.getHeight() * Sound.scaleFactor) - 2);
            }
            g.setColor(oldColor);
        }
        else
        {
            setBorder(new LineBorder(Color.cyan, borderSize));
        }
    }

    public void removehilite()
    {
        if (RhythmWheel.lowRes)
        {
            Graphics g = getGraphics();
            Color oldColor = g.getColor();
            g.setColor(RhythmWheel.BACKGROUND_COLOR);
            if (RhythmWheel.lowRes)
            {
                g.drawRect((getWidth() - sound.getWidth()) / 2, 0,
                           (int) (sound.getWidth() * Sound.scaleFactor),
                           (int) (sound.getHeight() * Sound.scaleFactor) - 2); 
            }
            g.setColor(oldColor);
        }
        else
        {
            setBorder(new LineBorder(RhythmWheel.BACKGROUND_COLOR, borderSize));
        }

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.cyan);
        g.translate((getWidth() - sound.getWidth()) / 2, 0);  // to center the sound
        sound.paint(g);
        g.translate(-(getWidth() - sound.getWidth()) / 2, 0);
    }
}