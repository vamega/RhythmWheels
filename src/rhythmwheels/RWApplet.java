package rhythmwheels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RWApplet extends JApplet implements ActionListener, WindowListener
{

    JFrame window;
    JButton button = new JButton("Start Rhythm Wheels");

    @Override
    public void init()
    {
        final WindowListener rwWindowListener = this;
        final ActionListener rwActionListener = this;
        
        try
        {
            SwingUtilities.invokeAndWait(new Runnable()
            {
                public void run()
                {
                    window = new JFrame();
                    TopContainer root = new TopContainer(getDocumentBase());
                    window.add(root);
                    window.addWindowListener(rwWindowListener);
                    window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    button.addActionListener(rwActionListener);
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    panel.add(button);
                    getContentPane().add(panel);
                }
            });
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(RWApplet.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvocationTargetException ex)
        {
            Logger.getLogger(RWApplet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        window.setVisible(true);
        button.setText("Close Rhythm Wheels");
    }

    private void hideWindow()
    {
        button.setText("Start Rhythm Wheels");
        window.setVisible(false);
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
