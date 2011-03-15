package rwredone;

public class LatinoCaribbean extends SoundCategory
{

    public LatinoCaribbean()
    {
        catName = "Latino-Caribbean";
        numSounds = 10;
        sounds = new Sound[10];

        sounds[0] = new Rest();
        sounds[1] = new Open();
        sounds[2] = new Tip();
        sounds[3] = new Slap();
        sounds[4] = new Heel();
        sounds[5] = new Neck();
        sounds[6] = new Mouth();
        sounds[7] = new Clave();
        sounds[8] = new Maracas();
        sounds[9] = new Tamborine();

        names = new String[numSounds];
        names[0] = "Rest";
        names[1] = "Open";
        names[2] = "Tip";
        names[3] = "Slap";
        names[4] = "Heel";
        names[5] = "Neck Cowbell";
        names[6] = "Mouth Cowbell";
        names[7] = "Clave";
        names[8] = "Maracas";
        names[9] = "Tamborine";
    }
}
