package rhythmwheels.soundcategories;

import rhythmwheels.Sound;

public class LatinoCaribbean extends SoundCategory
{
    public LatinoCaribbean()
    {
        catName = "Latino-Caribbean";
        numSounds = 10;
        sounds = new Sound[10];
        
        sounds[0] = Sound.installedSounds.get("rest");
        sounds[1] = Sound.installedSounds.get("open");
        sounds[2] = Sound.installedSounds.get("tip");
        sounds[3] = Sound.installedSounds.get("slap");
        sounds[4] = Sound.installedSounds.get("heel");
        sounds[5] = Sound.installedSounds.get("neck");
        sounds[6] = Sound.installedSounds.get("mouth");
        sounds[7] = Sound.installedSounds.get("clave");
        sounds[8] = Sound.installedSounds.get("maracas");
        sounds[9] = Sound.installedSounds.get("tamborine");

        names = new String[numSounds];
        
        for (int i = 0; i < sounds.length; i++)
        {
            names[i] = sounds[i].displayName;
        }
    }
}
