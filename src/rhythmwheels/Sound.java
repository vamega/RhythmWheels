package RhythmWheels;

import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.net.URL;
import java.io.IOException;

public abstract class Sound implements Cloneable, Serializable
{

    protected final static BasicStroke mediumStroke = new BasicStroke(2.0f);
    protected static Color SOUND_COLOR = Color.cyan;
    protected Color backgroundColor = Color.black;
    protected static int SOUND_LENGTH = 250; // milliseconds
    protected static int DELAY_LENGTH = 50; // milliseconds
    public String strCurrentFileName;       // such as clap1
    protected AudioClip audioClip;          // such as clap
    public String strFileBaseName;
    protected static int w = 50;
    protected static int h = 50;
    protected static int MAX_VOLUME = 3;  // Max number of volumes for each sound
    protected int volumeLevel = 1;        // 1 is default - softest
    protected Point p = new Point(0, 0);  // the top left point before rotation
    protected Point cp = new Point(0, 0); // Center point after rotation
    public int MAX_SOUND_SIZE = 100000;
    private byte[] soundBytes = new byte[MAX_SOUND_SIZE];
    public static String SOUND_DIR = "sounds/";
    public static String EXTENSION = ".au";
    public static double scaleFactor = 1.0; // For low resolution screens

    public Sound(String fileName)
    {
        strFileBaseName = fileName;
        strCurrentFileName = SOUND_DIR + strFileBaseName + volumeLevel + EXTENSION;
        audioClip = getAudioClip();

    }

    public AudioClip getAudioClip()
    {
        AudioClip ac = null;
        URL u;
        ac = java.applet.Applet.newAudioClip(RhythmWheel.class.getResource(strCurrentFileName));
        return ac;
    }

    // Increments the volume level, or resets to 1
    public void changeVolume()
    {
        volumeLevel = (volumeLevel + 1);
        if (volumeLevel > MAX_VOLUME)
        {
            volumeLevel = 1;
        }

        strCurrentFileName = SOUND_DIR + strFileBaseName + volumeLevel + EXTENSION;
        audioClip = getAudioClip();

        // Change the background color
        switch (volumeLevel)
        {
            case 2:
                backgroundColor = Color.darkGray.darker().darker().darker();
                break;
            case 3:
                backgroundColor = Color.darkGray.darker();
                break;
            default:
                backgroundColor = Color.black;
        }
        play();
    }

    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void play()
    {
        audioClip.play();
    }

    public void setPoint(Point point)
    {
        p = point;
    }

    public void setCenter(Point point)
    {
        cp = point;
    }

    public Point getCenter()
    {
        return cp;
    }

    public static final int getWidth()
    {
        return w;
    }

    public static final int getHeight()
    {
        return h;
    }

    public void paint(Graphics g)
    {
        // Draws the black triangle
        Graphics2D g2 = (Graphics2D) g;
        g2.scale(scaleFactor, scaleFactor);
        Point p1 = new Point(4 + p.x, 4 + p.y);
        Point p2 = new Point(w - 4 + p.x, 4 + p.y);
        Point p3 = new Point(w / 2 + p.x, h - 4 + p.y);
        int xpts[] = new int[3];
        int ypts[] = new int[3];
        xpts[0] = p1.x;
        xpts[1] = p2.x;
        xpts[2] = p3.x;
        ypts[0] = p1.y;
        ypts[1] = p2.y;
        ypts[2] = p3.y;
        g2.setColor(backgroundColor);
        g2.fillPolygon(xpts, ypts, 3);
        g2.setColor(Color.blue);
        g2.setStroke(mediumStroke);
        Line2D top = new Line2D.Double(p1, p2);
        Line2D left = new Line2D.Double(p1, p3);
        Line2D right = new Line2D.Double(p2, p3);
        g2.draw(top);
        g2.draw(left);
        g2.draw(right);
        paintMe(g2);
        g2.scale(1.0 / scaleFactor, 1.0 / scaleFactor);

    }

    // This gets overridden by child classes
    public void paintMe(Graphics g)
    {
    }
}
