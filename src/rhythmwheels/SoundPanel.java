package rhythmwheels;

import rhythmwheels.soundcategories.SoundCategory;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SoundPanel extends JPanel implements MouseListener
{

    public static final Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public static final Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    List<ImagePanel> imagePanels = new ArrayList<ImagePanel>();
    RhythmWheel rhythmWheel;
    List<JLabel> labels2 = new ArrayList<JLabel>(); // For sounds that require 2 labels
    List<JPanel> panels2 = new ArrayList<JPanel>(); // " "
    ImagePanel[] panels;
    /*
     * TODO: Replace this with a List.
     */
    JLabel[] labels;

    // Have to give it the applet so it can use getContextBase() to find the audio files
    public SoundPanel(RhythmWheel r, SoundCategory soundcat)
    {
        rhythmWheel = r;
        setBackground(RhythmWheel.BACKGROUND_COLOR);
        setCategory(soundcat);
    }

    /**
     * 
     * @param soundcat 
     */
    private void setCategory(SoundCategory soundcat)
    {
        panels = new ImagePanel[soundcat.getNumSounds()];
        labels = new JLabel[soundcat.getNumSounds()];

        if (RhythmWheel.lowRes)
        {
            setLayout(new GridLayout(2, soundcat.getNumSounds(), 2, -10));
        }
        else
        {
            setLayout(new GridLayout(2, soundcat.getNumSounds(), 3, -5));
        }
        for (int i = 0; i < soundcat.getNumSounds(); i++)
        {
            panels[i] = new ImagePanel(soundcat.getSounds()[i]);
            addPanel(panels[i]);
        }
        for (int i = 0; i < soundcat.getNumSounds(); i++)
        {
            String soundname = soundcat.getNames()[i];
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

    /**
     * Creates a new JLabel with string s adds it to this panel. The label created is also placed
     * in the array labels at index i.
     * @param s The string to create the label with
     * @param i The index in the array to assign the created label to.
     */
    private void addLabel(String s, int i)
    {
        labels[i] = new JLabel(s, JLabel.CENTER);
        RhythmWheel.changeComponent(labels[i], BACKGROUND_COLOR, FOREGROUND_COLOR);
        if (rhythmWheel.isLowRes())
        {
            Font current = labels[i].getFont();
            labels[i].setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
        }

        add(labels[i]);
    }

    /**
     * Creates two labels and adds them to a panel as if they were wrapped at the end of the first
     * word. The panel created is then added to this panel.
     * @param s1 The string to use for creating the upper label.
     * @param s2 The string to use for creating the lower label.
     */
    private void addLabel2(String s1, String s2)
    {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, -3));
        p.setBackground(BACKGROUND_COLOR);
        JLabel lbl = new JLabel(s1, JLabel.CENTER);
        RhythmWheel.changeComponent(lbl, BACKGROUND_COLOR, FOREGROUND_COLOR);
        labels2.add(lbl);

        JLabel lbl2 = new JLabel(s2, JLabel.CENTER);
        RhythmWheel.changeComponent(lbl2, BACKGROUND_COLOR, FOREGROUND_COLOR);
        labels2.add(lbl2);
        if (rhythmWheel.isLowRes())
        {
            Font current = lbl.getFont();
            lbl.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
            lbl2.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
        }

        p.add(lbl);
        p.add(lbl2);
        panels2.add(p);
        add(p);
    }

    private void addPanel(ImagePanel p)
    {
        imagePanels.add(p);
        p.addMouseListener(this);
        add(p);
    }

    // Mouse Events
    public void mouseEntered(MouseEvent evt)
    {
        if (evt.getSource() instanceof ImagePanel)
        {
            ImagePanel src = (ImagePanel) evt.getSource();
            src.hilite();
            //isActive = true;
            rhythmWheel.myGlassPane.setVisible(true);
            rhythmWheel.myGlassPane.setSelectedSound((ImagePanel) evt.getSource());
        }
    }

    public void mousePressed(MouseEvent evt)
    {
        rhythmWheel.myGlassPane.setVisible(true);
    }

    public void mouseExited(MouseEvent evt)
    {
    }

    public void mouseClicked(MouseEvent evt)
    {
    }

    public void mouseReleased(MouseEvent evt)
    {
    }
}
