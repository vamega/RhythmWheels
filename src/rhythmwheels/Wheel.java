package rhythmwheels;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JPanel;

public class Wheel extends JPanel implements MouseListener
{

    private WheelModel model;
    private int radius; // Radius of the Wheel
    private double previousRotationAngle = 0.0;
    double[] scales =
    {
        1, 1, 1, 1, 1, 1, 1, 1, 1, .92, .85, .82, .79, .75, .70, .67, .64
    };
    // by experimentation
    double[] translators =
    {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 13.5, 27, 33, 39, 48, 62, 70, 79
    };

    public Wheel(WheelModel model)
    {
        this.model = model;
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
    }

    /**
     * Returns the value of the counter denoting the number of sounds in the wheel that have finished
     * playing.
     * @return The value of the counter.
     */
    public int getSoundsPlayedCounter()
    {
        return model.getPlayedCounter();
    }

    /**
     * Sets the value of the counter denoting the number of sounds in the wheel that have finished
     * playing.
     * @param newValue  The new value of the counter.
     */
    public void setSoundsPlayedCounter(int newValue)
    {
        model.setPlayedCounter(newValue);
    }

    /**
     * Returns the angle at which the wheel is currently rotated by, in radians.
     * @return The angle at which the wheel is currently rotated by.
     */
    public double getRotationAngle()
    {
        return model.getRotation();
    }

    /*
     * TODO: Document this function, and improve the documentation of setPreviousRotationAngle.
     */
    public double getPreviousRotationAngle()
    {
        return previousRotationAngle;
    }

    /**
     * Sets the rotationAngle of the wheel prior to the last increment of the counter.
     * @param previousRotationAngle The new value for the previousRotationAngle.
     */
    public void setPreviousRotationAngle(double previousRotationAngle)
    {
        this.previousRotationAngle = previousRotationAngle;
    }

    /**
     * Sets the angle by which the wheel is rotated from it's normal position.
     * The wheel is not repainted with a call to this method
     * @param angle The angle by which the wheel should be rotated, in radians.
     */
    public void setRotationAngle(double angle)
    {
        model.setRotation(angle);
    }
    
    public WheelModel getWheelModel()
    {
        return model;
    }

    /**
     * Returns a list of all the Sounds in the wheel. Modifications to this list will modify the 
     * sounds in the Wheel.
     * @return a List of all the Sounds in the wheel.
     */
    public List<Sound> getSounds()
    {
        return model.getWheelSounds();
    }

    /**
     * Checks whether there is any sound in the Wheel.
     * @return true if the wheel has a sound in it, false if the wheel compromises only of Rests.
     */
    public boolean isBlank()
    {
        return model.isWheelEmpty();
    }

    // Sets the center of the points based on the number of sounds.
    /**
     * Sets the center of the points based on the number of sounds.
     */
    public void setPoints()
    {
        Point c = new Point(getWidth() / 2 - Sound.getWidth() / 2,
                            getHeight() / 2);
        Point p = (Point) c.clone();

        int numsounds = model.getWheelCapacity();
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
            Sound s = model.getSoundAtPosition(i);
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
        model.setWheelCapacity(i);
        setPoints();
    }
    boolean first = true;

    // Paints the sounds, outer circle, and triangle
    @Override
    public synchronized void paintComponent(Graphics g)
    {
        /*
         * TODO: Write extensive comments here, as graphics code is very difficult to understand 
         * without lots of coments.
         */
        super.paintComponent(g);

        if (first)
        {
            setPoints();
            first = false;
        }
        Graphics2D g2 = (Graphics2D) g;

        Point c = new Point(getWidth() / 2, getHeight() / 2);

        int numsounds = model.getWheelCapacity();
        g2.scale(scales[numsounds], scales[numsounds]);
        g2.translate(translators[numsounds], translators[numsounds]);

        // Draw the wheel, rotating if necessary
        g2.rotate(model.getRotation(), c.x, c.y);

        for (int i = 0; i < numsounds; i++)
        {
            Sound s = model.getSoundAtPosition(i);
            g2.scale(1 / Sound.scaleFactor, 1 / Sound.scaleFactor);
            //g2.drawOval(s.getCenter().x - 10, s.getCenter().y - 10, 25, 25);
            s.paint(g2);
            g2.scale(Sound.scaleFactor, Sound.scaleFactor);
            g2.rotate(-2.0 * Math.PI / numsounds, c.x, c.y);
        }
        // Draw the bottom triangle and the outer circle (not rotated)
        g2.rotate(-model.getRotation(), c.x, c.y);
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
        g2.fillPolygon(xpts, ypts, 3);

        // <editor-fold defaultstate="collapsed" desc="Beging drawing the sound counter">
        // Begin drawing the sound counter.

        // Reverse the scaling so that the rendered text does not shrink when the wheel shrinks
        g2.scale(1 / scales[numsounds], 1 / scales[numsounds]);

        /*
         * All things that aren't affected by the scale factor, like constants and values got
         * from methods that aren't sensitive to this Graphics' object's scale factor must be
         * multiplied by the current scale factor.
         */

        // p is the Point where the baseline of the first character of the counter will be drawn.
        Point p = new Point(xpts[0], ypts[2] + (int) (15 * 1 / scales[numsounds]));

        // Get the bounds of the string to be drawn so that we can center it on the canvas.
        Rectangle2D stringBounds = getFont().getStringBounds(Integer.toString(model.getPlayedCounter()),
                                                             g2.getFontRenderContext());
        double stringWidth = (stringBounds.getMaxX() + stringBounds.getMinX());

        /*
         * Set x so that the string is center. As the string width is not dependant on the scale 
         * factor we must explicitly multiply this value bu the scale factor.
         */
        p.x -= (stringWidth / 2) * (1 / scales[numsounds]);

        /*
         * Draw the string, we must transform p to the original coordinate system 
         * (which we reversed just prior to this).
         */
        g2.drawString(Integer.toString(model.getPlayedCounter()), (float) (p.x * scales[numsounds]),
                      (float) (p.y * scales[numsounds]));
        // Reverse the scaling, so that the rest of the code draws with the scaled values.
        g2.scale(scales[numsounds], scales[numsounds]);

        // End drawing the sound counter.
        //</editor-fold>

        ypts[0] = c.y - radius - 8;
        ypts[1] = ypts[0] - 4;
        ypts[2] = ypts[0] + 4;
        xpts[1] = xpts[0] - 7;
        xpts[2] = xpts[1];
        g2.fillPolygon(new Polygon(xpts, ypts, 3));
        if (RhythmWheel.isLowRes())
        {
            g2.scale(1.0 / .8, 1.0 / .8);
        }
    }

    /**
     * Replaces the sound on the wheel closest to a Point if the distance between the Point and the
     * Sound's center isn't too large. Does nothing if the distance is too large. This allows a 
     * Sound to be dropped onto the Wheel, and the Point in this case is the position of the mouse
     * when the mouse button was released.
     * 
     * @param dropSound The sound to place on the wheel.
     * @param mousePt  The point where the mouse button was released.
     */
    public void drop(Sound dropSound, Point mousePt)
    {
        int soundNum = model.getWheelCapacity() - 1;
        Point translatedPt = new Point();
        translatedPt.x = (int) (-translators[soundNum]
                                + mousePt.x / scales[soundNum]);
        translatedPt.y = (int) (-translators[soundNum]
                                + mousePt.y / scales[soundNum]);
        int minIndex = findClosestSound(translatedPt);
        Sound minSound = model.getSoundAtPosition(minIndex);
        double mindist = dist(minSound.getCenter(), translatedPt);

        // Replace it if we're close enough
        if (mindist < Sound.getHeight() / 2)
        {
            model.placeSoundInWheel(dropSound, minIndex);
            setPoints();
            repaint();
        }
    }

    /*
     * TODO: try and figure out what exactly this method does, and then decide whether to keep it
     * around or not. As of now there is no code that calls this method.
     */
    private Point translatePoint(Point p)
    {
        Point p1 = (Point) p.clone();
        int soundNum = model.getWheelCapacity() - 1;
        p1.x = (int) (p1.x * scales[soundNum] + translators[soundNum]);
        p1.y = (int) (p1.y * scales[soundNum] + translators[soundNum]);
        return p1;
    }

    /**
     * Returns the index of the sound in the Wheel closest to Point p.
     * @param p A Point.
     * @return The index of the sound in the wheel that is closest to p.
     */
    private int findClosestSound(Point p)
    {
        Sound minSound = model.getSoundAtPosition(0);
        double mindist = dist(p, minSound.getCenter());
        double d;
        int minIndex = 0;
        int numsounds = model.getWheelCapacity();

        // Find the closest sound
        for (int i = 1; i < numsounds; i++)
        {
            Sound s = model.getSoundAtPosition(i);
            d = dist(p, s.getCenter());
            if (d < mindist)
            {
                mindist = d;
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Calculates the distance between two points on Cartesian coordinate system.
     * @param p1 One of the points.
     * @param p2 The other point.
     * @return The distance between the two points.
     */
    private double dist(Point p1, Point p2)
    {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x)
                         + (p1.y - p2.y) * (p1.y - p2.y));
    }

    // Mouse Listener methods
    public void mousePressed(MouseEvent evt)
    {
        int soundNum = model.getWheelCapacity() - 1;
        Point mousePt = evt.getPoint();
        Point translatedPt = new Point();
        translatedPt.x = (int) (-translators[soundNum]
                                + mousePt.x / scales[soundNum]);
        translatedPt.y = (int) (-translators[soundNum]
                                + mousePt.y / scales[soundNum]);

        Sound closest = model.getSoundAtPosition(findClosestSound(translatedPt));
        if (dist(closest.getCenter(), translatedPt) < Sound.getHeight() / 2)
        {
            closest.cycleVolume();
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
