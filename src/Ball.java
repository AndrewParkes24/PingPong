import java.awt.*;

public class Ball {


    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public boolean isAlive;
    public int dx;
    public int dy;
    public Rectangle rec;
    public Image pic;
    public int hits;
    public boolean ballintersect = false;


    public Ball(int xpos, int ypos, int pXpos, int pYpos, Image ballpic) {

        this.xpos = pXpos;
        this.ypos = pYpos;
        width = 20;
        height = 20;
        dx = 7;
        dy = -7;
        pic = ballpic;
        isAlive = true;
        hits = 0;
        rec = new Rectangle(xpos, ypos, width, height);


    }

    public void pause ( int time){
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }



    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;


        if (xpos > 1000 || xpos < 0 - width) {
            System.out.println(xpos);
            xpos = 500;
            ypos = 350;
            dx = 7;
            dy = -7;
            pause(2000);
        }

        if (ypos > 700 - height || ypos < 0 - height) {
            dy = -dy;
        }



        rec = new Rectangle(xpos, ypos, width, height);

    }



}



