package RhythmWheels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

public class ControlsPanel extends JPanel implements ActionListener
{
    public static final Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public static final Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    private JButton playButton = new JButton("Play");
    private JButton stopButton = new JButton("Stop");
    private JPanel bottom = new JPanel();
    private JPanel sliderPanel = new JPanel();
    private JPanel top, lPanel, rPanel;
    public JSlider slider = new JSlider(-4, 1, 0);
    private JLabel slowLabel = new JLabel("Slow");
    private JLabel fastLabel = new JLabel("Fast");
    private ClipPlayer clipPlayer;
    static public RhythmWheel rhythmWheel;
    private ConcatThread concatThread;
    public JDialog dlg;
    private Painter painter;
    public int playIterations = 1;
    protected Timer paintTimer;
    private Timer playTimer;
    byte[] mixedBytes;
    public AudioFormat audioFormat;
    static final int FPS = 15; // frames per second

    public ControlsPanel(RhythmWheel r)
    {
        rhythmWheel = r;
        concatThread = new ConcatThread(this);
        dlg = new JDialog(new JFrame(), "Processing Sounds", true);
        dlg.getContentPane().add(new JLabel(
                "Processing Sounds.  Please Wait..."));
        dlg.setSize(220, 60);
        dlg.setResizable(false);

        setLayout(new BorderLayout());

        // Add Slider
        sliderPanel.setBackground(BACKGROUND_COLOR);
        sliderPanel.setLayout(new BorderLayout());
        slider.setBackground(BACKGROUND_COLOR);
        slider.setPaintLabels(false);

        slider.setBackground(BACKGROUND_COLOR);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slowLabel.setForeground(FOREGROUND_COLOR);
        fastLabel.setForeground(FOREGROUND_COLOR);

        // Set up slider
        slider.setOrientation(JSlider.HORIZONTAL);
        sliderPanel.add(slowLabel, BorderLayout.EAST);
        sliderPanel.add(fastLabel, BorderLayout.WEST);
        sliderPanel.add(slider, BorderLayout.CENTER);
        top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.add(sliderPanel);
        top.setBackground(BACKGROUND_COLOR);
        add(top, BorderLayout.NORTH);

        bottom.setLayout(new GridLayout(1, 2));
        lPanel = new JPanel(); //(new BorderLayout());
        playButton.addActionListener(this);
        lPanel.setBackground(BACKGROUND_COLOR);
        rPanel = new JPanel(); //(new BorderLayout());
        rPanel.setBackground(BACKGROUND_COLOR);
        rPanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        stopButton.addActionListener(this);

        if (RhythmWheel.lowRes)
        {
            Font current = fastLabel.getFont();
            Font smaller = new Font(current.getName(), Font.PLAIN,
                                    current.getSize() - 2);
            playButton.setFont(smaller);
            playButton.setSize(playButton.getWidth() - 3,
                               playButton.getHeight() - 7);
            stopButton.setFont(smaller);
            stopButton.setSize(stopButton.getWidth() - 3,
                               stopButton.getHeight() - 7);
            stopButton.revalidate();
            playButton.revalidate();
            fastLabel.setFont(smaller);
            slowLabel.setFont(smaller);
        }
        lPanel.add(playButton, BorderLayout.SOUTH);
        rPanel.add(stopButton, BorderLayout.SOUTH);
        bottom.add(lPanel);
        bottom.add(rPanel);
        add(bottom, BorderLayout.SOUTH);
    }

    public void changeColors(Color b, Color f)
    {
        RhythmWheel.changeComponent(sliderPanel, b, f);
        RhythmWheel.changeComponent(slider, b, f);
        RhythmWheel.changeComponent(this, b, f);
        RhythmWheel.changeComponent(bottom, b, f);
        RhythmWheel.changeComponent(slowLabel, b, f);
        RhythmWheel.changeComponent(fastLabel, b, f);
        RhythmWheel.changeComponent(top, b, f);
        RhythmWheel.changeComponent(lPanel, b, f);
        RhythmWheel.changeComponent(rPanel, b, f);
    }

    public void actionPerformed(ActionEvent evt)
    {
        if (painter == null)
        {
            painter = new Painter(this);
            paintTimer = new Timer(1000 / FPS, painter);
        }

        // ************************ PLAY *****************************
        if (evt.getSource() == playButton)
        {

            stopButton.doClick();

            if (clipPlayer != null && clipPlayer.isAlive())
            {
                clipPlayer.stopPlaying();

                // Show dialog "Processing"
            }
            int cx = (rhythmWheel.getX() + rhythmWheel.getWidth()) / 2
                     - dlg.getWidth() / 2;
            int cy = (rhythmWheel.getY() + rhythmWheel.getHeight()) / 2
                     - dlg.getHeight() / 2;
            dlg.setLocation(cx, cy);
            concatThread = new ConcatThread(this);
            concatThread.start();
            dlg.setVisible(true);

            repaint();

            // If we get to this point the dlg is closed by the concat thread
            painter.reset();

            // Probably not the most efficient way to do this
            clipPlayer = new ClipPlayer(mixedBytes, audioFormat, paintTimer, playIterations);
            clipPlayer.start();
        }
        else if (evt.getSource() == stopButton)
        {
            paintTimer.stop();
            if (clipPlayer != null)
            {
                clipPlayer.stopPlaying();
            }
            for (int i = 0; i < rhythmWheel.NUM_WHEELS; i++)
            {
                rhythmWheel.getWheelPanels()[i].wheel.setRotationAngle(0);
                // We need to stop the wheel from continuing to rotate.
                paintTimer.stop();
            }
        }
    }
}