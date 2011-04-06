package RhythmWheels;

//package rhythmwheel;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.event.*;

// Class WheelPanel handles the spinning of the wheel.  Contains a NumberPanel
public class WheelPanel extends JPanel
{

    public Wheel wheel = new Wheel();
    public Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public NumberPanel numPanel = new NumberPanel(wheel);
    public JTextField loopField = new JTextField("1", 3);
    public JLabel loopLabel = new JLabel("Loop:");
    public JPanel inputPanel = new JPanel();
    private JPanel top = new JPanel(new FlowLayout());

    public WheelPanel()
    {
        setLayout(new BorderLayout());
        top.add(numPanel);
        
        add(top, BorderLayout.NORTH);
        add(wheel, BorderLayout.CENTER);
        loopLabel.setForeground(Color.white);

        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            Font current = loopLabel.getFont();
            loopLabel.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
            loopField.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
        }

        inputPanel.add(loopLabel);
        inputPanel.add(loopField);
        add(inputPanel, BorderLayout.SOUTH);
        setBackground(BACKGROUND_COLOR);
        top.setBackground(BACKGROUND_COLOR);
        numPanel.setBackground(BACKGROUND_COLOR);
        wheel.setBackground(BACKGROUND_COLOR);
        loopLabel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBackground(BACKGROUND_COLOR);
    }

    public void changeColors(Color b, Color f)
    {
        RhythmWheel.changeComponent(this, b, f);
        RhythmWheel.changeComponent(top, b, f);
        RhythmWheel.changeComponent(numPanel, b, f);
        RhythmWheel.changeComponent(wheel, b, f);
        RhythmWheel.changeComponent(loopLabel, b, f);
        RhythmWheel.changeComponent(inputPanel, b, f);
        numPanel.changeColor(b, f);
    }

    public int getIterations()
    {
        int wheelIterations = 0;
        try
        {
            wheelIterations = Integer.parseInt(loopField.getText());
        }
        catch (NumberFormatException e)
        {
            loopField.setText("0");
        }
        return wheelIterations;
    }
}

class NumberPanel extends JPanel implements MouseListener
{

    private static final Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    private static final Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    private static final Color OUTLINE_COLOR = Color.cyan;
    private static final Color SELECTED_COLOR = Color.blue;
    private static final int NUM_LABELS = 16;
    private static int borderSize = 2;
    private Wheel wheel;
    private int selected = 0; // Index of the selected label
    private Vector labels;

    public NumberPanel(Wheel w)
    {
        wheel = w;
        setLayout(new GridLayout(2, NUM_LABELS / 2));
        labels = new Vector();

        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            borderSize = 1;
        }
        else
        {
            borderSize = 2;
        }
        for (int i = 1; i <= NUM_LABELS; i++)
        {
            addLabel(" " + i + " ");
        }

        // Select the first label
        select(0);
    }

    private void addLabel(String num)
    {
        JLabel lbl = new JLabel(num, JLabel.CENTER);
        lbl.setBackground(BACKGROUND_COLOR);
        lbl.setForeground(FOREGROUND_COLOR);
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            Font currentFont = lbl.getFont();
            lbl.setFont(new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize() - 2));
        }
        lbl.setBorder(new LineBorder(BACKGROUND_COLOR, borderSize));
        lbl.addMouseListener(this);
        labels.addElement(lbl);
        add(lbl);
    }

    public void changeColor(Color b, Color f)
    {
        RhythmWheel.changeComponent(this, b, f);
        for (int i = 0; i < labels.size(); i++)
        {
            RhythmWheel.changeComponent((JLabel) labels.elementAt(i), b, f);
            if (i != selected)
            {
                JLabel l = (JLabel) labels.elementAt(i);
                l.setBorder(new LineBorder(b));
            }
        }
    }

    public void mouseDragged(MouseEvent evt)
    {
    }

    public void mouseClicked(MouseEvent evt)
    {
    }

    public void mousePressed(MouseEvent evt)
    {
        JLabel clicked = (JLabel) evt.getSource();
        for (int i = 0; i < NUM_LABELS; i++)
        {
            JLabel lbl = (JLabel) labels.elementAt(i);
            if (clicked == lbl)
            {
                select(i);
                return;
            }
        }
    }

    public void select(int i)
    {
        // Delelect the old one
        if (i != selected)
        {
            JLabel old = (JLabel) labels.elementAt(selected);
            old.setBorder(new LineBorder(BACKGROUND_COLOR, borderSize));
        }
        selected = i;
        JLabel lbl = (JLabel) labels.elementAt(i);
        lbl.setBorder(new LineBorder(SELECTED_COLOR, borderSize));
        try
        {
            if (Character.isDigit(lbl.getText().charAt(2)))
            {
                wheel.setNumSounds(Integer.parseInt(lbl.getText().substring(1, 3)));
            }
            else
            {
                wheel.setNumSounds(Integer.parseInt(lbl.getText().substring(1, 2)));
            }
            wheel.repaint();
        }
        catch (NumberFormatException e)
        {
            System.err.println("Error parsing number panel label: " + lbl.getText());
        }
    }

    public void mouseReleased(MouseEvent evt)
    {
    }

    public void mouseEntered(MouseEvent evt)
    {
        JLabel lbl = (JLabel) evt.getSource();
        LineBorder b = new LineBorder(OUTLINE_COLOR, borderSize, false);
        lbl.setBorder(b);
        lbl.setBackground(Color.black);
    }

    public void mouseExited(MouseEvent evt)
    {
        JLabel lbl = (JLabel) evt.getSource();

        LineBorder b;
        if (lbl != (JLabel) labels.elementAt(selected))
        {
            b = new LineBorder(BACKGROUND_COLOR, borderSize, false);
        }
        else
        {
            b = new LineBorder(SELECTED_COLOR, borderSize, false);
        }
        lbl.setBorder(b);
        lbl.setBackground(BACKGROUND_COLOR);
    }
}
