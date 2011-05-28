package RhythmWheels;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

// This thread calls the createSoundFile function, then hides the dialog box
class ConcatThread extends Thread
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

    @Override
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
        cp.dlg.setVisible(false);
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
                                                   boolean useWheelIter,
                                                   String delayFile)
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