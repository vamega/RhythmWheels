package rhythmwheels.scripting;

import rhythmwheels.RhythmWheel;

/**
 * This class acts as the interface to the Java codebase for scripts.
 * The only api exposed to the scripts is defined in this file.
 * @author Varun Madiath vamega@gmail.com
 */
public class RhythmFacade
{

    private RhythmWheel rw;

    public RhythmFacade(RhythmWheel rw)
    {
        this.rw = rw;
    }

    public void setNumberOfWheels(int newNumWheels)
    {
        rw.setNumWheels(newNumWheels);
    }

    public void lockNumberOfWheels()
    {
        rw.lockNumWheels();
    }

    public void unlockNumberOfWheels()
    {
        rw.unlockNumWheels();
    }

    public int getIterations(int wheelNum)
    {
        return rw.wheelPanels[wheelNum-1].getIterations();
    }
    
}