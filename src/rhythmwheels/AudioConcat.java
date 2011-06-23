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
        return original;
    }

     /**
      * Creates a stream representing of the sequencing of the audio from a list of audio files.
      * @param fileNames The list of strings representing the file names of the audio files.
      * @param playbackSpeed A factor which determines how much of the end of each audioStream to
      *        drop so as to achieve sounds that finish playback in a shorter period of time.
      * @return A byte array representing the sequenced audio files.
      */
    public static byte[] sequence(Vector fileNames, int playbackSpeed)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AudioFormat audioFormat = null;
        List audioInputStreamList = new ArrayList();

        for (int i = 0; i < fileNames.size(); i++)
        {
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
                                                                         audioInputStreamList, playbackSpeed);

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