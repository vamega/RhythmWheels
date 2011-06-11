package RhythmWheels;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioConcat
{
    /**
     * Takes an integer representing the playback speed, and returns the factor, that the size
     * of a byteSteam representing a sound needs to be multiplied with in order to be played back
     * at that speed.
     * @param playbackSpeed The integer representing playback speed. This must be in the range
     *          [-x,-1)U[1,y].
     * @return A double that can be multiplied by the steam size to get the size the new stream
     *          needs to be.
     */
    private static double transformSpeed(int playbackSpeed)
    {
        return playbackSpeed/5;
    }

    public static AudioFormat audioFormat = null;

    public AudioConcat()
    {
    }

    /*************** MIX *****************************************
    Given a vector of ByteArrayInputStreams, returns a byte array
    representing the output stream created
     *************************************************************/
    public static byte[] Mix(Vector inputStreams, int playbackSpeed)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        List audioInputStreamList = new ArrayList();

        for (int i = 0; i < inputStreams.size(); i++)
        {
            AudioInputStream audioInputStream = null;
            try
            {
                ByteArrayInputStream bis = (ByteArrayInputStream) inputStreams.elementAt(i);
                audioInputStream = AudioSystem.getAudioInputStream(bis);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
            AudioFormat format = audioInputStream.getFormat();
            if (audioFormat == null)
            {
                audioFormat = format;
            }
            audioInputStreamList.add(audioInputStream);
        }

        AudioInputStream audioInputStream = new MixingAudioInputStream(audioFormat,
                                                                       audioInputStreamList);
        int frameSize = audioFormat.getFrameSize();

        try
        {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.AU, bos);
            audioInputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        byte[] original = bos.toByteArray();
        int numFrames = original.length/frameSize;
        byte[] modified;

        if (playbackSpeed < 0)
        {
            modified = new byte[(numFrames + numFrames * playbackSpeed / 5) * frameSize];
            for (int i = 0; i < (original.length / frameSize) + playbackSpeed - 1; i++)
            {
                for (int j = 0; j < frameSize; j++)
                {
                    modified[(((i * playbackSpeed) / 5) + i) * frameSize + j] = original[i * frameSize + j];
                }
            }
        }
        else
        {
            modified = original;
            /*
             * Everything here is handled by the sound concatenation.
             * This is because we're trying to avoid changing the pitch of the sound when
             * elongating playback. The method used to shorted the duration above alters the pitch.
             * If ever that is changed to not alter the pitch, then this code should be suitably
             * replaced to ease readability of the code.
             */
        }

        return modified;
    }// end Mix

    // Given a vector of fileNames to concat, returns a byte array representing
    // the concatenated output stream
    public static byte[] Concat(Vector fileNames)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AudioFormat audioFormat = null;
        List audioInputStreamList = new ArrayList();

        for (int i = 0; i < fileNames.size(); i++)
        {
            //System.err.println(files.elementAt(i));
            String fileName = (String) fileNames.elementAt(i);
            if (fileName == null)
            {
                System.err.println("Soundfile is null for the " + (i + 1) + "th file");
            }
            AudioInputStream audioInputStream = null;
            try
            {
                InputStream is;
                if (RhythmWheel.isApplet)
                {
                    is = RhythmWheel.class.getResourceAsStream(fileName);
                }
                else
                {
                    is = new FileInputStream(fileName);
                }

                ByteArrayInputStream bis = new ByteArrayInputStream(StreamUtil.toByteArray(is));
                audioInputStream = AudioSystem.getAudioInputStream(bis);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
            AudioFormat format = audioInputStream.getFormat();
            if (audioFormat == null)
            {
                audioFormat = format;
            }
            audioInputStreamList.add(audioInputStream);
        }

        AudioInputStream audioInputStream = new SequenceAudioInputStream(audioFormat,
                                                                         audioInputStreamList);

        try
        {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.AU, bos);
            audioInputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }
}

class StreamUtil
{
    public static byte[] toByteArray(final InputStream input) throws IOException
    {
        int status = 0;
        final int blockSize = 5000;
        int totalBytesRead = 0;
        int blockCount = 1;
        byte[] dynamicBuffer = new byte[blockSize * blockCount];
        final byte[] buffer = new byte[blockSize];

        boolean endOfStream = false;
        while (!endOfStream)
        {
            int bytesRead = 0;
            if (input.available() != 0)
            {
// data is waiting so read as
//much as is available
                status = input.read(buffer);
                endOfStream = (status == -1);
                if (!endOfStream)
                {
                    bytesRead = status;
                }
            }
            else
            {
// no data waiting so use the
//one character read to block until
// data is available or the end of the input stream is reached
                status = input.read();
                endOfStream = (status == -1);
                buffer[0] = (byte) status;
                if (!endOfStream)
                {
                    bytesRead = 1;
                }
            }

            if (!endOfStream)
            {
                if (totalBytesRead + bytesRead > blockSize * blockCount)
                {
// expand the size of the buffer
                    blockCount++;
                    final byte[] newBuffer = new byte[blockSize * blockCount];
                    System.arraycopy(dynamicBuffer, 0,
                                     newBuffer, 0, totalBytesRead);
                    dynamicBuffer = newBuffer;
                }
                System.arraycopy(buffer, 0,
                                 dynamicBuffer, totalBytesRead, bytesRead);
                totalBytesRead += bytesRead;
            }
        }

// make a copy of the array of the exact length
        final byte[] result = new byte[totalBytesRead];
        if (totalBytesRead != 0)
        {
            System.arraycopy(dynamicBuffer, 0, result, 0, totalBytesRead);
        }

        return result;
    }
}
/*
InputStream in = MyClass.class.getResourceAsStream("abc.gif");

// fully read the image into a byte array
final byte[] buffer = StreamUtil.toByteArray(input);
input.close();

// create an icon from the resulting buffer
ImageIcon imageIcon = new ImageIcon(buffer);
 */
/*** AudioConcat.java ***/
