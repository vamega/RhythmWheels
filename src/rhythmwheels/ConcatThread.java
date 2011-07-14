package RhythmWheels;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Vector;

/**
 * This class is responsible for concatenating sounds in the Wheel, and creating an stream that
 * can be used for playback. Once these operations are complete the Thread in this class closes the
 * dialog that the ControlPanel set up that
 * @author madiav
 */
class ConcatThread extends Thread
{

    static ControlsPanel cp;
    static RhythmWheel rhythmWheel;
    static Vector inputStreams;
    //static Vector oldSoundFiles;
    byte[] oldbytes;
    AudioConcat concatenator = new AudioConcat();
    static Vector oldAfterConcat = new Vector(rhythmWheel.MAX_WHEELS);

    public ConcatThread(ControlsPanel c)
    {
        cp = c;
        rhythmWheel = cp.rhythmWheel;
    }

    @Override
    public void run()
    {
        inputStreams = createSoundFile();

        cp.mixedBytes = concatenator.Mix(inputStreams, cp.slider.getValue());
        cp.audioFormat = concatenator.audioFormat;

        int count = 0;
        /*
         * TODO: Possibly replace this with a wait/notify system?
         */
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
        cp.dlg.setVisible(false);
    }

    /*
     * TODO: Evaluate possibity of replacing this with ArrayList.
     */
    /**
     * Creates ByteArrayInputStreams representing the concatenated sound for each wheel.
     * @return A Vector of the concatenated ByteArrayInputStreams. The indices of the Vector
     *         correspond to indices of the wheels.
     */
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

        /*
         * TODO: See if this set of conditionals can be collapsed to simplify the code paths.
         */
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
        }
        return inputStreams;
    }

    /*
     * TODO: Examine this code throughly and figure out exactly what the purpose of useWheelIter does.
     * Then fill in the Javadocs.
     */
    /**
     * Creates a ByteArrayInputStream from the sounds placed in a Wheel.
     * @param wheelNum The wheel index for which to a ByteArrayInputStream is necessary.
     * @param useWheelIter Need to figure this one out
     * @param delayFile
     * @return
     */
    private ByteArrayInputStream createInputStream(int wheelNum,
                                                   boolean useWheelIter,
                                                   String delayFile)
    {
        Wheel wheel = rhythmWheel.wheelPanels[wheelNum].wheel;
        List<Sound> wheelSounds = wheel.getSounds();
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
                    Sound sound = wheelSounds.get(s);
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
                Sound sound = wheelSounds.get(s);
                fileNames.addElement(sound.strCurrentFileName);

                if (delayFile != null)
                {
                    fileNames.addElement(delayFile);
                }
            }
        }

        // Concatenate the files in the wheel
        return new ByteArrayInputStream(concatenator.sequence(fileNames, cp.slider.getValue()));

    }
}
