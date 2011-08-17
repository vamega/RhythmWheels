package rhythmwheels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdom.JDOMException;

public class ControlsPanel extends JPanel implements ActionListener
{

    public static final int MIN_SPEED = -8;
    public static final int MAX_SPEED = 4;
    public static final int DEFAULT_SPEED = 0;
    public static final Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public static final Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    private JButton playButton = new JButton("Play");
    private JButton stopButton = new JButton("Stop");
    private JButton loadButton = new JButton("Load Rhythm");
    private JButton saveButton = new JButton("Save Rhythm");
    private JFileChooser fileChooser = new JFileChooser();
    private FileNameExtensionFilter rhythmFilter = new FileNameExtensionFilter("RhythmWheel Rhythms",
                                                                               "xml");
    private JPanel bottom = new JPanel();
    private JPanel sliderPanel = new JPanel();
    private JPanel top, lPanel, rPanel;
    public JSlider slider = new JSlider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
    private JLabel slowLabel = new JLabel("Slow");
    private JLabel fastLabel = new JLabel("Fast");
    private ClipPlayer clipPlayer;
    static public RhythmWheel rhythmWheel;
    private ConcatThread concatThread;
    public JDialog dlg;
    private Painter painter;
    protected Timer paintTimer;
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

        fileChooser.setFileFilter(rhythmFilter);
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());

        saveButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                int returnVal = fileChooser.showSaveDialog(rhythmWheel);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    String selectedFileName = selectedFile.getPath();
                    
                    if(!fileChooser.getFileFilter().accept(selectedFile))
                    {
                        selectedFile = new File(selectedFileName + "." +rhythmFilter.getExtensions()[0]);
                    }

                    Wheel[] wheels = new Wheel[rhythmWheel.getWheelPanels().length];

                    for (int i = 0; i < wheels.length; i++)
                    {
                        wheels[i] = rhythmWheel.getWheelPanels()[i].wheel;
                    }
                    try
                    {
                        RhythmWriter.saveState(wheels, selectedFile, rhythmWheel);
                    }
                    catch (FileNotFoundException ex)
                    {
                        /*
                         * This should never happen, as we should be creating the file if it
                         * doesn't exist.
                         */
                        JOptionPane.showMessageDialog(rhythmWheel, "The file does not exist.",
                                                      "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(rhythmWheel, "Unable to write to the file.",
                                                      "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (returnVal == JFileChooser.CANCEL_OPTION)
                {
                    return;
                }
                /*
                 * If an error occurs, or the file chooser is dismissed, see
                 * JFileChooser.ERROR_OPTION
                 */
                else
                {
                    /*
                     * TODO: Need to check if there is a way to distinguis between the dialog being
                     * dismissed and an error occuring. If the dialog is dismissed we should not be
                     * doing anything, but if the dialog generates an error, we should probably
                     * display an error message.
                     *
                     * For now simply assume that it's caused by the dismissal of the dialog.
                     */
                    return;
                }
            }
        });
        loadButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                int returnVal = fileChooser.showOpenDialog(rhythmWheel);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    Wheel[] wheels = new Wheel[rhythmWheel.getWheelPanels().length];
                    for (int i = 0; i < wheels.length; i++)
                    {
                        wheels[i] = rhythmWheel.getWheelPanels()[i].wheel;
                    }
                    try
                    {
                        RhythmWriter.loadState(wheels, selectedFile, rhythmWheel);
                    }
                    catch (JDOMException ex)
                    {
                        //TODO: Find a better way of wording this error message
                        JOptionPane.showMessageDialog(rhythmWheel,
                                                      "The file is not formatted correctly, or "
                                                      + "does not represent a Rhythm",
                                                      "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(rhythmWheel, "Unable to read the file.",
                                                      "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(rhythmWheel,
                                                      "This file was created by a version of "
                                                      + "RhythmWheels that this version "
                                                      + "cannot read.",
                                                      "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (returnVal == JFileChooser.CANCEL_OPTION)
                {
                    return;
                }
                /*
                 * If an error occurs, or the file chooser is dismissed, see
                 * JFileChooser.ERROR_OPTION
                 */
                else
                {
                    /*
                     * TODO: Need to check if there is a way to distinguis between the dialog being
                     * dismissed and an error occuring. If the dialog is dismissed we should not be
                     * doing anything, but if the dialog generates an error, we should probably
                     * display an error message.
                     *
                     * For now simply assume that it's caused by the dismissal of the dialog.
                     */
                    return;
                }
            }
        });

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
        lPanel.add(loadButton, BorderLayout.SOUTH);
        lPanel.add(playButton, BorderLayout.SOUTH);
        rPanel.add(stopButton, BorderLayout.SOUTH);
        rPanel.add(saveButton, BorderLayout.SOUTH);
        bottom.add(lPanel);
        bottom.add(rPanel);
        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Sets the background and foreground colors of all components within this class.
     * @param b The background color the components should have
     * @param f The foreground color the components should have
     */
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
            //This shouldnt be necessary, but for some reasons Web Start doesn't seem to repect
            //the call to paintTimer.stop() and the timer keeps firing events. If that is ever fixed.
            // This and the associated call in the stop button handler should be removed.
            paintTimer.addActionListener(painter);

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
            clipPlayer = new ClipPlayer(mixedBytes, audioFormat, paintTimer, 1);
            clipPlayer.start();
        }
        else if (evt.getSource() == stopButton)
        {   
            if (clipPlayer != null)
            {
                clipPlayer.stopPlaying();
            }
            for (int i = 0; i < rhythmWheel.NUM_WHEELS; i++)
            {
                rhythmWheel.getWheelPanels()[i].wheel.setRotationAngle(0);
            }
            //Stop the wheel from rotating
            paintTimer.stop();
            
            //This shouldnt be necessary, but for some reasons Web Start doesn't seem to repect
            //the call to paintTimer.stop() and the timer keeps firing events.
            paintTimer.removeActionListener(painter);
        }
    }

    /**
     * Gets the speed of the Rhythm, as shown in the slider.
     * @return The speed of the Rhythm.
     */
    public int getSpeed()
    {
        return slider.getValue();
    }

    /**
     * Sets the speed of the Rhythm, as shown in the slider. If the new speed is outside the 
     * acceptable range of [MIN_SPEED, MAX_SPEED] the speed is set to DEFAULT_SPEED.
     * @param newSpeed The new speed of the Rhythm.
     */
    public void setSpeed(int newSpeed)
    {
        if (newSpeed >= MAX_SPEED && newSpeed <= MIN_SPEED)
        {
            slider.setValue(newSpeed);
        }
        else
        {
            slider.setValue(DEFAULT_SPEED);
        }
    }
}
