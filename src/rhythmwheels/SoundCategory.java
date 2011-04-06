package RhythmWheels;

public abstract class SoundCategory
{
    Sound[] sounds;
    String[] names;
    String catName;
    int numSounds = 8;

    public SoundCategory()
    {
    }

    @Override
    public String toString()
    {
        return catName;
    }
}
