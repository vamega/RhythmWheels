package rhythmwheels;

/*
 *	SequenceAudioInputStream.java
 *
 *	This file is part of the Java Sound Examples.
 */

/*
 *  Copyright (c) 1999 - 2001 by Matthias Pfisterer <Matthias.Pfisterer@web.de>
 *  Modifications by Varun Madiath.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class SequenceAudioInputStream extends AudioInputStream
{

    private static final boolean DEBUG = true;
    private List m_audioInputStreamList;
    private int m_nCurrentStream;
    private ArrayList<Byte> sequencedData;
    private boolean isSequenced;
    private int streamOffset;
    int speed;

    public SequenceAudioInputStream(AudioFormat audioFormat, Collection audioInputStreams, int speed)
    {
        super(new ByteArrayInputStream(new byte[0]), audioFormat, AudioSystem.NOT_SPECIFIED);
        m_audioInputStreamList = new ArrayList<AudioInputStream>(audioInputStreams);
        sequencedData = new ArrayList<Byte>();
        isSequenced = false;
        this.speed = speed < 1 ? speed : 0;
//        this.speed = speed;
        streamOffset = 0;
        m_nCurrentStream = 0;
    }

    private boolean addAudioInputStream(AudioInputStream audioStream)
    {
        if (DEBUG)
        {
            System.out.println("SequenceAudioInputStream.addAudioInputStream(): called.");
        }
        // Contract.check(audioStream != null);
        if (!getFormat().matches(audioStream.getFormat()))
        {
            if (DEBUG)
            {
                System.out.println(
                        "SequenceAudioInputStream.addAudioInputStream(): audio formats do not match, trying to convert.");
            }
            AudioInputStream asold = audioStream;
            audioStream = AudioSystem.getAudioInputStream(getFormat(), asold);
            if (audioStream == null)
            {
                System.out.println(
                        "###  SequenceAudioInputStream.addAudioInputStream(): could not convert.");
                return false;
            }
            if (DEBUG)
            {
                System.out.println(" converted");
            }
        }
        // Contract.check(audioStream != null);
        synchronized (m_audioInputStreamList)
        {
            m_audioInputStreamList.add(audioStream);
            m_audioInputStreamList.notifyAll();
        }
        if (DEBUG)
        {
            System.out.println(
                    "SequenceAudioInputStream.addAudioInputStream(): enqueued " + audioStream);
        }
        return true;
    }

    private AudioInputStream getCurrentStream()
    {
        return (AudioInputStream) m_audioInputStreamList.get(m_nCurrentStream);
    }

    private boolean advanceStream()
    {
        m_nCurrentStream++;
        boolean bAnotherStreamAvailable = (m_nCurrentStream < m_audioInputStreamList.size());
        return bAnotherStreamAvailable;
    }

    @Override
    public long getFrameLength()
    {
        long lLengthInFrames = 0;
        Iterator streamIterator = m_audioInputStreamList.iterator();
        while (streamIterator.hasNext())
        {
            AudioInputStream stream = (AudioInputStream) streamIterator.next();
            long lLength = stream.getFrameLength();
            if (lLength == AudioSystem.NOT_SPECIFIED)
            {
                return AudioSystem.NOT_SPECIFIED;
            }
            else
            {
                lLengthInFrames += lLength;
            }
        }
        return lLengthInFrames;
    }

    @Override
    public int read() throws IOException
    {
        sequenceData();

        if (streamOffset < sequencedData.size())
        {
            return sequencedData.get(streamOffset++);
        }
        else
        {
            return -1;
        }
    }

    @Override
    public int read(byte[] abData, int nOffset, int nLength) throws IOException
    {
        sequenceData();

        int bytesRead = 0;
        for (int i = 0; i < abData.length && streamOffset < sequencedData.size(); i++, bytesRead++)
        {
            abData[i] = sequencedData.get(streamOffset++);
        }

        if (bytesRead == 0)
        {
            return -1;
        }
        else
        {
            return bytesRead;
        }
    }

    private void sequenceData() throws IOException
    {
        if (!isSequenced)
        {
            /*
             * We read data in using a buffer for greater performance.
             */
            byte[] abData = new byte[4096];

            /*
             * The number of bytes that are read into the buffer.
             */
            int nBytesRead;

            /*
             * This loop reads the data from each individual AudioInputStream, and appends the
             * portion of the data from that stream that needs to be maintained into a list.
             *
             * It then advances to the next stream, and repeats the process.
             *
             * The portion of the data to be maintained is decided by the speed variable.
             */
            do
            {
                /*
                 * The number of bytes that the current stream has.
                 */
                int totalBytesRead = 0;
                AudioInputStream stream = getCurrentStream();
                /*
                 * An optimization to avoid unecessary reallocation.
                 */
                sequencedData.ensureCapacity(sequencedData.size() + abData.length);

                /*
                 * Places the content of the stream into the list.
                 */
                while ((nBytesRead = stream.read(abData, 0, abData.length)) != -1)
                {
                    totalBytesRead += nBytesRead;
                    for (int i = 0; i < nBytesRead; i++)
                    {
                        sequencedData.add(abData[i]);
                    }
                }

                /*
                 * fSize is the frame size of the AudioInputStream.
                 */
                long fSize = stream.getFormat().getFrameSize();
                int numFrames = (int) (totalBytesRead / fSize);

                /*
                 * The formula that determines how much of the original stream to keep.
                 * Calculations are performed on the number of frames, to avoid ending up with
                 * and array that isn't a integer multiple of the frame size.
                 */
                int bytesRequired = (int) ((numFrames
                                            + numFrames * speed
                                              / (ControlsPanel.MAX_SPEED - ControlsPanel.MIN_SPEED))
                                           * fSize);

//                if(bytesRequired < totalBytesRead)
//                {
                /*
                 * Calculate the position in the list, after which data need not be maintained.
                 */
                int startIndex = sequencedData.size() - (totalBytesRead - bytesRequired);

                /*
                 * The sublist created is backed by the original list, so when it is cleared, the
                 * original list has that range of elements removed as well.
                 *
                 * This would be the equivalent of calling sequencedData.removeRange(startIndex, endIndex)
                 * but that method is protected. This calls that method behind the scenes.
                 */
                sequencedData.subList(startIndex, sequencedData.size()).clear();
//                }
//                else
//                {
//                    Byte[] empty = new Byte[(int)((bytesRequired - totalBytesRead))];
//                    
//                    for (int i = 0; i < empty.length; i++)
//                    {
//                        empty[i] = 0;
//                    }
//                    
//                    sequencedData.addAll(Arrays.asList(empty));
//                }
            }
            while (advanceStream());

            isSequenced = true;
        }
    }

    @Override
    public long skip(long lLength) throws IOException
    {
        throw new IOException(
                "skip() is not implemented in class SequenceInputStream. Mail <Matthias.Pfisterer@web.de> if you need this feature.");
    }

    @Override
    public int available() throws IOException
    {
        return getCurrentStream().available();
    }

    @Override
    public void close() throws IOException
    {
        // TODO: should we close all streams in the list?
    }

    @Override
    public void mark(int nReadLimit)
    {
        throw new RuntimeException(
                "mark() is not implemented in class SequenceInputStream. Mail <Matthias.Pfisterer@web.de> if you need this feature.");
    }

    @Override
    public void reset() throws IOException
    {
        throw new IOException(
                "reset() is not implemented in class SequenceInputStream. Mail <Matthias.Pfisterer@web.de> if you need this feature.");
    }

    @Override
    public boolean markSupported()
    {
        return false;
    }
}
/*** SequenceAudioInputStream.java ***/
