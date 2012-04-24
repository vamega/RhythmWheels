package rhythmwheels;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Varun Madiath
 */
public class WheelModel
{
    private List<Sound> wheelSounds;
    private double rotation;
    private volatile int playedCounter;

    public WheelModel()
    {
        wheelSounds = new ArrayList<Sound>();
        wheelSounds.add(Sound.getNewInstance("rest"));
        rotation = 0;
        playedCounter = 0;
    }

    // Methods operating on the wheels rotation.
    public double getRotation()
    {
        return rotation;
    }
    
    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

    // Methods operating on the counter.
    public int getPlayedCounter()
    {
        return playedCounter;
    }

    public void setPlayedCounter(int playedCounter)
    {
        this.playedCounter = playedCounter;
    }
    
    public void incrementPlayedCounter()
    {
        setPlayedCounter(getPlayedCounter()+1);
    }
    
    public void resetPlayedCounter()
    {
        setPlayedCounter(0);
    }

    // Methods operating on the sounds.
    /**
     * Places a sound in the wheel at a particular position. If there is a sound currently at that 
     * position it is replaced. If the position is larger than the wheels current sized, the wheel
     * remains unchanged.
     * 
     * @param s The Sound to place in the sound wheel. A new copy of this sound is made and added to
     *          to the wheel, so the Sound passed in is never modified.
     * @param position The position in the wheel where the sound should be placed. Values that will
     *        cause the method to return success are in the range
     *        [0,<code>getWheelCapacity()</code>).
     * 
     * @return true if the sound was placed in the wheel. false otherwise.
     */
    public void empty()
    {
        for (int i = 0; i < wheelSounds.size(); i++)
        {
            wheelSounds.set(i, Sound.getNewInstance("rest"));
        }
    }
    
    public void randomlyFillWheel()
    {
        Sound[] installedSounds = Sound.installedSounds.values().toArray(new Sound[Sound.installedSounds.size()]);
        
        for (int i = 0; i < wheelSounds.size(); i++)
        {
            int randomNum = random(installedSounds.length-1);
            wheelSounds.set(i, installedSounds[randomNum]);
        }
    }
    
    private int random(int limit)
    {
        return (int) (Math.random()*(limit+1));
    }
    
    public boolean placeSoundInWheel(Sound s, int position)
    {
        if(position < 0 || position > getWheelCapacity())
        {
            return false;
        }
        else
        {
            try
            {
                wheelSounds.set(position, (Sound) s.clone());
            }
            catch (Exception e)
            {
                return false;
            }
            
            return true;
        }
    }
    
    public Sound getSoundAtPosition(int position)
    {
        return wheelSounds.get(position);
    }
    
    public List<Sound> getWheelSounds()
    {
        return wheelSounds;
    }
    
    public boolean isWheelEmpty()
    {
        for (Sound sound : wheelSounds)
        {
            if(!sound.getStrFileBaseName().equals("rest"));
            {
                return false;
            }
        }
        
        return true;
    }
    
    public int getWheelCapacity()
    {
        return getWheelSounds().size();
    }
    
    public void setWheelCapacity(int capacity)
    {
        int currentSize = getWheelCapacity();;
        
        // Case where no change is necessary.
        if(currentSize == capacity)
        {
            
        }
        else if(currentSize < capacity)
        {
            for (int i = currentSize; i < capacity; i++)
            {
                wheelSounds.add(Sound.getNewInstance("rest"));
            }
        }
        else
        {
            wheelSounds.subList(capacity, wheelSounds.size()).clear();
        }
        
    }
}