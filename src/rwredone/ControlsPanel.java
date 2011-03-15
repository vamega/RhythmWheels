package rwredone;

//package rhythmwheel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Calendar;
import java.io.*;
import javax.swing.Timer;

import javax.sound.sampled.*;
import sun.applet.AppletAudioClip;

public class ControlsPanel extends JPanel implements ActionListener
{

    public static final Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public static final Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    private JButton playButton = new JButton("Play");
    private JButton stopButton = new JButton("Stop");
    private JPanel bottom = new JPanel();
    private JPanel sliderPanel = new JPanel();
    private JPanel top, lPanel, rPanel;
    public JSlider slider = new JSlider(0, 7, 3);
    private JLabel slowLabel = new JLabel("Slow");
    private JLabel fastLabel = new JLabel("Fast");
    private ClipPlayer clipPlayer;
    static public RhythmWheel rhythmWheel;
    private ConcatThread concatThread;
    public JDialog dlg;
    private Painter painter;
    public int playIterations = 1;
    protected Timer paintTimer;
    //public AudioClip mixedClip;
    byte[] mixedBytes;

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
            dlg.show();

            repaint();

            // If we get to this point the dlg is closed by the concat thread
            painter.reset();

            // Play the mixed clip
               /*             if (mixedClip != null) {
            mixedClip.play();
            }
            try {
            Thread.sleep(100);
            }
            catch (Exception e) {}*/

            // NOTE: THIS WORKS, AND WOULD BE THE BEST WAY TO DO IT,
            // BUT YOU GET A GLITCH AT THE BEGINNING OF PLAYBACK WHICH I
            // HAVEN'T BEEN ABLE TO GET RID OF YET
               /*
            byte[] bytescopy = (byte[]) mixedBytes.clone();
            AudioInputStream ais = null;
            AudioFormat audioFormat = null;
            try {
            //audioFormat = AudioSystem.getAudioFileFormat(mixed).getFormat();
            ais = AudioSystem.getAudioInputStream(
            new ByteArrayInputStream(mixedBytes));
            audioFormat = ais.getFormat();
            }
            catch (Exception e) {
            e.printStackTrace();
            }
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat); // format is an AudioFormat object
            if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Audio Line not supported!\n");
            }
            Clip clip = null;
            // Obtain and open the line.
            try {
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            }
            catch (Exception ex) {
            //ex.printStackTrace();
            System.err.println(ex.toString());
            }
            clip.loop(5);
            AudioInputStream ais2 = null;
            AudioFormat audioFormat2 = null;
            try {
            Open o = new Open();
            //audioFormat = AudioSystem.getAudioFileFormat(mixed).getFormat();
            ais2 = AudioSystem.getAudioInputStream(new File(o.
            strCurrentFileName));
            //    new ByteArrayInputStream(bytescopy));
            audioFormat2 = ais.getFormat();
            }
            catch (Exception e) {
            e.printStackTrace();
            }
            DataLine.Info info2 = new DataLine.Info(Clip.class, audioFormat2); // format is an AudioFormat object
            if (!AudioSystem.isLineSupported(info2)) {
            System.err.println("Audio Line not supported!\n");
            }
            Clip clip2 = null;
            // Obtain and open the line.
            try {
            clip2 = (Clip) AudioSystem.getLine(info2);
            clip2.open(ais2);
            }
            catch (Exception ex) {
            ex.printStackTrace();
            //System.err.println(ex.toString());
            }
            try {
            Thread.sleep(100);
            }
            catch (Exception e) {}
            clip2.loop(5);*/

            // Probably not the most efficient way to do this
            clipPlayer = new ClipPlayer(mixedBytes, paintTimer,
                                        playIterations);
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
            }
        }
    }
    static final int FPS = 15; // frames per second
}


// This thread calls the createSoundFile function, then hides the dialog box
class Painter implements ActionListener
{
    ControlsPanel cp;
    Wheel wheels[];
    long[] soundLength;
    int[] wheelIterations;
    GregorianCalendar calendar;
    long lastTimeFromStart = -1;
    long startTime = -1;
    double previousRotationAngle = 0;

    public Painter(ControlsPanel c)
    {
        cp = c;
        WheelPanel panels[];
        panels = cp.rhythmWheel.wheelPanels;
        calendar = new GregorianCalendar();
        wheels = new Wheel[panels.length];
        soundLength = new long[panels.length];
        wheelIterations = new int[panels.length];

        for (int i = 0; i < panels.length; i++)
        {
            wheels[i] = panels[i].wheel;
        }
    }

    public void actionPerformed(ActionEvent evt)
    {
        calendar.setTime(new Date());
        long currentTime = 1000 * calendar.get(Calendar.SECOND)
                           + 60000 * calendar.get(Calendar.MINUTE)
                           + calendar.get(Calendar.MILLISECOND);
        if (lastTimeFromStart == -1)
        {
            lastTimeFromStart = 0;
            startTime = currentTime;
        }
        long timeFromStart;
        if (startTime <= currentTime)
        {
            timeFromStart = currentTime - startTime;
        }
        else
        {
            timeFromStart = 3600000 - startTime + currentTime;
        }
        lastTimeFromStart = timeFromStart;

        double rotationDifference = 0;
        double rotationAngle = 0;
        boolean someRunning = false;
        for (int i = 0; i < RhythmWheel.NUM_WHEELS; i++)
        {
            if (timeFromStart < soundLength[i] * wheelIterations[i])
            {
                someRunning = true;
                rotationAngle = (2.0 * Math.PI
                                        * (double) timeFromStart / (double) soundLength[i]);

                rotationDifference = (rotationAngle - previousRotationAngle) % (2.0 * Math.PI /wheels[i].getSounds().size());
                if (rotationDifference >= 1)
                {
                    wheels[i].setSoundsPlayedCounter(wheels[i].getSoundsPlayedCounter() + (int) rotationDifference);
                }
                wheels[i].setRotationAngle(rotationAngle);
                wheels[i].repaint();

            }
            else
            {
                wheels[i].setRotationAngle(0.0);
            }
        }

        if(rotationDifference >=1)
        {
            previousRotationAngle = rotationAngle;
        }
        if (!someRunning)
        {
            cp.paintTimer.stop();
        }
    }

    public void reset()
    {
        lastTimeFromStart = -1;
        previousRotationAngle = 0;
        startTime = -1;
        int numsounds;
        for (int i = 0; i < RhythmWheel.NUM_WHEELS; i++)
        {
            wheelIterations[i] = 0;
            try
            {
                wheelIterations[i] = Integer.parseInt(cp.rhythmWheel.wheelPanels[i].loopField.getText());
            }
            catch (NumberFormatException e)
            {
            }
            if (wheelIterations[i] < 0)
            {
                wheelIterations[i] = 0;
            }
            numsounds = wheels[i].getSounds().size();
            soundLength[i] = numsounds
                             * (Sound.SOUND_LENGTH
                                + Sound.DELAY_LENGTH * cp.slider.getValue());
            wheels[i].setSoundsPlayedCounter(0);
        }
    }
} // Class ControlsPanel

// This thread calls the createSoundFile function, then hides the dialog box
class ConcatThread
        extends Thread
{

    static ControlsPanel cp;
    static RhythmWheel rhythmWheel;
    static Vector inputStreams;
    //static Vector oldSoundFiles;
    byte[] oldbytes;

    public ConcatThread(ControlsPanel c)
    {
        cp = c;
        rhythmWheel = cp.rhythmWheel;
    }

    public void run()
    {
        inputStreams = createSoundFile();
        //System.err.println("Old soundFiles = " + oldSoundFiles);
        //System.err.println("New soundFiles = " + soundFiles);

        // if (oldSoundFiles == null || !oldSoundFiles.equals(soundFiles)) {
        //     System.err.println("Mixing");

        if (inputStreams.size() > 1)
        {
            cp.mixedBytes = concatenator.Mix(inputStreams);
        }
        else
        {
            InputStream is = (InputStream) inputStreams.elementAt(0);
            try
            {
                cp.mixedBytes = new byte[1000000];
                is.read(cp.mixedBytes);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //           oldbytes = (byte [])cp.mixedBytes.clone();
        //}
          /*else {
        System.err.println("Sounds are the same");
        cp.mixedBytes = oldbytes;
        //System.err.println("OldAfterConcat = soundfiles " +
        //                   oldAfterConcat);
        }
        oldSoundFiles = (Vector) soundFiles.clone();*/

        int count = 0;
        while (!cp.dlg.isShowing() && count < 10)
        {
            try
            {
                sleep(100);
            }
            catch (Exception e)
            {
            }
            count++;
        }
        cp.dlg.hide();
    }
    AudioConcat concatenator = new AudioConcat();
    static Vector oldBeforeConcat = new Vector(rhythmWheel.MAX_WHEELS); // the sound files for each wheel before concatenation
    static Vector oldAfterConcat = new Vector(rhythmWheel.MAX_WHEELS);

    // Creates and returns a vector of ByteArrayInputStreams from each wheel
    public Vector createSoundFile()
    {
        Vector inputStreams = new Vector(rhythmWheel.NUM_WHEELS); // the sound file for each wheel after concatenation
        int sliderVal = cp.slider.getValue();
        String delayFile = null;
        if (sliderVal > 0)
        {
            delayFile = "sounds/blank" + sliderVal + Sound.EXTENSION;
        }

        int numNonBlank = 0; // number of blank wheels
        int nonBlankIndex = -1; // index of last blank wheel (only used if numblank = 1)
        for (int i = 0; i < rhythmWheel.NUM_WHEELS; i++)
        {
            if (!rhythmWheel.wheelPanels[i].wheel.isBlank())
            {
                numNonBlank++;
                nonBlankIndex = i;
            }
        }
        if (numNonBlank == 0)
        { // They're all blank
            inputStreams.addElement(createInputStream(0, false, delayFile));
            cp.playIterations = 1;
        }
        else if (numNonBlank == 1)
        {
            inputStreams.addElement(createInputStream(nonBlankIndex, false,
                                                      delayFile));
            cp.playIterations = rhythmWheel.wheelPanels[nonBlankIndex].getIterations();
        }
        else
        {
            for (int w = 0; w < rhythmWheel.NUM_WHEELS; w++)
            {
                inputStreams.addElement(createInputStream(w, true,
                                                          delayFile));
            }

            //}
            // Use existing file
            //else {
            //System.err.println("Using existing file");
            //     wheelFiles.addElement(oldAfterConcat.elementAt(w));
            //}

            /*if (oldBeforeConcat.size() > w) {
            oldBeforeConcat.setElementAt(files, w);
            oldAfterConcat.setElementAt(wheelFiles.elementAt(w), w);
            }
            else {
            oldBeforeConcat.addElement(files);
            oldAfterConcat.addElement(wheelFiles.elementAt(w));
            }*/
        } // end for
        return inputStreams;
    }

    private ByteArrayInputStream createInputStream(int wheelNum,
                                                   boolean useWheelIter, String delayFile)
    {
        Wheel wheel = rhythmWheel.wheelPanels[wheelNum].wheel;
        Vector wheelSounds = wheel.getSounds();
        Vector fileNames = new Vector(); // The vector of files created for this wheel
        int wheelIterations = rhythmWheel.wheelPanels[wheelNum].getIterations();
        // Make sure it's a valid number of iterations
        if (wheelIterations < 0)
        {
            wheelIterations = 0;
            rhythmWheel.wheelPanels[wheelNum].loopField.setText("0");
        }
        else if (wheelIterations > 100)
        {
            rhythmWheel.wheelPanels[wheelNum].loopField.setText("100");
            wheelIterations = 100;
        }

        // If 0 iterations, just use a rest
        if (wheelIterations == 0)
        {
            fileNames.addElement(new Rest().strCurrentFileName);

        }
        if (useWheelIter)
        {
            for (int j = 0; j < wheelIterations; j++)
            {
                // Add the sounds to the files vector
                for (int s = 0; s < wheelSounds.size(); s++)
                {
                    Sound sound = (Sound) wheelSounds.elementAt(s);
                    fileNames.addElement(sound.strCurrentFileName);

                    if (delayFile != null)
                    {
                        fileNames.addElement(delayFile);
                    }
                }
            }
        }
        else
        {
            for (int s = 0; s < wheelSounds.size(); s++)
            {
                Sound sound = (Sound) wheelSounds.elementAt(s);
                fileNames.addElement(sound.strCurrentFileName);

                if (delayFile != null)
                {
                    fileNames.addElement(delayFile);
                }
            }

        }

        // Concatenate the files in the wheel
        return new ByteArrayInputStream(concatenator.Concat(fileNames));

    }
}
