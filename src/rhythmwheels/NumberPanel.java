package rhythmwheels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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
    private List<JLabel> labels;

    public NumberPanel(Wheel w)
    {
        wheel = w;
        setLayout(new GridLayout(2, NUM_LABELS / 2));
        labels = new ArrayList<JLabel>();

        if (RhythmWheel.isLowRes())
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
        if (RhythmWheel.isLowRes())
        {
            Font currentFont = lbl.getFont();
            lbl.setFont(new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize() - 2));
        }
        lbl.setBorder(new LineBorder(BACKGROUND_COLOR, borderSize));
        lbl.addMouseListener(this);
        labels.add(lbl);
        add(lbl);
    }

    public void changeColor(Color b, Color f)
    {
        RhythmWheel.changeComponent(this, b, f);
        for (int i = 0; i < labels.size(); i++)
        {
            RhythmWheel.changeComponent(labels.get(i), b, f);
            if (i != selected)
            {
                JLabel l = labels.get(i);
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
            JLabel lbl = labels.get(i);
            if (clicked == lbl)
            {
                select(i);
                return;
            }
        }
    }

    /**
     * Note this uses indexing starting at 1.
     * @param i The number of sounds the wheel should have.
     */
    public void select(int i)
    {
        // Delelect the old one
        if (i != selected)
        {
            JLabel old = labels.get(selected);
            old.setBorder(new LineBorder(BACKGROUND_COLOR, borderSize));
        }
        selected = i;
        JLabel lbl = labels.get(i);
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
        if (lbl != labels.get(selected))
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
