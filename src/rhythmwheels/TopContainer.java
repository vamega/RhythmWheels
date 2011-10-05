/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rhythmwheels;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

/**
 *
 * @author Varun Madiath <vamega@gmail.com>
 */
public class TopContainer extends JPanel
{

    JSplitPane panes;

    public TopContainer()
    {
        panes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        RhythmWheel rw = new RhythmWheel();
        panes.setRightComponent(rw);

        JEditorPane htmlContent = new JEditorPane();
        htmlContent.setContentType("text/html");
        try
        {
            htmlContent.setPage(new File("test.html").toURI().toURL());
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(TopContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(TopContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JScrollPane htmlScrollPane = new JScrollPane(htmlContent);
        panes.setLeftComponent(htmlScrollPane);
    }
}
