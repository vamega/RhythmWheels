package RhythmWheels;

/*
 *  Copyright (c) 1999 - 2001 by Matthias Pfisterer <Matthias.Pfisterer@web.de>
 *
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.Timer;

/**
 * This file is part of the Java Sound Examples.
 */
public class ClipPlayer extends Thread implements LineListener
{

    private Clip m_clip;
    private SourceDataLine line = null;
    Timer timer;

    /*
     *	The clip will be played nLoopCount + 1 times.
     */
    public ClipPlayer(InputStream is, int nLoopCount)
    {
        AudioInputStream audioInputStream = null;
        try
        {
            audioInputStream = AudioSystem.getAudioInputStream(is);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (audioInputStream != null)
        {
            AudioFormat format = audioInputStream.getFormat();
            //NOTE: Was SourceDataLine.Info
            DataLine.Info info = new DataLine.Info(Clip.class, format,
                                                   AudioSystem.NOT_SPECIFIED);
            //Mixer.Info [] inf = AudioSystem.getMixerInfo();
            // SourceDataLine [] lines =  new SourceDataLine[3];
            try
            {
                m_clip = (Clip) AudioSystem.getLine(info);
                m_clip.addLineListener(this);
                //m_clip.open(format, is.toByteArray, 0, is.length);
                m_clip.open(audioInputStream);
                // lines[0] = (SourceDataLine)AudioSystem.getLine(info);
                //lines[1] = (SourceDataLine)AudioSystem.getLine(info);

                // Mixer mixer = AudioSystem.getMixer(inf[0]);
                // System.err.println("Synch supported " + mixer.isSynchronizationSupported(lines, false));
            }
            catch (LineUnavailableException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (nLoopCount == 1)
            {
                m_clip.start();
            }
            else
            {
                m_clip.loop(nLoopCount - 1);
            }
        }
        else
        {
            System.out.println("ClipPlayer.<init>(): can't get data from input stream.");
        }
    }

    AudioInputStream audioInputStream;
    AudioFormat audioFormat;
    int nExternalBufferSize = 128000;
    boolean DEBUG = false;
    private boolean ok = true; // ok to keep writing to the data line?
    private int playIterations = 1; // number of times to play data
    private byte[] mybytes; // The data to play

    public ClipPlayer(byte[] bytes, AudioFormat audioFormat, Timer t, int iter)
    {
        timer = t;
        mybytes = bytes;
        playIterations = iter;
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        audioInputStream = null;
        this.audioFormat = audioFormat;

        try
        {
            audioInputStream = new AudioInputStream(is, audioFormat, bytes.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.exit(1);
        }

//        AudioFormat audioFormat = audioInputStream.getFormat();
        if (DEBUG)
        {
            System.out.println("AudioPlayer.main(): format: " + audioFormat);
        }

        int nInternalBufferSize = AudioSystem.NOT_SPECIFIED;

        DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                                               audioFormat, nInternalBufferSize);
        try
        {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat, nInternalBufferSize);
            line.addLineListener(this);
        }
        catch (LineUnavailableException e)
        {
            System.err.println("Line unavailable");
            e.printStackTrace();
        }
    }

    public void stopPlaying() // can't call it stop
    {
        ok = false;
        line.stop();
        line.close();
    }

    @Override
    public void run()
    {
        line.start();
        byte[] tempBytes = null;
        //if (playIterations > 1)
        tempBytes = (byte[]) mybytes.clone();

        //      System.err.println("playiterations = " + playIterations);
        for (int i = 0; i < playIterations; i++)
        {
            //    System.err.println("iteration " + i + " of " + playIterations);
            mybytes = (byte[]) tempBytes.clone();
            try
            {
                audioInputStream = new AudioInputStream(new ByteArrayInputStream(mybytes), audioFormat, mybytes.length);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            int nBytesRead = 0;
            byte[] abData = new byte[nExternalBufferSize];
            ok = true;
            while (nBytesRead != -1 && ok)
            {
                try
                {
                    nBytesRead = audioInputStream.read(abData, 0, abData.length);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (DEBUG)
                {
                    System.out.println(
                            "AudioPlayer.main(): read from AudioInputStream (bytes): "
                            + nBytesRead);
                }
                if (nBytesRead >= 0)
                {
                    int nBytesWritten = line.write(abData, 0, nBytesRead);
                    if (DEBUG)
                    {
                        System.out.println(
                                "AudioPlayer.main(): written to SourceDataLine (bytes): "
                                + nBytesWritten);
                    }
                }
            }
            // line.drain();
        /*
             *	Wait until all data is played.
             *	This is only necessary because of the bug noted below.
             *	(If we do not wait, we would interrupt the playback by
             *	prematurely closing the line and exiting the VM.)
             *
             *	Thanks to Margie Fitch for bringing me on the right
             *	path to this solution.
             */
            if (DEBUG)
            {
                System.out.println("AudioPlayer.main(): before drain");
            }
        } // end for

    }

    public void update(LineEvent event)
    {
        if (event.getType().equals(LineEvent.Type.STOP))
        {
            if (line != null)
            {
                line.close();
            }
            if (m_clip != null)
            {
                m_clip.close();
            }
        }
        else if (event.getType().equals(LineEvent.Type.CLOSE))
        {
            /*
             *	There is a bug in the jdk1.3.0.
             *	It prevents correct termination of the VM.
             *	So we have to exit ourselves.
             */
            if (line != null)
            {
                line.close();
            }
            if (m_clip != null)
            {
                m_clip.close();
            }
        }
        else if (event.getType().equals(LineEvent.Type.START))
        {
            if (timer != null)
            {
                timer.restart();
            }
        }

    }
}
/*** ClipPlayer.java ***/
