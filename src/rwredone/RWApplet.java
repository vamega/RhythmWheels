package rwredone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.applet.*;
import java.net.URL;

public class RWApplet extends JApplet implements ActionListener, WindowListener
{

    RhythmWheel rw;
    JButton button = new JButton("Start Rhythm Wheels");

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

    public void start()
    {
        rw.start();
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
        rw.show();
        button.setText("Close Rhythm Wheels");
    }

    private void hideWindow()
    {
        button.setText("Start Rhythm Wheels");
        rw.hide();
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
