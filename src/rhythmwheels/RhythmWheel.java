package RhythmWheels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RhythmWheel extends JFrame implements ActionListener
{

    public static boolean isApplet = false;
    public WheelPanel wheelPanels[];
    /**
     * This is the combo box that determines which set of sounds is played.
     */
    private JComboBox categoryBox;
    public static int NUM_WHEELS = 3;
    public static int MAX_WHEELS = 3;
    public static Color BACKGROUND_COLOR = Color.darkGray;
    public static Color FOREGROUND_COLOR = Color.white;
    public static URL docBase;
    public JLabel soundCatLabel;
    public JPanel top, mid, bottom, wheelLayout, tempBottom;
    private JLabel numWheelsLabel = new JLabel("   Number of Wheels: ");
    private JComboBox numWheelsBox = new JComboBox(new Object[]
            {
                "1", "2", "3"
            });
    public MyGlassPane myGlassPane;
    public static boolean lowRes = false; // Is the screen at 600 x 800 or less?
    public ControlsPanel controlPanel;
    private SoundPanel soundPanel;

    public RhythmWheel()
    {
        this(null);
    }

    public RhythmWheel(URL docbase)
    {
        super("Rhythm Wheels");
        if(docbase != null)
        {
            docBase = docbase;
            isApplet = true;
        }
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            lowRes = true;
            Sound.scaleFactor = 0.8;
            setSize(770, 550);
        }
        else
        {
            lowRes = false;
            Sound.scaleFactor = 1.0;
            setSize(800, 670);
        }

        controlPanel = new ControlsPanel(this);

        // Add the soundPanel, title, and choice box
        Container cp = getContentPane();
        cp.setBackground(BACKGROUND_COLOR);
        cp.setLayout(new BorderLayout());
        top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setBackground(BACKGROUND_COLOR);

        soundCatLabel = new JLabel("Sound Category: ");
        soundCatLabel.setForeground(FOREGROUND_COLOR);

        categoryBox = new JComboBox(new Object[]
                {
                    new LatinoCaribbean(), new HipHop(), new Rock()
                });
        categoryBox.setBackground(BACKGROUND_COLOR);
        categoryBox.setForeground(FOREGROUND_COLOR);
        categoryBox.addActionListener(this);
        if (lowRes)
        {
            Font current = soundCatLabel.getFont();
            Font smaller = new Font(current.getName(), Font.PLAIN, current.getSize() - 2);

            soundCatLabel.setFont(smaller);
            categoryBox.setFont(smaller);
            numWheelsLabel.setFont(smaller);
            numWheelsBox.setFont(smaller);
        }

        top.add(numWheelsLabel);
        numWheelsBox.addActionListener(this);
        top.add(numWheelsBox);
        top.add(soundCatLabel);
        top.add(categoryBox);

        soundPanel = new SoundPanel(this, (SoundCategory) categoryBox.getSelectedItem());

        cp.add(top, BorderLayout.BEFORE_FIRST_LINE);
        mid = new JPanel(new FlowLayout());
        mid.setBackground(BACKGROUND_COLOR);
        mid.add(soundPanel);
        mid.add(controlPanel);
        cp.add(mid, BorderLayout.CENTER);

        changeComponent(numWheelsBox, BACKGROUND_COLOR, FOREGROUND_COLOR);
        changeComponent(numWheelsLabel, BACKGROUND_COLOR, FOREGROUND_COLOR);

        // Wheel panels
        bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BACKGROUND_COLOR);

        wheelPanels = new WheelPanel[MAX_WHEELS];
        for (int i = 0; i < MAX_WHEELS; i++)
        {
            WheelPanel w = new WheelPanel();
            //w.setBGColor(BACKGROUND_COLOR);
            w.wheel.setNumSounds(1);
            wheelPanels[i] = w;
        }


        // Set the number of wheels to 1.  This changes NUM_WHEELS to 1.
        // Don't want to start with NUM_WHEELS = 1 because other classes create
        // arrays of size NUM_WHEELS
        setNumWheels(1);

        tempBottom = new JPanel(new FlowLayout());
        tempBottom.setBackground(BACKGROUND_COLOR);
        tempBottom.add(controlPanel);
        bottom.add(tempBottom, BorderLayout.SOUTH);
        cp.add(bottom, BorderLayout.SOUTH);

        // Add the glass pane for dragging
        myGlassPane = new MyGlassPane(cp, soundPanel);
        setGlassPane(myGlassPane);
        myGlassPane.setVisible(false);
    }

    public void start()
    {
        top.remove(categoryBox);
        categoryBox = new JComboBox(new Object[]
                {
                    new LatinoCaribbean(), new HipHop(),
                    new Rock()
                });
        categoryBox.setBackground(BACKGROUND_COLOR);
        categoryBox.setForeground(FOREGROUND_COLOR);
        categoryBox.addActionListener(this);
        top.add(categoryBox);
        if (lowRes)
        {
            Font current = categoryBox.getFont();
            Font smaller = new Font(current.getName(), Font.PLAIN, current.getSize() - 2);
            categoryBox.setFont(smaller);
        }
    }

    public WheelPanel[] getWheelPanels()
    {
        return wheelPanels;
    }

    public void setNumWheels(int i)
    {
        if (wheelLayout != null)
        {
            bottom.remove(wheelLayout);
        }

        wheelLayout = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wheelLayout.setBackground(BACKGROUND_COLOR);

        // Layout for 3 Wheels
        for (int w = 0; w < i; w++)
        {
            wheelLayout.add(wheelPanels[w]);
        }

        bottom.add(wheelLayout, BorderLayout.CENTER);
        bottom.revalidate();
        bottom.repaint();
        NUM_WHEELS = i;
        repaint();
    }

    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == categoryBox)
        {
            mid.remove(soundPanel);
            soundPanel = new SoundPanel(this, (SoundCategory) categoryBox.getSelectedItem());
            mid.add(soundPanel);
            mid.revalidate();
            repaint();
        }
        else if (evt.getSource() == numWheelsBox)
        {
            setNumWheels(Integer.parseInt((String) (numWheelsBox.getSelectedItem())));
        }
    }

    // Sets the background and foreground of a component
    public static void changeComponent(Component c, Color b, Color f)
    {
        c.setBackground(b);
        c.setForeground(f);
    }
}
