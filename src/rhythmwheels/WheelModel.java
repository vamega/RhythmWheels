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
    private double playedCounter;

    public WheelModel()
    {
        wheelSounds = new ArrayList<Sound>();
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
    
    public double getPlayedCounter()
    {
        return playedCounter;
    }

    public void setPlayedCounter(double playedCounter)
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
    
    public void addSoundToWheel(Sound s, int position)
    {
        
    }
    
    public List<Sound> getWheelSounds()
    {
        return wheelSounds;
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