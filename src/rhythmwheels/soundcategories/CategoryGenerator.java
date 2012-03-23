package rhythmwheels.soundcategories;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import rhythmwheels.Sound;

/**
 *
 * @author Vincent Riemer
 */
public class CategoryGenerator 
{   
    
    public static void generateCategories(File categoryListFile) 
            throws JDOMException, IOException
    {
        /*Using JDOM, prepare the soundcategories.xml to be read*/
        SAXBuilder documentBuilder = new SAXBuilder();
        Document data = documentBuilder.build(categoryListFile);
        
        /*store all the categories in a list*/
        List<Element> categories = data.getRootElement()
                .getChildren("soundcategory");
        
        /*get the number of categories*/
        int size_alloc = categories.size();        
        
        /*for every sound category*/
        for (int i = 0; i < size_alloc; i++) 
        {
            /*get the current sound category and set its name*/
            Element currentCatElement = categories.get(i);
            String name = currentCatElement.getChildText("name");
            
            /*get the element of all sounds,store its size, and prepare to store 
              the sounds and their names*/
            Element soundElement = currentCatElement.getChild("sounds");
            List<Element> sounds = soundElement.getChildren("sound");
            int numSounds = sounds.size();            
            Sound[] soundF = new Sound[numSounds];
            String[] names = new String[numSounds];
            
            /*get and store all the sounds in the category's xml file*/
            for(int j = 0; j < numSounds; j++)
            {
                /*get the single song element and temporarily store it's name*/
                Element currentSoundElement = sounds.get(j);
                String fileName = currentSoundElement.getChildText("name");
                String displayName = currentSoundElement.getChildText("displayName");
                
                /*get and store the sound and name;*/
                names[j] = displayName;
                soundF[j] = Sound.installedSounds.get(fileName);
            }
            /*create the new sound category from the information taken from the 
             * xml and store it into the list of installed sound categories */
            SoundCategory newSC = new SoundCategory(soundF, names, name, 
                    numSounds);            
            
           /* debugging code */
            System.out.println(names.length);
            
            SoundCategory.installeCcategories.add(newSC);
        }    
        System.out.println(SoundCategory.installeCcategories.size());
    }
}
