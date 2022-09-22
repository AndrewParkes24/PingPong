import java.awt.*;

public class Ball {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.

    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;
    public int hits;
    public boolean ballintersect = false;


    // METHOD DEFINITION SECTION

    //This is a constructor that takes 3 parameters.  This allows us to specify the object's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
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


    } // constructor

    public void pause ( int time){
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }


    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
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



} //end of the Cheese object class  definition



