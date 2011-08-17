package rhythmwheels;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for concatenating sounds in the Wheel, and creating an stream that
 * can be used for playback. Once these operations are complete the Thread in this class closes the
 * dialog that the ControlPanel set up that
 * @author Varun Madiath (vamega@gmail.com)
 */
class ConcatThread extends Thread
{

    static ControlsPanel cp;
    static RhythmWheel rhythmWheel;
    static List<ByteArrayInputStream> inputStreams;
    byte[] oldbytes;

    public ConcatThread(ControlsPanel c)
    {
        cp = c;
        rhythmWheel = cp.rhythmWheel;
    }

    @Override
    public void run()
    {
        inputStreams = createSoundFile();

        cp.mixedBytes = AudioConcat.Mix(inputStreams);
        cp.audioFormat = AudioConcat.audioFormat;

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
     * TODO: Rename this to something more appropriate.
     */
    /**
     * Creates ByteArrayInputStreams representing the concatenated sound for each wheel.
     * @return A List of the concatenated ByteArrayInputStreams. The indices of the List
     *         correspond to indices of the wheels.
     */
    public List<ByteArrayInputStream> createSoundFile()
    {
        // the sound file for each wheel after concatenation
        List<ByteArrayInputStream> inputStreams = new ArrayList<ByteArrayInputStream>(
                rhythmWheel.NUM_WHEELS);
        int sliderVal = cp.slider.getValue();
        String delayFile = null;
        if (sliderVal > 0)
        {
            delayFile = "sounds/blank" + sliderVal + Sound.EXTENSION;
        }

        for (int w = 0; w < rhythmWheel.NUM_WHEELS; w++)
        {
            inputStreams.add(createInputStream(w, delayFile,
                                               rhythmWheel.wheelPanels[w].wheel.getSounds(),
                                               rhythmWheel.wheelPanels[w].getIterations(),
                                               cp.getSpeed()));
        }
        return inputStreams;
    }

    /**
     * Creates a ByteArrayInputStream from the sounds placed in a Wheel.
     * @param wheelNum The wheel index for which to a ByteArrayInputStream is necessary.
     * @param useWheelIter Need to figure this one out
     * @param delayFile
     * @return
     */
    private static ByteArrayInputStream createInputStream(int wheelNum, String delayFile,
                                                   List<Sound> wheelSounds, int wheelIterations,
                                                   int speed)
    {
//        Wheel wheel = rhythmWheel.wheelPanels[wheelNum].wheel;
//        List<Sound> wheelSounds = wheel.getSounds();

        // The list of files created for this wheel
        List<String> fileNames = new ArrayList<String>();
//        int wheelIterations = rhythmWheel.wheelPanels[wheelNum].getIterations();

        for (int j = 0; j < wheelIterations; j++)
        {
            // Add the sounds to the files List
            for (int s = 0; s < wheelSounds.size(); s++)
            {
                Sound sound = wheelSounds.get(s);
                fileNames.add(sound.strCurrentFileName);

                if (delayFile != null)
                {
                    fileNames.add(delayFile);
                }
            }
        }

        // Concatenate the files in the wheel
//        return new ByteArrayInputStream(concatenator.sequence(fileNames, cp.slider.getValue()));
        return new ByteArrayInputStream(AudioConcat.sequence(fileNames, speed));

    }
}
