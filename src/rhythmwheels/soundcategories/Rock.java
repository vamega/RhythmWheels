package rhythmwheels.soundcategories;

import rhythmwheels.Sound;

public class Rock extends SoundCategory
{

    public Rock()
    {
        catName = "Rock";
        numSounds = 9;
        sounds = new Sound[numSounds];

        sounds[0] = Sound.installedSounds.get("rest");
        sounds[1] = Sound.installedSounds.get("acousticbass");
        sounds[2] = Sound.installedSounds.get("acousticsnare");
        sounds[3] = Sound.installedSounds.get("electricsnare");
        sounds[4] = Sound.installedSounds.get("lowfloortom");
        sounds[5] = Sound.installedSounds.get("openhighconga");
        sounds[6] = Sound.installedSounds.get("hihato");
        sounds[7] = Sound.installedSounds.get("splash");
        sounds[8] = Sound.installedSounds.get("crash");

        names = new String[numSounds];
        
        for (int i = 0; i < sounds.length; i++)
        {
            names[i] = sounds[i].displayName;
            
        }
    }
}
