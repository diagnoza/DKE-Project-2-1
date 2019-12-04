import javax.swing.*;
import java.awt.*;

public class BackgammonBoard extends JComponent {


    //Outer white board triangles
    Triangle tri1 = new Triangle(new int[]{45, 72, 100}, new int[]{45, 300, 45});
    Triangle tri2 = new Triangle(new int[]{100, 127, 160}, new int[]{45, 300, 45});
    Triangle tri3 = new Triangle(new int[]{160, 187, 220}, new int[]{45, 300, 45});
    Triangle tri4 = new Triangle(new int[]{220, 247, 280}, new int[]{45, 300, 45});
    Triangle tri5 = new Triangle(new int[]{280, 307, 340}, new int[]{45, 300, 45});
    Triangle tri6 = new Triangle(new int[]{340, 367, 400}, new int[]{45, 300, 45});

    //White home board triangles
    Triangle tri7 = new Triangle(new int[]{425, 452, 480}, new int[]{45, 300, 45});
    Triangle tri8 = new Triangle(new int[]{480, 507, 540}, new int[]{45, 300, 45});
    Triangle tri9 = new Triangle(new int[]{540, 567, 600}, new int[]{45, 300, 45});
    Triangle tri10 = new Triangle(new int[]{600, 627, 660}, new int[]{45, 300, 45});
    Triangle tri11 = new Triangle(new int[]{660, 687, 720}, new int[]{45, 300, 45});
    Triangle tri12 = new Triangle(new int[]{720, 747, 780}, new int[]{45, 300, 45});

    //Black outer board triangles
    Triangle tri13 = new Triangle(new int[]{42, 72, 100}, new int[]{615, 330, 615});
    Triangle tri14 = new Triangle(new int[]{100, 127, 160}, new int[]{615, 330, 615});
    Triangle tri15 = new Triangle(new int[]{160, 187, 220}, new int[]{615, 330, 615});
    Triangle tri16 = new Triangle(new int[]{220, 247, 280}, new int[]{615, 330, 615});
    Triangle tri17 = new Triangle(new int[]{280, 307, 340}, new int[]{615, 330, 615});
    Triangle tri18 = new Triangle(new int[]{340, 367, 400}, new int[]{615, 330, 615});

    //Black home board triangles
    Triangle tri19 = new Triangle(new int[]{425, 452, 480}, new int[]{615, 330, 615});
    Triangle tri20 = new Triangle(new int[]{480, 507, 540}, new int[]{615, 330, 615});
    Triangle tri21 = new Triangle(new int[]{540, 567, 600}, new int[]{615, 330, 615});
    Triangle tri22 = new Triangle(new int[]{600, 627, 660}, new int[]{615, 330, 615});
    Triangle tri23 = new Triangle(new int[]{660, 687, 720}, new int[]{615, 330, 615});
    Triangle tri24 = new Triangle(new int[]{720, 747, 780}, new int[]{615, 330, 615});


    Triangle[] triangle = new Triangle[]{tri1, tri2, tri3, tri4, tri5, tri6, tri7, tri8, tri9, tri10, tri11, tri12, tri13, tri14, tri15, tri16, tri17, tri18, tri19, tri20, tri21, tri22, tri23, tri24};


    public void paintComponent(Graphics g) {
        //Main Rect
        g.setColor(Color.decode("#722620"));
        g.fillRect(30, 30, 900, 610);

        //Outer board Rectangle
        g.setColor(Color.decode("#D2B48C"));
        g.fillRect(45, 45, 355, 570);

        //home board rectangle
        g.setColor(Color.decode("#D2B48C"));
        g.fillRect(425, 45, 355, 570);


        g.drawRect(795, 45, 100, 285);
        g.drawRect(795, 330, 100, 285);

        // g.setColor(Color.decode("#5B0E2D"));
        // g.fillRect(1000, 30, 300, 305);
        //
        //
        // g.setColor(Color.decode("#BC8F8F"));
        // g.fillRect(1000, 335, 300, 305);

        for (int i = 0; i < triangle.length; i++) {
            if (i >= 12) {
                if (i % 2 == 0) {
                    g.setColor(Color.decode("#5B0E2D"));//dark
                    g.fillPolygon(triangle[i].x, triangle[i].y, 3);
                } else {
                    g.setColor(Color.decode("#BC8F8F"));//light
                    g.fillPolygon(triangle[i].x, triangle[i].y, 3);
                }
            } else {

                if (i % 2 == 0) {
                    g.setColor(Color.decode("#BC8F8F"));//dark
                    g.fillPolygon(triangle[i].x, triangle[i].y, 3);
                } else {
                    g.setColor(Color.decode("#5B0E2D"));
                    g.fillPolygon(triangle[i].x, triangle[i].y, 3);
                }


            }
        }

    }

}
