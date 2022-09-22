import java.awt.*;

public class Player {


    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;

    //movement booleans
    public boolean down;
    public boolean up;
    public int points=0;


    public Player(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 50;
        height = 100;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);

    } // constructor

    //move( ) method for a keyboard controlled character
    public void move() {

        if(down==true){
            ypos = ypos +dy;
            if(ypos>700-height){
                ypos = 700-height;
            }
        }
        if(up==true){
            ypos = ypos - dy;
            if(ypos<0){
                ypos = 0;
            }
        }

        rec = new Rectangle(xpos, ypos, width, height);

    }

}



