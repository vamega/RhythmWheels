package rhythmwheels;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Varun Madiath (vamega@gmail.com)
 */
public class RhythmWriter
{

    public static final String CURRENT_FORMAT = "0.2";
    //Named Elements
    public static final String ROOT_ELEMENT = "engine";
    public static final String SOUND_ELEMENT = "sound";
    public static final String WHEEL_ELEMENT = "wheel";
    
    public static final String ROOT_FORMAT_ATTRIBUTE = "format";
    public static final String ROOT_TYPE_ATTRIBUTE = "type";
    public static final String ROOT_SPEED_ATTRIBUTE = "speed";
    
    public static final String WHEEL_ITERATIONS_ATTRIBUTE = "speed";
    
    public static final String SOUND_VOLUME_ATTRIBUTE = "volume";

    public static void saveState(Wheel[] wheels, File outputFile, RhythmWheel rw) throws
            FileNotFoundException,
            IOException
    {
        Element engine = new Element(ROOT_ELEMENT);
        engine.setAttribute(ROOT_TYPE_ATTRIBUTE, "RS");
        engine.setAttribute(ROOT_FORMAT_ATTRIBUTE, CURRENT_FORMAT);
        engine.setAttribute(ROOT_SPEED_ATTRIBUTE, Integer.toString(rw.controlPanel.getSpeed()));

        Document data = new Document(engine);

        List<Element> wheelElements = new LinkedList<Element>();

        for (int i = 0; i < wheels.length; i++)
        {
            Element currentWheel = new Element(WHEEL_ELEMENT);
            List<Sound> soundObjects = wheels[i].getSounds();
            List<Element> soundElements = new LinkedList<Element>();

            for (int j = 0; j < soundObjects.size(); j++)
            {
                Sound currentSound = (Sound) soundObjects.get(j);
                String name = currentSound.strFileBaseName;

                Element soundElement = new Element(SOUND_ELEMENT);
                soundElement.setText(name);
                soundElement.setAttribute(SOUND_VOLUME_ATTRIBUTE, Integer.toString(currentSound.getVolume()));

                soundElements.add(soundElement);
            }

            if (!soundElements.isEmpty())
            {
                currentWheel.setContent(soundElements);
                currentWheel.setAttribute(WHEEL_ITERATIONS_ATTRIBUTE,
                                          Integer.toString(rw.wheelPanels[i].getIterations()));
                wheelElements.add(currentWheel);
            }
        }

        engine.addContent(wheelElements);

        XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
        OutputStream outStream = new FileOutputStream(outputFile);

        output.output(data, outStream);
        outStream.close();
    }

    public static void loadState(Wheel[] wheels, File inputFile, RhythmWheel rw) throws
            JDOMException,
            IOException,
            Exception
    {
        SAXBuilder documentBuilder = new SAXBuilder();
        Document data = documentBuilder.build(inputFile);

        if (!data.getRootElement().getAttributeValue(ROOT_FORMAT_ATTRIBUTE).equals(CURRENT_FORMAT))
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
                Sound reflectedSound = (Sound) Sound.installedSounds.get(currentSound).clone();

                int soundVolume;
                try
                {
                    soundVolume = Integer.parseInt(soundElement.getAttributeValue(SOUND_VOLUME_ATTRIBUTE));
                }
                catch (NumberFormatException numberFormatException)
                {
                    throw new JDOMParseException(
                            "\""+ SOUND_VOLUME_ATTRIBUTE + "\"" +
                            "attribute of sound element has non-numeric content",
                            numberFormatException, data);
                }

                reflectedSound.setVolume(soundVolume);
                sounds.set(soundIndex++, reflectedSound);
            }

            int iterations;
            try
            {
                iterations = Integer.parseInt(currentWheel.getAttributeValue(
                        WHEEL_ITERATIONS_ATTRIBUTE));
            }
            catch (NumberFormatException numberFormatException)
            {
                throw new JDOMParseException(
                        "\"" + WHEEL_ITERATIONS_ATTRIBUTE + "\""
                        + "attribute of sound element has non-numeric content",
                        numberFormatException, data);
            }

            rw.wheelPanels[wheelIndex].setIterations(iterations);

            rw.wheelPanels[wheelIndex].numPanel.select(soundElements.size() - 1);
            wheels[wheelIndex].repaint();
            ++wheelIndex;
        }

        rw.controlPanel.setSpeed(Integer.parseInt(data.getRootElement().getAttributeValue(
                ROOT_SPEED_ATTRIBUTE)));
    }
}
