package cyra.trongame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

@SuppressWarnings("serial")
public abstract class Map extends JComponent {


    // the player and all other players
    Player player1;
    Player[] players;
    Color[] colors = {Color.CYAN, Color.PINK, Color.WHITE, Color.YELLOW,
            Color.BLUE, Color.ORANGE, Color.RED, Color.GREEN};

    Random rand = new Random();

    // court dimensions
    int MAPWIDTH = 500;
    int MAPHEIGHT = 500;

    // initial velocity
    int velocity1 = 2;
    int velocity2 = 2;



    // score and score labels
    int i = 0;
    JLabel score1;
    JLabel score2;

    // the game timer and speed at which tick() is called
    int interval = 20;
    Timer timer;
    boolean run = true;

    // constructor adds KeyListeners and initializes fields
    public Map(JLabel sco1, JLabel sco2, int p) {
        setBackground(Color.WHITE);
        if (p > 8) { p = 8; }
        this.players = new Player[p];
        this.score1 = sco1;
        this.score2 = sco2;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        // timer that runs the game
        timer = new Timer(interval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        // player one controls
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!player1.getAlive()) {
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player1.setVelocityX(-velocity1);
                    player1.setVelocityY(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player1.setVelocityX(velocity1);
                    player1.setVelocityY(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player1.setVelocityY(-velocity1);
                    player1.setVelocityX(0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player1.setVelocityY(velocity1);
                    player1.setVelocityX(0);
                } else if (e.getKeyCode() == KeyEvent.VK_N) {
                    if (velocity1 >1) {
                        velocity1++;
                        if (player1.getVelocityX() > 0) {
                            player1.setVelocityX(velocity1);
                        } else if (player1.getVelocityX() < 0) {
                            player1.setVelocityX(-velocity1);
                        } else if (player1.getVelocityY() > 0) {
                            player1.setVelocityY(velocity1);
                        } else if (player1.getVelocityY() < 0) {
                            player1.setVelocityY(-velocity1);
                        }
                    }
                    //speed down
                } else if (e.getKeyCode() == KeyEvent.VK_M){
                    if (velocity1 >1 ) {
                        velocity1--;
                        if (player1.getVelocityX() > 0) {
                            player1.setVelocityX(-velocity1);
                        } else if (player1.getVelocityX() < 0) {
                            player1.setVelocityX(velocity1);
                        } else if (player1.getVelocityY() > 0) {
                            player1.setVelocityY(-velocity1);
                        } else if (player1.getVelocityY() < 0) {
                            player1.setVelocityY(velocity1);
                        }
                    }}
            }
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    public int[] getRandomStart1() {
        return randomStarts(2);
    }


    // returns an array of velocities and dimensions for a Player
    // ensures that the Player moves toward the center initially
    public int[] randomStarts(int velocity1) {
        int[] start = new int[4];
        int xnew = 50 + rand.nextInt(400);
        int ynew = 50 + rand.nextInt(400);
        int ra = rand.nextInt(2);
        int velx = 0;
        int vely = 0;
        if (ra == 0) {
            if (xnew < 250) {
                velx = velocity1;
            } else {
                velx = -velocity1;
            }
        } else {
            if (ynew < 250) {
                vely = velocity1;
            } else {
                vely = -velocity1;
            }
        }
        start[0] = xnew;
        start[1] = ynew;
        start[2] = velx;
        start[3] = vely;
        return start;
    }
    public int[] getRandomStart2() {
        return randomStarts(2);
    }

    public void setVelocity1(int velocity1){
        this.velocity1 = velocity1;
    }


    public void setVelocity2(int velocity2) {
        this.velocity2 = velocity2;
    }

    public int getVelocity1() {
        return velocity1;
    }

    public int getVelocity2() {
        return velocity2;
    }

    // moves the game by one timestamp
    abstract void tick();

    // initializes all new characters and restarts the timer
    abstract void reset();

    // changes the score being displayed
    abstract void setScore();

    // adds scores to high scores or sets the score after a level
    abstract void addScore();

    // draws the Player objects
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, MAPWIDTH, MAPHEIGHT);
        for (Player p: players) {
            if (p != null) {
                p.draw(g);
            }
        }
    }

    // sets the dimensions of the court
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MAPWIDTH, MAPHEIGHT);
    }
}