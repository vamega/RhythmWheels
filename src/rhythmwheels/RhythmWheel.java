package rhythmwheels;

import rhythmwheels.soundcategories.SoundCategory;
import rhythmwheels.soundcategories.HipHop;
import rhythmwheels.soundcategories.Rock;
import rhythmwheels.soundcategories.LatinoCaribbean;
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

/*
 * Varun Madiath comments
 * 
 * NOTE: Future me - remove this comment when all the code in this project is well documented and
 * the code is refactored to have good coding practices.
 * 
 * If someone else must pick up this code before the above NOTE is removed, I'm terribly sorry.
 * When I got this, the code was a mess, I've tried to clean it up since. Hopefully you'll be able 
 * to finish the job, and actually make this something thats easy to modify.
 */
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
    public static final Color BACKGROUND_COLOR = Color.darkGray;
    public static final Color FOREGROUND_COLOR = Color.white;
    //TODO: Evaluate if this is actually used anywhere.
    public static URL docBase;
    private JLabel soundCatLabel;
    private JPanel top, mid, bottom, wheelContainer, tempBottom;
    private JLabel numWheelsLabel = new JLabel("   Number of Wheels: ");
    private JComboBox numWheelsBox = new JComboBox(new Object[]
            {
                "1", "2", "3"
            });
    public MyGlassPane myGlassPane;

    // TODO: Change this to a non-static private boolean.
    // And perhaps have the setLowRes perform changes to the UI that are needed on low-res screens.
    /**
     * A boolean to represent whether the application is running on a screen which has a resolution
     * of 800 x 600 or less.
     */
    public static boolean lowRes = false;
    public ControlsPanel controlPanel;
    private SoundPanel soundPanel;

    static
    {
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 801)
        {
            lowRes = true;
        }
        else
        {
            lowRes = false;
        }
    }
    
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
        if (isLowRes())
        {
            Sound.scaleFactor = 0.8;
            setSize(770, 550);
        }
        else
        {
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


        //FIXME: Change this so that this call is uncessary.
        //       Rather start with NUM_WHEELS = 1, and MAX_WHEELS =3
        //       Then make everything use max wheels for the array.

        /* Set the number of wheels to 1.  This changes NUM_WHEELS to 1.
         * Don't want to start with NUM_WHEELS = 1 because other classes create
         * arrays of size NUM_WHEELS
         */
        setNumWheels(1);

        tempBottom = new JPanel(new FlowLayout());
        tempBottom.setBackground(BACKGROUND_COLOR);
        tempBottom.add(controlPanel);
        bottom.add(tempBottom, BorderLayout.SOUTH);
        cp.add(bottom, BorderLayout.SOUTH);

        // Add the glass pane for dragging
        myGlassPane = new MyGlassPane(cp, soundPanel);
        setGlassPane(myGlassPane);
        myGlassPane.setVisible(true);
    }

    public WheelPanel[] getWheelPanels()
    {
        return wheelPanels;
    }

    /**
     * Changes the number of wheels that are displayed in the user interface.
     * @param newNumWheels The new number of wheels to be displayed.
     */
    public void setNumWheels(int newNumWheels)
    {
        if (wheelContainer != null)
        {
            bottom.remove(wheelContainer);
        }

        wheelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wheelContainer.setBackground(BACKGROUND_COLOR);

        for (int w = 0; w < newNumWheels; w++)
        {
            if(w >= NUM_WHEELS)
            {
                wheelPanels[w].wheel.setNumSounds(1);
            }
            wheelContainer.add(wheelPanels[w]);
        }
        
        for (int i = newNumWheels; i < wheelPanels.length; i++)
        {
            wheelPanels[i].wheel.setNumSounds(0);
        }

        bottom.add(wheelContainer, BorderLayout.CENTER);
        bottom.revalidate();
        NUM_WHEELS = newNumWheels;
    }

    /**
     * Checks if the application is running on a low resolution screen. A low resolution screen
     * is one which has a resolution of 800 x 600 or less.
     * @return true, if the application is running on a low resolution screen, false otherwise.
     */
    public static boolean isLowRes()
    {
        return lowRes;
    }

     /**
     * @param aLowRes the lowRes to set
     */
    public void setLowRes(boolean aLowRes)
    {
        lowRes = aLowRes;
    }

    /**
     * Handles the events for the categoryBox and the numWheelsBox.
     * @param evt An event triggered by either categoryBox or numWheelsBox.
     */
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

    /**
     * Sets the background and foreground of a component.
     * @param c The component to modify.
     * @param b The new background color for the component.
     * @param f The new foreground color for the component.
     */
    public static void changeComponent(Component c, Color b, Color f)
    {
        c.setBackground(b);
        c.setForeground(f);
    }
    
    public void setDropDownValue(int index)
    {
        numWheelsBox.setSelectedIndex(index - 1);
    }
}
