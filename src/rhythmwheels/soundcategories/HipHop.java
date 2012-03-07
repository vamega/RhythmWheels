package rhythmwheels.soundcategories;

import rhythmwheels.Sound;

public class HipHop extends SoundCategory
{
    public HipHop()
    {
        numSounds = 9;
        catName = "HipHop";
        sounds = new Sound[numSounds];
        
//        for (String sound : Sound.installedSounds.keySet())
//        {
//            System.out.println(sound);
//        }
        
        
        sounds[0] = Sound.installedSounds.get("rest");
        sounds[1] = Sound.installedSounds.get("scratch1");
        sounds[2] = Sound.installedSounds.get("scratch2");
        sounds[3] = Sound.installedSounds.get("scratch3");
        sounds[4] = Sound.installedSounds.get("hup");
        sounds[5] = Sound.installedSounds.get("clap");
        sounds[6] = Sound.installedSounds.get("tube");
        sounds[7] = Sound.installedSounds.get("bassdrum");
        sounds[8] = Sound.installedSounds.get("hihat");

        names = new String[numSounds];
        
        for (int i = 0; i < sounds.length; i++)
        {
            names[i] = sounds[i].displayName;
            
        }
    }
}