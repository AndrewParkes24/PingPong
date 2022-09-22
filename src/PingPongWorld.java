
//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

//Keyboard and Mouse
//Step 0 - Import
import java.awt.event.*;

//*******************************************************************************
// Class Definition Section

//Step 1 for Keyboard control - implements Keylistener
public class PingPongWorld implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial valu'es too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Image ballpic;
    public Image paddlepic;
    public Image endScreenpic;
    public Image backgroundpic;

    public Ball ultimateball;
    public Player user1;
    public Player user2;

    public SoundFile pongnoise;

    public boolean endScreen = false;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        PingPongWorld myApp = new PingPongWorld();   //creates a new instance of the game
        new Thread(myApp).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public PingPongWorld() {

        setUpGraphics();

        //Step 3 - Keyboard use.  addKeyListener(this)
        canvas.addKeyListener(this);

        //variable and objects
        //load images
        ballpic = Toolkit.getDefaultToolkit().getImage("ball.pic"); //load the picture
        paddlepic = Toolkit.getDefaultToolkit().getImage("paddle.pic"); //load the picture
        endScreenpic = Toolkit.getDefaultToolkit().getImage("end.pic");
        backgroundpic = Toolkit.getDefaultToolkit().getImage("background.pic");

        pongnoise = new SoundFile("pongnoise.wav");

        ultimateball = new Ball(50, 350, 5, -5, ballpic);

        user1 = new Player(950, 300, 5, 5, paddlepic);
        user2 = new Player(0, 300, 5, 5, paddlepic);

        //construct array
 /*       cheeseArray = new Cheese[5];

        for (int i = 0; i < cheeseArray.length; i++) {

            cheeseArray[i] = new Cheese((int) (Math.random() * 900) + 1, (int) (Math.random() * 650) + 1, (int) (Math.random() * 5) + 1, (int) (Math.random() * 5) + 1, cheesePic);
*/
    }


    // CheeseWorld()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up

    public void moveThings() {
        //calls the move( ) code in the objects
        ultimateball.move();
        user1.move();
        user2.move();

    }

    public void checkIntersections() {
        if (ultimateball.rec.intersects(user1.rec) && ultimateball.ballintersect == false) {
            ultimateball.ballintersect = true;
            ultimateball.dx = -ultimateball.dx;
            pongnoise.play();
        }

        if (ultimateball.rec.intersects(user2.rec) && ultimateball.ballintersect == false) {
            ultimateball.ballintersect = true;
            ultimateball.dx = -ultimateball.dx;
            pongnoise.play();
        }

        if (!ultimateball.rec.intersects(user1.rec) && ultimateball.ballintersect == true) {
            ultimateball.ballintersect = false;
        }

        if (!ultimateball.rec.intersects(user2.rec) && ultimateball.ballintersect == true) {
            ultimateball.ballintersect = false;
        }
        if (ultimateball.xpos > 993) {
            user1.points = user1.points +1;

        }
        if (ultimateball.xpos <= 0) {
            user2.points = user2.points +1;
        }

    }


    //paints things on the screen using bufferStrategy
    public void render () {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if(user1.points >= 5 || user2.points >= 5) {
            g.drawImage(endScreenpic,0,0,1000,700,null);
        }
        else {


            //put your code to draw things on the screen here.
            g.drawImage(backgroundpic, 0, 0, 1000, 700, null);
            g.drawImage(ballpic, ultimateball.xpos, ultimateball.ypos, ultimateball.width, ultimateball.height, null);
            g.drawImage(user1.pic, user1.xpos, user1.ypos, user1.width, user1.height, null);
            g.drawImage(user2.pic, user2.xpos, user2.ypos, user2.width, user2.height, null);
            g.setColor(Color.white);
            g.drawString("user2 has: " + user2.points + " points", 750, 100);
            g.drawString("user1 has: " + user1.points + " points", 150, 100);
            g.setColor(Color.black);
        }


        g.dispose();
        bufferStrategy.show();
    }


    public void run () {

        //for the moment we will loop things forever.
        while (true) {
            checkIntersections();
            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }

    //Graphics setup method
    public void setUpGraphics () {
        frame = new JFrame("PingPongWorld");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause ( int time){
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //*******************************************************************************
    //  The Required 3 Keyboard Methods
    //  You need to have all 3 even if you aren't going to use them all
    //*******************************************************************************

    public void keyPressed (KeyEvent event){
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);
//Add the arrows to if you can later

        if (keyCode == 83) {
            user2.down = true;
        }
        if (keyCode == 87) {
            user2.up = true;
        }
        if (keyCode == 40) {
            user1.down = true;
        }
        if (keyCode == 38) {
            user1.up = true;
        }

    }//keyPressed()

    public void keyReleased (KeyEvent event){
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //Add the arrows to if you can later

        if (keyCode == 83) {
            user2.down = false;
        }
        if (keyCode == 87) {
            user2.up = false;
        }
        if (keyCode == 40) {
            user1.down = false;
        }
        if (keyCode == 38) {
            user1.up = false;
        }


    }//keyReleased()

    public void keyTyped (KeyEvent event){
        // handles a press of a character key  (any key that can be printed but not keys like SHIFT)
        // we won't be using this method but the definition needs to be in your program
    }//keyTyped()


}



