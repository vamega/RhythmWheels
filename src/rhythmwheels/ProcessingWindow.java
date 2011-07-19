package rhythmwheels;

import javax.swing.JFrame;
import java.awt.HeadlessException;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProcessingWindow extends JFrame
{

    private RhythmWheel rw;

    public ProcessingWindow(RhythmWheel r)
    {
        rw = r;
        setSize(250, 75);
        setTitle("Processing Sounds");
        int cx = (rw.getX() + rw.getWidth()) / 2 - getWidth() / 2;
        int cy = (rw.getY() + rw.getHeight()) / 2 - getHeight() / 2;
        setLocation(cx, cy);
        setResizable(false);

        //setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel p = new JPanel();
        p.add(new JLabel("Processing Sounds..."));
        getContentPane().add(p);
    }

    public void setVisible(boolean b)
    {
        int cx = (rw.getX() + rw.getWidth()) / 2 - getWidth() / 2;
        int cy = (rw.getY() + rw.getHeight()) / 2 - getHeight() / 2;
        setLocation(cx, cy);

        super.setVisible(b);
        //repaint();
    }
}
