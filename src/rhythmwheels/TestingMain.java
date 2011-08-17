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
                RhythmWheel rw = new RhythmWheel();
                rw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                rw.setVisible(true);
            }
        });
    }
}
