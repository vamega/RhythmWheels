package rhythmwheels.soundcategories;

import java.util.ArrayList;
import java.util.List;
import rhythmwheels.Sound;

public class SoundCategory
{
    protected  Sound[] sounds;
    protected  String[] names;
    protected  String catName;
    int numSounds = 8;
    public static List<SoundCategory> installeCcategories = 
            new ArrayList<SoundCategory>();

    public SoundCategory(Sound[] m_sounds, String[] m_names, String m_catName, 
            int m_numSounds)
    {                
        sounds = m_sounds;
        names = m_names;
        catName = m_catName;
        numSounds = m_numSounds;
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
