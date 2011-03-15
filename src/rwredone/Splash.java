package rwredone;

//package rhythmwheel;

import java.applet.AudioClip;
import java.awt.*;

public class Splash extends Sound {

    public Splash() {
          super("splash");
    }

     public void paintMe(Graphics g)
     {
          g.translate(p.x, p.y);
          g.setColor(SOUND_COLOR);

          g.drawLine(3*w/8, h/2 - 2, 5*w/8, h/2 - 2);
          g.drawLine(3*w/8, h/2 - 10, 5*w/8, h/2 - 10);
          g.translate(-p.x, -p.y);
     }
}
