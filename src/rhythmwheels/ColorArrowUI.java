/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rhythmwheels;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Varun Madiath <vamega@gmail.com>
 */
class ColorArrowUI extends BasicComboBoxUI
{

    public static ComboBoxUI createUI(JComponent c)
    {
        return new ColorArrowUI();
    }

    @Override
    protected JButton createArrowButton()
    {
        return new BasicArrowButton(
                BasicArrowButton.SOUTH,
                RhythmWheel.BACKGROUND_COLOR,
                RhythmWheel.BACKGROUND_COLOR,
                RhythmWheel.FOREGROUND_COLOR,
                RhythmWheel.FOREGROUND_COLOR);
    }
}