package rhythmwheels;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Varun Madiath
 */
public class SoundLoader
{
    public static void loadSounds(File soundListFile) throws JDOMException, IOException
    {
        SAXBuilder documentBuilder = new SAXBuilder();
        Document data = documentBuilder.build(soundListFile);
        
        List<Element> sounds = data.getRootElement().getChildren("sound");
        
        for (Element currentSoundElement : sounds)
        {
            String name = currentSoundElement.getChildText("name");
            String displayName = currentSoundElement.getChildText("displayName");
            String maxVolumeStr = currentSoundElement.getChildText("maxVolume");
            int maxVolume = 3;
            
            if(maxVolumeStr != null)
            {
                maxVolume = Integer.parseInt(maxVolumeStr);
            }
            
            Sound currentSound = new Sound(name, displayName, maxVolume);
            Sound.installedSounds.put(name, currentSound);
            
        }
    }
}
