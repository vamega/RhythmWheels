package rhythmwheels;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * This class runs the RhythmWheels program as an application.
 * This makes it useful for debugging, as it allows log files to be made.
 * @author Varun Madiath
 */
public class TestingMain
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame topFrame = new JFrame("Rhythm Wheels");
                topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                TopContainer root = new TopContainer();
                topFrame.add(root.panes);
                
//                topFrame.setSize(1000,800);
                topFrame.setVisible(true);
            }
        });
    }
}
