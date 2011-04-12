package RhythmWheels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SoundPanel extends JPanel implements MouseListener
{

    public static Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public static Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    Vector imagePanels = new Vector();
    RhythmWheel rhythmWheel;
    Vector labels2 = new Vector(); // For sounds that require 2 labels
    Vector panels2 = new Vector(); // " "
    imagePanel[] panels;
    JLabel[] labels;

    // Have to give it the applet so it can use getContextBase() to find the audio files
    public SoundPanel(RhythmWheel r, SoundCategory soundcat)
    {
        rhythmWheel = r;
        setBackground(BACKGROUND_COLOR);
        setCategory(soundcat);
    }

    public void setCategory(SoundCategory soundcat)
    {
        panels = new imagePanel[soundcat.numSounds];
        labels = new JLabel[soundcat.numSounds];

        if (RhythmWheel.lowRes)
        {
            setLayout(new GridLayout(2, soundcat.numSounds, 2, -10));
        }
        else
        {
            setLayout(new GridLayout(2, soundcat.numSounds, 3, -5));
        }
        for (int i = 0; i < soundcat.numSounds; i++)
        {
            panels[i] = new imagePanel(soundcat.sounds[i]);
            addPanel(panels[i]);
        }
        for (int i = 0; i < soundcat.numSounds; i++)
        {
            String soundname = soundcat.names[i];
            int firstspace = soundname.indexOf(" ");
            if (firstspace == -1 || RhythmWheel.lowRes)
            {
                addLabel(soundname, i);
            }
            else
            {
                addLabel2(soundname.substring(0, firstspace),
                          soundname.substring(firstspace + 1,
                                              soundname.length()));
            }
        }


        this.revalidate();

        repaint();
    }

    private void addLabel(String s, int i)
    {
        labels[i] = new JLabel(s, JLabel.CENTER);
        labels[i].setBackground(BACKGROUND_COLOR);
        labels[i].setForeground(FOREGROUND_COLOR);
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            Font current = labels[i].getFont();
            labels[i].setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
        }

        add(labels[i]);//, NUM_SOUNDS + i);
    }

    private void addLabel2(String s1, String s2)
    {
        //JPanel p = new JPanel(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(2, 1, 0, -3));
        p.setBackground(BACKGROUND_COLOR);
        JLabel lbl = new JLabel(s1, JLabel.CENTER);
        lbl.setBackground(BACKGROUND_COLOR);
        lbl.setForeground(FOREGROUND_COLOR);
        labels2.addElement(lbl);
        JLabel lbl2 = new JLabel(s2, JLabel.CENTER);
        lbl2.setBackground(BACKGROUND_COLOR);
        lbl2.setForeground(FOREGROUND_COLOR);
        labels2.addElement(lbl2);
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            Font current = lbl.getFont();
            lbl.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
            lbl2.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
        }

        // p.add(lbl, BorderLayout.CENTER);
        // p.add(lbl2, BorderLayout.SOUTH);
        p.add(lbl);
        p.add(lbl2);
        panels2.add(p);
        add(p);
    }

    private void addPanel(imagePanel p)
    {
        imagePanels.addElement(p);
        p.addMouseListener(this);
        add(p);
    }

    public void changeColors(Color b, Color f)
    {
        RhythmWheel.changeComponent(this, b, f);
        for (int i = 0; i < panels.length; i++)
        {
            RhythmWheel.changeComponent(panels[i], b, f);
            panels[i].setBorder(new LineBorder(b));
            if (labels[i] != null)
            {
                RhythmWheel.changeComponent(labels[i], b, f);
            }
        }
        for (int i = 0; i < labels2.size(); i++)
        {
            JLabel l = (JLabel) labels2.elementAt(i);
            RhythmWheel.changeComponent(l, b, f);
        }
        for (int i = 0; i < panels2.size(); i++)
        {
            JPanel p = (JPanel) panels2.elementAt(i);
            RhythmWheel.changeComponent(p, b, f);
        }
        BACKGROUND_COLOR = b;
        FOREGROUND_COLOR = f;
    }

    // Mouse Events
    public void mouseEntered(MouseEvent evt)
    {
        if (evt.getSource() instanceof imagePanel)
        {
            imagePanel src = (imagePanel) evt.getSource();
            src.hilite();
            //isActive = true;
            rhythmWheel.myGlassPane.setVisible(true);
            rhythmWheel.myGlassPane.setSelectedSound((imagePanel) evt.getSource());
        }
    }

    public void mousePressed(MouseEvent evt)
    {
        rhythmWheel.myGlassPane.setVisible(true);
    }

    public void mouseExited(MouseEvent evt)
    {
    }

    public void mouseMoved(MouseEvent evt)
    {
    }

    public void mouseClicked(MouseEvent evt)
    {
    }

    public void mouseReleased(MouseEvent evt)
    {
    }
}

class imagePanel
        extends JPanel
{

    final static BasicStroke mediumStroke = new BasicStroke(2.0f);
    private int borderSize = 3;
    int count = 1;
    Sound sound;

    public imagePanel(Sound s)
    {
        sound = s;
        setForeground(Color.cyan);
        setBackground(RhythmWheel.BACKGROUND_COLOR);
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
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
                g.drawRect((getWidth() - Sound.getWidth()) / 2, 0,
                           (int) (Sound.getWidth() * Sound.scaleFactor),
                           (int) (sound.getHeight() * sound.scaleFactor) - 2);
            }
            // g.drawRect(0, 0, Sound.getWidth(), sound.getHeight());
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
                g.drawRect((getWidth() - Sound.getWidth()) / 2, 0,
                           (int) (Sound.getWidth() * Sound.scaleFactor),
                           (int) (sound.getHeight() * sound.scaleFactor) - 2);
            }
            g.setColor(oldColor);
        }
        else
        {
            setBorder(new LineBorder(RhythmWheel.BACKGROUND_COLOR, borderSize));
        }

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.cyan);
        g.translate((getWidth() - sound.getWidth()) / 2, 0);  // to center the sound
        sound.paint(g);
        g.translate(-(getWidth() - sound.getWidth()) / 2, 0);
    }
}
