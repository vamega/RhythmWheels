/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rhythmwheels.scripting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import javax.script.*;
import rhythmwheels.RhythmWheel;

/**
 *
 * @author Varun Madiath <vamega@gmail.com>
 */
public class ScriptManager
{
    // The Script Engine manager
    private ScriptEngineManager factory;
    // The actual Script Engine
    private ScriptEngine engine;
    
    private static ScriptManager s;

    public ScriptManager(RhythmWheel rw)
    {
        factory = new ScriptEngineManager();
        // Request the Javascript script engine.
        engine = factory.getEngineByName("JavaScript");
        RhythmFacade apiBindings = new RhythmFacade(rw);
        engine.put("RhythmWheels", apiBindings);
        s = this;
    }
    
    public static ScriptManager getInstance()
    {
        return s;
    }
    
    public void loadScript(File f) throws FileNotFoundException, ScriptException
    {
        engine.eval(new FileReader(f));
    }
    
    public void loadScript(Reader r) throws ScriptException
    {
        engine.eval(r);
    }
    
    public void fireEvent(Events e)
    {
        Invocable invocableEngine = (Invocable) engine;
        try
        {
            switch (e)
            {
                case ON_LOAD:
                    invocableEngine.invokeFunction("onLoad");
                    break;
                case ON_UNLOAD:
                    invocableEngine.invokeFunction("onUnload");
                    break;
                case ON_PLAY:
                    invocableEngine.invokeFunction("onPlay");
                    break;
                case ON_WHEELS_COMPLETE:
                    invocableEngine.invokeFunction("onWheelsComplete");
                    break;
            }
        }
        catch (ScriptException scriptException)
        {
        }
        catch (NoSuchMethodException noSuchMethodException)
        {
        }
    }
}