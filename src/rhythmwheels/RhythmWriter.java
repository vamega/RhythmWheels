package RhythmWheels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author madiav
 */
public class RhythmWriter
{
    
    public static final String CURRENT_FORMAT = "0.1";

    public static void saveState(Wheel[] wheels, File outputFile, RhythmWheel rw) throws FileNotFoundException,
                                                                         IOException
    {
        Element rhythm = new Element("rhythm");
        rhythm.setAttribute("format", CURRENT_FORMAT);
        rhythm.setAttribute("speed", Integer.toString(rw.controlPanel.getSpeed()));
        
        Document data = new Document(rhythm);

        List<Element> wheelElements = new LinkedList<Element>();

        for (int i = 0; i < wheels.length; i++)
        {
            Element currentWheel = new Element("wheel");
            List<Sound> soundObjects = wheels[i].getSounds();
            List<Element> soundElements = new LinkedList<Element>();

            for (int j = 0; j < soundObjects.size(); j++)
            {
                Sound currentSound = (Sound) soundObjects.get(j);
                String name = currentSound.getClass().getCanonicalName();

                Element soundElement = new Element("Sound");
                soundElement.setText(name);

                soundElements.add(soundElement);
            }

            currentWheel.setContent(soundElements);
            currentWheel.setAttribute("iterations", Integer.toString(rw.wheelPanels[i].getIterations()));
            wheelElements.add(currentWheel);
        }

        rhythm.addContent(wheelElements);

        XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
        OutputStream outStream = new FileOutputStream(outputFile);

        output.output(data, outStream);
    }

    public static void loadState(Wheel[] wheels, File inputFile, RhythmWheel rw) throws
            JDOMException, IOException, Exception
    {
        SAXBuilder documentBuilder = new SAXBuilder();
        Document data = documentBuilder.build(inputFile);

        if (!data.getRootElement().getAttributeValue("format").equals(CURRENT_FORMAT))
        {
            //TODO: Create Exception class for exceptions of this sort.
            throw new Exception("Incorrect Format");
        }

        List<Element> wheelElements = data.getRootElement().getChildren();
        rw.setNumWheels(wheelElements.size());
        rw.setDropDownValue(wheelElements.size());
        
        int wheelIndex = 0;
        for (Element currentWheel : wheelElements)
        {
            List<Element> soundElements = currentWheel.getChildren();
            //TODO: Add a clear method, and then clear the sounds.
            wheels[wheelIndex].setNumSounds(soundElements.size());
            List<Sound> sounds = wheels[wheelIndex].getSounds();

            int soundIndex = 0;
            for (Element soundElement : soundElements)
            {
                String currentSound = soundElement.getText();
                //Use reflection to get this working.
                Class soundClass = Class.forName(currentSound);
                Sound reflectedSound = (Sound) soundClass.newInstance();
                sounds.set(soundIndex++, reflectedSound);
            }
            rw.wheelPanels[wheelIndex].numPanel.select(soundElements.size() - 1);
            wheels[wheelIndex].repaint();
            ++wheelIndex;
        }
        
        rw.controlPanel.setSpeed(Integer.parseInt(data.getRootElement().getAttributeValue("speed")));
    }
}
