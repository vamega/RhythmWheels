package rhythmwheels.soundcategories;

import rhythmwheels.Sound;

public abstract class SoundCategory
{
    protected  Sound[] sounds;
    protected  String[] names;
    protected  String catName;
    int numSounds = 8;

    public SoundCategory()
    {
    }

    /**
     * 
     * @return 
     */
    public Sound[] getSounds()
    {
        return sounds;
    }

    /**
     * 
     * @return 
     */
    public int getNumSounds()
    {
        return numSounds;
    }

    /**
     * 
     * @return 
     */
    public String[] getNames()
    {
        return names;
    }
    
    @Override
    public String toString()
    {
        return catName;
    }
}
