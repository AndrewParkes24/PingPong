
//K. Chun 8/2018, Andrew Parkes 2021


import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;


import java.awt.event.*;


public class PingPongWorld implements Runnable, KeyListener {


    final int WIDTH = 1000;
    final int HEIGHT = 700;

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

    public static void main(String[] args) {
        PingPongWorld myApp = new PingPongWorld();
        new Thread(myApp).start();
    }


    public PingPongWorld() {

        setUpGraphics();


        canvas.addKeyListener(this);


        ballpic = Toolkit.getDefaultToolkit().getImage("ball.pic");
        paddlepic = Toolkit.getDefaultToolkit().getImage("paddle.pic");
        endScreenpic = Toolkit.getDefaultToolkit().getImage("end.pic");
        backgroundpic = Toolkit.getDefaultToolkit().getImage("background.pic");

        pongnoise = new SoundFile("pongnoise.wav");

        ultimateball = new Ball(50, 350, 5, -5, ballpic);

        user1 = new Player(950, 300, 5, 5, paddlepic);
        user2 = new Player(0, 300, 5, 5, paddlepic);


 /*       cheeseArray = new Cheese[5];

        for (int i = 0; i < cheeseArray.length; i++) {

            cheeseArray[i] = new Cheese((int) (Math.random() * 900) + 1, (int) (Math.random() * 650) + 1, (int) (Math.random() * 5) + 1, (int) (Math.random() * 5) + 1, cheesePic);
*/
    }




    public void moveThings() {

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



    public void render () {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if(user1.points >= 5 || user2.points >= 5) {
            g.drawImage(endScreenpic,0,0,1000,700,null);
        }
        else {



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


        while (true) {
            checkIntersections();
            moveThings();
            render();
            pause(20);
        }
    }


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


    public void pause ( int time){

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }


    public void keyPressed (KeyEvent event){

        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

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

    }

    public void keyReleased (KeyEvent event){
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();


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


    }

    public void keyTyped (KeyEvent event){

    }


}



