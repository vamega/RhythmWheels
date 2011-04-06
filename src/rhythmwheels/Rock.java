package RhythmWheels;

public class Rock extends SoundCategory
{

    public Rock()
    {
        catName = "Rock";
        numSounds = 9;
        sounds = new Sound[numSounds];

        sounds[0] = new Rest();
        sounds[1] = new AcousticBass();
        sounds[2] = new AcousticSnare();
        sounds[3] = new ElectricSnare();
        sounds[4] = new LowFloorTom();
        sounds[5] = new OpenHighConga();
        sounds[6] = new HiHatO();
        sounds[7] = new Splash();
        sounds[8] = new Crash();

        names = new String[numSounds];
        names[0] = "Rest";
        names[1] = "Acoustic Bass";
        names[2] = "Acoustic Snare";
        names[3] = "Electric Snare";
        names[4] = "Low Tom";
        names[5] = "Open High Conga";
        names[6] = "Hi Hat-O";
        names[7] = "Splash";
        names[8] = "Crash";
    }
}
