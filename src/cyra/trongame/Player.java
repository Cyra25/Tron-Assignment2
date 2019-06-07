package cyra.trongame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Player extends LightCycle {

    boolean lightwallOn=true;
    // player's colors
    Color color;

    // states of the player
    boolean alive = true;

    // initial conditions
    int startVel = 0;

    // static values to be used by all Player objects
    static int WIDTH = 2;
    static int HEIGHT = 2;

    // Player object's path
    ArrayList<Shape> lines = new ArrayList<Shape>();

    // constructor initializes initial conditions, timer, and color
    public Player(int randX, int randY, int velx, int vely, Color color, String playerName) {
        super(randX, randY, velx, vely, WIDTH, HEIGHT);
        startVel = Math.max(Math.abs(velx), Math.abs(vely));
//        boostTimer = new Timer(300, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                booster = false;
//                boostTimer.stop();
//            }
//        });
        this.color = color;
    }


    // changes state of Player if it exits the bounds
    public void accelerate() {
        if (x < 0 || x > rightBound) {
            velocityX = 0;
            alive = false;
        }
        if (y < 0 || y > bottomBound) {
            velocityY = 0;
            alive = false;
        }
    }


    // draws Player and path
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT);
        for (Shape k: lines) {
            k.draw(g);
        }
    }

    // returns the state of the Player
    public boolean getAlive() {
        return alive;
    }

    // returns the Player's path
    public ArrayList<Shape> getPath() {
        return lines;
    }

    // checks if the Player has crashed with a path
    public void crash(Intersection i) {
        if (i == Intersection.UP) {
            velocityX = 0;
            velocityY = 0;
            alive = false;
        }
    }

    public void addPlayers(Player[] players) {
    }



    //lightwall on and off
    public void lightwall(){
        if (lightwallOn == false) {
            lightwallOn = true;
        }else if (lightwallOn == true){
            lightwallOn = false;
        }
    }

    //speed up
//    public void speedDown(){
//        if (velocityY == 0) {
//            velocityX = velocityX - 0.3;
//        }else if (velocityX == 0) {
//            velocityY = velocityY - 0.3;
//        }
//        System.out.println("speed down "+velocityX +" and "+velocityY);
//    }

    //speed down
//    public void speedUp(){
//        if (velocityY == 0) {
//            velocityX = velocityX + 0.3;
//        }else if (velocityX == 0) {
//            velocityY = velocityY + 0.3;
//        }
//        System.out.println("Velocity up "+velocityX +" and "+velocityY);
//    }

    // moves the Player based on its conditions
    public void move() {
        int a = x;
        int b = y;
        if (true) {
            x += velocityX;
            y += velocityY;
            if (lightwallOn) {
                if (lines.size() > 1) {
                    Shape l1 = lines.get(lines.size() - 2);
                    Shape l2 = lines.get(lines.size() - 1);
                    if (a == l1.getStartX() &&
                            l1.getEndY() == l2.getStartY()) {
                        lines.add(new Line(l1.getStartX(), l1.getStartY(),
                                l2.getEndX(), l2.getEndY()));
                        lines.remove(lines.size() - 2);
                        lines.remove(lines.size() - 2);
                    } else if (b == l1.getStartY() &&
                            l1.getEndX() == l2.getStartX()) {
                        lines.add(new Line(l1.getStartX(), l1.getStartY(),
                                l2.getEndX(), l2.getEndY()));
                        lines.remove(lines.size() - 2);
                        lines.remove(lines.size() - 2);
                    }
                }
                lines.add(new Line(a, b, x, y));
            }
            accelerate();
            clip();
        }
    }
}