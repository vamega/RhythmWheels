package RhythmWheels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    /*
     * TODO: Remove the repaint call from within setRotationAngle, and make only a single repaint
     * operation at the end of this method.
     */
    /*
     * TODO: Improve the quality of this documentation
     * Here we are updating the rotation angle of the wheel, updating the counter of beneath the
     * wheel, and incrementing the previous rotation angle of the wheel if the new rotation angle
     * is greater than the next multiple of 2pi/soundlenght
     */
    public void actionPerformed(ActionEvent evt)
    {
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
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
                /*
                 * The rotation angle is equivalent to the time that has passed since the last
                 * time the circle was redrawn, times 2pi divided by the amount of time it takes
                 * to play a single sound.
                 */
                rotationAngle = (2.0 * Math.PI * (double) timeFromStart / (double) soundLength[i]);

                rotationDifference = (rotationAngle - wheels[i].getPreviousRotationAngle());

                if (rotationDifference >= (2.0 * Math.PI / wheels[i].getSounds().size()))
                {
                    wheels[i].setSoundsPlayedCounter(
                            wheels[i].getSoundsPlayedCounter()
                            + (int) (rotationDifference / (2.0 * Math.PI / wheels[i].getSounds().size())));
                }
                wheels[i].setRotationAngle(rotationAngle);
                wheels[i].repaint();
            }
            else
            {
                /*
                 * Only increment the counter if the wheel hasn't already reached the maximum value
                 * it needs to reach.
                 */
                if (wheels[i].getSoundsPlayedCounter() != wheelIterations[i] * wheels[i].getSounds().size())
                {
                    wheels[i].setSoundsPlayedCounter(wheels[i].getSoundsPlayedCounter() + 1);
                    wheels[i].setRotationAngle(0.0);
                    wheels[i].repaint();
                }
            }

            if (rotationDifference >= (2.0 * Math.PI / wheels[i].getSounds().size()))
            {
                wheels[i].setPreviousRotationAngle(
                        wheels[i].getSoundsPlayedCounter() * (2.0 * Math.PI / wheels[i].getSounds().size()));
            }
        }

        if (!someRunning)
        {
            cp.paintTimer.stop();
        }
    }

    public void reset()
    {
        lastTimeFromStart = -1;
        startTime = -1;
        int numsounds;
        for (int i = 0; i < RhythmWheel.NUM_WHEELS; i++)
        {
            wheelIterations[i] = 0;
            try
            {
                wheelIterations[i] = Integer.parseInt(
                        cp.rhythmWheel.wheelPanels[i].loopField.getText());
            }
            catch (NumberFormatException e)
            {
            }
            if (wheelIterations[i] < 0)
            {
                wheelIterations[i] = 0;
            }
            numsounds = wheels[i].getSounds().size();
            soundLength[i] = numsounds *
                    (Sound.SOUND_LENGTH + Sound.SOUND_LENGTH * cp.getSpeed()/(cp.MAX_SPEED - cp.MIN_SPEED));
            wheels[i].setSoundsPlayedCounter(0);
            wheels[i].setPreviousRotationAngle(0);

        }
    }
}