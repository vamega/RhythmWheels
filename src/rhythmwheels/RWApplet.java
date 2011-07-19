package rhythmwheels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RWApplet extends JApplet implements ActionListener, WindowListener
{

    RhythmWheel rw;
    JButton button = new JButton("Start Rhythm Wheels");

    @Override
    public void init()
    {
        rw = new RhythmWheel(getDocumentBase());
        rw.addWindowListener(this);
        rw.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        button.addActionListener(this);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(button);
        getContentPane().add(panel);
    }

    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == button)
        {
            if (button.getText().startsWith("Start"))
            {
                showWindow();
            }
            else
            {
                button.setText("Start Rhythm Wheels");
                hideWindow();
            }
        }
    }

    private void showWindow()
    {
        rw.setVisible(true);
        button.setText("Close Rhythm Wheels");
    }

    private void hideWindow()
    {
        button.setText("Start Rhythm Wheels");
        rw.setVisible(false);
    }

    public void windowOpened(WindowEvent evt)
    {
        showWindow();
    }

    public void windowIconified(WindowEvent evt)
    {
        hideWindow();
    }

    public void windowDeiconified(WindowEvent evt)
    {
        showWindow();
    }

    public void windowDeactivated(WindowEvent evt)
    {
    }

    public void windowActivated(WindowEvent evt)
    {
    }

    public void windowClosing(WindowEvent evt)
    {
        hideWindow();
    }

    public void windowClosed(WindowEvent evt)
    {
    }
}
