package RhythmWheels;

//package rhythmwheel;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.event.*;

public class Wheel extends JPanel implements MouseListener
{

    private int radius; // Radius of the Wheel
    private volatile int soundsPlayedCounter = 0;
    private Vector sounds;
    private double rotationAngle = 0.0;
    private double previousRotationAngle = 0.0;
    double[] scales =
    {
        1, 1, 1, 1, 1, 1, 1, 1, .92, .85, .82, .79, .75, .70, .67, .64
    }; // by experimentation
    double[] translators =
    {
        0, 0, 0, 0, 0, 0, 0, 0, 13.5, 27, 33, 39, 48, 62, 70, 79
    };

    public Wheel()
    {
        addMouseListener(this);
        if (RhythmWheel.lowRes)
        {
            setMinimumSize(new Dimension(200, 200));
            setPreferredSize(new Dimension(200, 200));
            for (int i = 0; i < translators.length; i++)
            {
                translators[i] = translators[i] + 20;
                scales[i] *= Sound.scaleFactor;
            }
        }
        else
        {
            setMinimumSize(new Dimension(200, 300));
            setPreferredSize(new Dimension(250, 300));
        }
        sounds = new Vector();
        Rest r = new Rest();
        sounds.addElement(r);
    }

    public int getSoundsPlayedCounter()
    {
        return soundsPlayedCounter;
    }

    public void setSoundsPlayedCounter(int newValue)
    {
        soundsPlayedCounter = newValue;
    }

    public double getRotationAngle()
    {
        return rotationAngle;
    }

    public double getPreviousRotationAngle()
    {
        return previousRotationAngle;
    }

    public void setPreviousRotationAngle(double previousRotationAngle)
    {
        this.previousRotationAngle = previousRotationAngle;
    }

    public void setRotationAngle(double angle)
    {
        rotationAngle = angle;
        repaint();
    }

    public Vector getSounds()
    {
        return sounds;
    }

    public boolean isBlank()
    {
        for (int i = 0; i < sounds.size(); i++)
        {
            Sound s = (Sound) sounds.elementAt(i);
            if (!(s instanceof Rest))
            {
                return false;
            }
        }
        return true;
    }

    // Sets the center of the points based on the number of sounds.
    public void setPoints()
    {
        Point c = new Point(getWidth() / 2 - Sound.getWidth() / 2,
                            getHeight() / 2);
        Point p = (Point) c.clone();

        int numsounds = sounds.size();
        if (numsounds == 1)
        {
            p.y -= Sound.getHeight() / 2 - 3;
        }
        else if (numsounds == 2)
        {
            p.y -= 4;
        }
        else if (numsounds == 3)
        {
            p.y += 9;
        }
        else if (numsounds == 4)
        {
            p.y += Sound.getHeight() / 2 - 8;
        }
        else if (numsounds == 5)
        {
            p.y += Sound.getHeight() / 2;
        }
        else if (numsounds == 6)
        {
            p.y += Sound.getHeight() / 2 + 8;
        }
        else if (numsounds == 7)
        {
            p.y += Sound.getHeight() - 8;
        }
        else if (numsounds == 8)
        {
            p.y += Sound.getHeight() - 2;
        }
        else if (numsounds == 9)
        {
            p.y += Sound.getHeight() + 7;
        }
        else if (numsounds == 10)
        {
            p.y += Sound.getHeight() + 16;
        }
        else if (numsounds == 11)
        {
            p.y += Sound.getHeight() + 20;
        }
        else if (numsounds == 12)
        {
            p.y += Sound.getHeight() + 25;
        }
        else if (numsounds == 13)
        {
            p.y += Sound.getHeight() + 31;
        }
        else if (numsounds == 14)
        {
            p.y += Sound.getHeight() + 40;
        }
        else if (numsounds == 15)
        {
            p.y += Sound.getHeight() + 47;
        }
        else
        {
            p.y += Sound.getHeight() + 55;
        }
        int hyp = p.y - c.y + Sound.getHeight() / 2;
        int soundnum = numsounds - 1;
        double theta, angle;
        for (int i = 0; i < numsounds; i++)
        {
            Sound s = (Sound) sounds.elementAt(i);
            Point soundCenter = new Point();
            theta = i * -2.0 * Math.PI / numsounds - Math.PI / 2.0;
            soundCenter.x = c.x - (int) (Math.cos(theta) * hyp)
                            + (int) (25 * Sound.scaleFactor);
            soundCenter.y = c.y - (int) (Math.sin(theta) * hyp);
            s.setPoint(p);
            s.setCenter(soundCenter);
        }

        if (numsounds == 1)
        {
            radius = 30;
        }
        else
        {
            radius = p.y - c.y + Sound.getHeight() - 1;
        }
    }

    // Sets the number of sounds.  Adds rests if necessary
    public void setNumSounds(int i)
    {
        int numSounds = sounds.size();
        if (numSounds == i)
        {
            setPoints();
            return;
        }
        int oldNum = numSounds;
        numSounds = i;
        if (oldNum < numSounds)
        {
            for (int j = oldNum; j < numSounds; j++)
            {
                sounds.addElement(new Rest());
            }
        }
        else
        {
            sounds.setSize(numSounds);
        }
        setPoints();
    }
    boolean first = true;

    // Paints the sounds, outer circle, and triangle
    @Override
    public synchronized void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (first)
        {
            setPoints();
            first = false;
        }
        Graphics2D g2 = (Graphics2D) g;

        Point c = new Point(getWidth() / 2, getHeight() / 2);

        int numsounds = sounds.size();
        int soundnum = sounds.size() - 1;
        g2.scale(scales[soundnum], scales[soundnum]);
        g2.translate(translators[soundnum], translators[soundnum]);

        // Draw the wheel, rotating if necessary
        g2.rotate(rotationAngle, c.x, c.y);

        for (int i = 0; i < numsounds; i++)
        {
            Sound s = (Sound) sounds.elementAt(i);
            g2.scale(1 / Sound.scaleFactor, 1 / Sound.scaleFactor);
            //g2.drawOval(s.getCenter().x - 10, s.getCenter().y - 10, 25, 25);
            s.paint(g2);
            g2.scale(Sound.scaleFactor, Sound.scaleFactor);
            g2.rotate(-2.0 * Math.PI / numsounds, c.x, c.y);
        }

        // Draw the bottom triangle and the outer circle (not rotated)
        g2.rotate(-rotationAngle, c.x, c.y);
        g2.setColor(Color.blue);
        g2.drawOval(c.x - radius, c.y - radius, radius * 2, radius * 2);
        g2.setColor(Color.cyan);
        g2.drawArc(c.x - radius - 8, c.y - radius - 8, radius * 2 + 10,
                   radius * 2 + 10, 90, 60);
        int xpts[] = new int[3];
        int ypts[] = new int[3];
        xpts[0] = c.x;
        xpts[1] = c.x - 4;
        xpts[2] = c.x + 4;
        ypts[0] = c.y + radius;
        ypts[1] = ypts[0] + 9;
        ypts[2] = ypts[1];
        g2.fillPolygon(new Polygon(xpts, ypts, 3));
        //ADD
        g2.drawString(Integer.toString(soundsPlayedCounter), xpts[0] - 4, ypts[2] + 15);
        //End Add
        ypts[0] = c.y - radius - 8;
        ypts[1] = ypts[0] - 4;
        ypts[2] = ypts[0] + 4;
        xpts[1] = xpts[0] - 7;
        xpts[2] = xpts[1];
        g2.fillPolygon(new Polygon(xpts, ypts, 3));
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            g2.scale(1.0 / .8, 1.0 / .8);
        }
    }

    // Allows the glass pane to drop a sound.  Replaces the sound on the wheel if it's over
    // a sound; otherwise does nothing.
    public void drop(Sound dropSound, Point mousePt)
    {
        int soundNum = sounds.size() - 1;
        Point translatedPt = new Point();
        translatedPt.x = (int) (-translators[soundNum]
                                + mousePt.x / scales[soundNum]);
        translatedPt.y = (int) (-translators[soundNum]
                                + mousePt.y / scales[soundNum]);
        int minIndex = findClosestSound(translatedPt);
        Sound minSound = (Sound) sounds.elementAt(minIndex);
        double mindist = dist(minSound.getCenter(), translatedPt);
        // Replace it if we're close enough
        if (mindist < Sound.getHeight() / 2)
        {
            sounds.setElementAt(dropSound, minIndex);
            setPoints();
            repaint();
        }
    }

    private Point translatePoint(Point p)
    {
        Point p1 = (Point) p.clone();
        int soundNum = sounds.size() - 1;
        p1.x = (int) (p1.x * scales[soundNum] + translators[soundNum]);
        p1.y = (int) (p1.y * scales[soundNum] + translators[soundNum]);
        return p1;
    }

    // Returns the INDEX of the sound closest to Point p
    private int findClosestSound(Point p)
    {
        Sound minSound = (Sound) sounds.elementAt(0);
        double mindist = dist(p, minSound.getCenter());
        double d;
        int minIndex = 0;
        int numsounds = sounds.size();

        // Find the closest sound
        for (int i = 1; i < numsounds; i++)
        {
            Sound s = (Sound) sounds.elementAt(i);
            d = dist(p, s.getCenter());
            if (d < mindist)
            {
                mindist = d;
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Returns the distance between points p1 and p2
    private double dist(Point p1, Point p2)
    {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x)
                         + (p1.y - p2.y) * (p1.y - p2.y));
    }

    // Mouse Listener methods
    public void mousePressed(MouseEvent evt)
    {
        int soundNum = sounds.size() - 1;
        Point mousePt = evt.getPoint();
        Point translatedPt = new Point();
        translatedPt.x = (int) (-translators[soundNum]
                                + mousePt.x / scales[soundNum]);
        translatedPt.y = (int) (-translators[soundNum]
                                + mousePt.y / scales[soundNum]);

        Sound closest = (Sound) sounds.elementAt(findClosestSound(translatedPt));
        if (dist(closest.getCenter(), translatedPt) < Sound.getHeight() / 2)
        {
            closest.changeVolume();
            // Repaint so the color change takes effect
        }
        repaint();
    }

    public void mouseReleased(MouseEvent evt)
    {
    }

    public void mouseClicked(MouseEvent evt)
    {
    }

    public void mouseEntered(MouseEvent evt)
    {
    }

    public void mouseExited(MouseEvent evt)
    {
    }
}
