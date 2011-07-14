package RhythmWheels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
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

    public static void saveState(Wheel[] wheels, File outputFile) throws FileNotFoundException,
                                                                               IOException
    {
        Element rhythm = new Element("rhythm");
        rhythm.setAttribute("format", "1");
        Document data = new Document(rhythm);

        List<Element> wheelElements = new LinkedList<Element>();

        int i = 0;
        do
        {
            Element currentWheel = new Element("wheel");
            //TODO: Replace this with generics.
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
            wheelElements.add(currentWheel);
        }
        while(++i < wheels.length && !wheels[i].isBlank());

        rhythm.addContent(wheelElements);

        XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
        OutputStream outStream = new FileOutputStream(outputFile);

        output.output(data, outStream);
    }

    public static void loadState(Wheel[] wheels, File inputFile, RhythmWheel rw) throws JDOMException, IOException, Exception
    {
        SAXBuilder documentBuilder = new SAXBuilder();
        Document data = documentBuilder.build(inputFile);

        if(!data.getRootElement().getAttributeValue("format").equals("1"))
        {
            //TODO: Create Exception class for exceptions of this sort.
            throw new Exception("Incorrect Format");
        }

        List<Element> wheelElements = data.getRootElement().getChildren();

        int wheelIndex = 0;
        for (Element currentWheel : wheelElements)
        {
            List<Element> soundElements = currentWheel.getChildren();
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
            wheels[wheelIndex].setNumSounds(soundElements.size());
            wheels[wheelIndex].repaint();
            ++wheelIndex;
        }

        rw.setNumWheels(wheelElements.size());
    }
}
