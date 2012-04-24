package rhythmwheels.scripting;

import rhythmwheels.RhythmWheel;
import rhythmwheels.soundcategories.SoundCategory;

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
    
    public void setIterations(int wheelNum, int newIterations)
    {
        rw.wheelPanels[wheelNum-1].setIterations(newIterations);
    }
    
    public boolean isWheelEmpty(int wheelNum)
    {
        return rw.wheelPanels[wheelNum-1].wheel.getWheelModel().isWheelEmpty();
    }
    
    public void emptyWheel(int wheelNum)
    {
        rw.wheelPanels[wheelNum-1].wheel.getWheelModel().empty();
    }
    
    public void randomlyFillWheel(int wheelNum)
    {
        rw.wheelPanels[wheelNum-1].wheel.getWheelModel().empty();
    }
    
    public void randomlyFillWheelFromCategory(int wheelNum, SoundCategory category)
    {
        
    }
    
    public void fillWheelFromFile(String filepath, int wheelNum)
    {
        
    }
    
    public void loadRhythmFile(String filepath)
    {
        
    }
    

}