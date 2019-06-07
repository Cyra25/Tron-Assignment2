package cyra.trongame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class TwoPlayerMap extends Map {

    //Names of the players
    private String player1name, player2name;

    // the second human player
    private Player player2;

    private Color playerOneColor;
    private Color playerTwoColor;
    // scores of player one and two
    private int i = 0;
    private int j = 0;

    // outcome of the match
    private boolean p1 = false;
    private boolean p2 = false;
    private boolean tie = false;

    //highscores
    public Integer[] highscores = new Integer[]{0,0,0,0,0};

    // constructor calls super and adds KeyListeners
    public TwoPlayerMap(JLabel sco1, JLabel sco2, int p) {
        super(sco1, sco2, p);

        // adds KeyListeners for player two
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {if (!player2.getAlive()) {
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                player2.setVelocityX(-velocity2);
                player2.setVelocityY(0);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                player2.setVelocityX(velocity2);
                player2.setVelocityY(0);
            } else if (e.getKeyCode() == KeyEvent.VK_W) {
                player2.setVelocityY(-velocity2);
                player2.setVelocityX(0);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                player2.setVelocityY(velocity2);
                player2.setVelocityX(0);
            } else if (e.getKeyCode() == KeyEvent.VK_E) {
                if (velocity2 >1) {
                    velocity2++;
                    if (player2.getVelocityX() > 0) {
                        player2.setVelocityX(velocity2);
                    } else if (player2.getVelocityX() < 0) {
                        player2.setVelocityX(-velocity2);
                    } else if (player2.getVelocityY() > 0) {
                        player2.setVelocityY(velocity2);
                    } else if (player2.getVelocityY() < 0) {
                        player2.setVelocityY(-velocity2);
                    }
                }
                //speed down
            } else if (e.getKeyCode() == KeyEvent.VK_F){
                if (velocity2 >1 ) {
                    velocity2--;
                    if (player2.getVelocityX() > 0) {
                        player2.setVelocityX(-velocity2);
                    } else if (player2.getVelocityX() < 0) {
                        player2.setVelocityX(velocity2);
                    } else if (player2.getVelocityY() > 0) {
                        player2.setVelocityY(-velocity2);
                    } else if (player2.getVelocityY() < 0) {
                        player2.setVelocityY(velocity2);
                    }
                }}
            }
            public void keyReleased(KeyEvent e) {
            }
        });
    }


    public void setPlayer1name(String player1name){
        this.player1name = player1name;
    }
    public void setPlayer2name(String player2name){
        this.player2name = player2name;
    }

    public String getPlayer1name() {
        return player1name;
    }

    public String getPlayer2name() {
        return player2name;
    }

    public int getPlayer1score(){
        return i;
    }
    public int getPlayer2score(){
        return j;
    }
    public void setPlayerOneColor(Color color1) {
        this.playerOneColor = color1;
    }

    public void setPlayerTwoColor(Color color2) {
        this.playerTwoColor = color2;
    }
    // moves both players and checks if they crash
    void tick() {
        player1.setBounds(getWidth(), getHeight());
        player1.move();
        player2.setBounds(getWidth(), getHeight());
        player2.move();
        for (Player k1: players) {
            for (Player k2: players) {
                k1.crash(k1.intersects(k2));
            }
        }
        if (!player1.getAlive() || !player2.getAlive()) {
            timer.stop();
            run = false;
            addScore();
        }
        setScore();
        repaint();

    }

    // restarts the score if the game is exited
    public void restartGame() {
        i = 0;
        j = 0;
    }

    // sets the players' scores and displays the boost left in game
    public void setScore() {

        score1.setText
                (" " + getPlayer1name() + ": " + i );
        score2.setText
                (" " + getPlayer2name() + ": " + j );
    }

    // initializes all players and restarts the timer
    public void reset() {
        p1 = false;
        p2 = false;
        tie = false;
        int[] start1 = getRandomStart1();
        player1 = new Player
                (start1[0], start1[1], start1[2], start1[3], playerOneColor, player1name);
        players[0] = player1;
        int[] start2 = getRandomStart2();
        player2 = new Player
                (start2[0], start2[1], start2[2], start2[3], playerTwoColor, player2name);
        players[1] = player2;
        timer.start();
        requestFocusInWindow();
    }

    public void setPlayer1Name(String playerName1){
        this.player1name = playerName1;
    }
    public void setPlayer2Name(String playerName2){
        this.player2name = playerName2;
    }

    public String getPlayer1Name() {
        return player1name;
    }

    public String getPlayer2Name() {
        return player2name;
    }

    // updates the scores after each round
    public void addScore() {
        if (!run) {
            if (player2.getAlive()) {
                p2 = true;
                j++;
            } else if (player1.getAlive()) {
                p1 = true;
                i++;
            } else {
                tie = true;
            }
        }
        addHighscores();
        score1.repaint();
        score2.repaint();
    }

    //add the highscore into a highscore list
    public void addHighscores(){
        if (j > highscores[0] || i > highscores[0] ) {
            if (j > i) {
                highscores[0] = j;
            } else if (i > j) {
                highscores[0] = i;
            }
        }else if (j > highscores[1] || i > highscores[1]) {
            if (j > i) {
                highscores[1] = j;
            } else if (i > j) {
                highscores[1] = i;
            }
        }else if (j > highscores[2] || i > highscores[2]) {
            if (j > i) {
                highscores[2] = j;
            } else if (i > j) {
                highscores[2] = i;
            }
        }else if (j > highscores[3] || i > highscores[3]) {
            if (j > i) {
                highscores[3] = j;
            } else if (i > j) {
                highscores[3] = i;
            }
        }else if (j > highscores[4] || i > highscores[4]) {
            if (j > i) {
                highscores[4] = j;
            } else if (i > j) {
                highscores[4] = i;
            }
        }
    }



    // draws the outcome of each match
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if (p1) {

            g.drawString(player1name + " wins!",MAPWIDTH / 2 - 50, MAPHEIGHT - 30);
        }
        if (p2) {
            g.drawString(player2name + " wins!",MAPWIDTH / 2 - 50, MAPHEIGHT - 30);
        }
        if (tie) {
            g.drawString("It's a tie!",MAPWIDTH / 2 - 50, MAPHEIGHT - 30);
        }
    }
}
