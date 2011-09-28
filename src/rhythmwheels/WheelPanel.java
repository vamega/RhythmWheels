package rhythmwheels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

// Class WheelPanel handles the spinning of the wheel.  Contains a NumberPanel
public class WheelPanel extends JPanel
{

    public Wheel wheel = new Wheel();
    public Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    public NumberPanel numPanel = new NumberPanel(wheel);
    SpinnerNumberModel loopSpinnerModel = new SpinnerNumberModel(1, 0, 100, 1);
    public JSpinner loopSpinner = new JSpinner(loopSpinnerModel);
    public JLabel loopLabel = new JLabel("Loop:");
    public JPanel inputPanel = new JPanel();
    private JPanel top = new JPanel(new FlowLayout());

    public WheelPanel()
    {
        setLayout(new BorderLayout());
        top.add(numPanel);
        
        add(top, BorderLayout.NORTH);
        add(wheel, BorderLayout.CENTER);
        loopLabel.setForeground(Color.white);

        if (RhythmWheel.isLowRes())
        {
            Font current = loopLabel.getFont();
            loopLabel.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
            loopSpinner.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
        }

        inputPanel.add(loopLabel);
        inputPanel.add(loopSpinner);
        add(inputPanel, BorderLayout.SOUTH);
        changeColors();
    }

    /**
     * Sets the background and foreground color of all components in this panel, as well as the 
     * panel itself to BACKGROUND_COLOR and FOREGROUND_COLOR respectively.
     */
    private void changeColors()
    {
        RhythmWheel.changeComponent(this, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(top, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(numPanel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(wheel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(loopLabel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(inputPanel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        numPanel.changeColor(BACKGROUND_COLOR, FOREGROUND_COLOR);
    }

    /**
     * Gets the number of iterations of this wheel.
     * @return The number of iterations for this wheel, as shown in the Loop Field.
     */
    public int getIterations()
    {
        int wheelIterations = (Integer) loopSpinner.getValue();
        return wheelIterations;
    }
    
    /**
     * Sets the number of iterations for this wheel
     * @param numIterations The new number of iterations for this wheel, as is to be shown in the
     *                      Loop Field.
     */
    public void setIterations(int numIterations)
    {
        loopSpinner.setValue(numIterations);
    }
}