package cyra.trongame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends Component implements Runnable {
    private String gameRequest = "LightCycles game request to start";

    //set the name of the players
    public String player1name, player2name;
    // Client Server
    private GameClient socketClient;

    // true if the instructions page is in the frame
    private boolean instructOn = false;
    // true if the high scores are displayed in the frame
    private boolean scoresOn = false;

    public void run() {
        // Top-level frame
        final JFrame frame = new JFrame("LightCycles");
        frame.setBackground(Color.BLACK);
        frame.setPreferredSize(new Dimension(500,560));
        frame.setLocation(400, 100);
        frame.setResizable(false);

        /**Main Menu**/

        // outside panel
        final JPanel outside = new JPanel();
        outside.setLayout(new BorderLayout());
        outside.setBackground(Color.BLACK);


        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel east = new JPanel();
        JPanel west = new JPanel();

        JLabel title = new JLabel();

        // main menu panel
        final JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(3,1, 10, 10));
        mainMenu.setBackground(Color.BLACK);

        mainMenu.add(title);
        title.setText("LightCycles!");
        title.setBackground(Color.BLACK);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.PLAIN, 50));
        title.setHorizontalAlignment(JLabel.CENTER);

        // buttons for main menu
        final JButton play = new JButton("Play");
        mainMenu.add(play);

        final JButton quit = new JButton("Quit");
        mainMenu.add(quit);

        outside.add(mainMenu, BorderLayout.CENTER);
        outside.add(west, BorderLayout.WEST);
        outside.add(south, BorderLayout.SOUTH);
        outside.add(east, BorderLayout.EAST);
        outside.add(north, BorderLayout.NORTH);



        north.setBackground(Color.BLACK);
        south.setBackground(Color.BLACK);
        east.setBackground(Color.BLACK);
        west.setBackground(Color.BLACK);



        west.setPreferredSize(new Dimension(100, 200));
        south.setPreferredSize(new Dimension(200, 200));
        east.setPreferredSize(new Dimension(100, 200));
        north.setPreferredSize(new Dimension(200, 100));

        // adds main menu panel to the frame
        frame.add(outside);



        // two players

        // panel that holds the buttons and labels for two-player mode
        final JPanel twoMenu = new JPanel();
        twoMenu.setLayout(new GridLayout(1,2));
        twoMenu.setBackground(Color.BLACK);

        final JPanel bottomGameMenu = new JPanel();
        bottomGameMenu.setLayout(new GridLayout(1,2));
        twoMenu.setBackground(Color.BLACK);


        // the score labels for each player
        final JLabel scoreTwo1 = new JLabel("   Player 1: 0");
        scoreTwo1.setForeground(Color.WHITE);
        scoreTwo1.setBackground(Color.BLACK);
        twoMenu.add(scoreTwo1);
        final JLabel scoreTwo2 = new JLabel("   Player 2: 0");
        scoreTwo2.setForeground(Color.WHITE);
        scoreTwo2.setBackground(Color.BLACK);
        twoMenu.add(scoreTwo2);

        // the reset and main menu buttons for two-player mode
        final JButton resetTwo = new JButton("Restart");
        bottomGameMenu.add(resetTwo);
        final JButton exitTwo = new JButton("Back");
        bottomGameMenu.add(exitTwo);

        // the two-player level
        final TwoPlayerMap levelTwoPlayer =
                new TwoPlayerMap(scoreTwo1, scoreTwo2, 2);
        levelTwoPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // action listeners
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                player1name = JOptionPane.showInputDialog("Enter the name of the player 1");
                player2name = JOptionPane.showInputDialog("Enter the name of the player 2");
                frame.remove(outside);
                frame.setLayout(new BorderLayout());
                frame.add(levelTwoPlayer, BorderLayout.CENTER);
                frame.add(twoMenu, BorderLayout.NORTH);
                frame.add(bottomGameMenu, BorderLayout.SOUTH);
                frame.update(frame.getGraphics());
                levelTwoPlayer.requestFocusInWindow();
                levelTwoPlayer.revalidate();
                levelTwoPlayer.reset();
                levelTwoPlayer.setPlayer1name(player1name);
                levelTwoPlayer.setPlayer2name(player2name);
            }
        });


        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });


        resetTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                levelTwoPlayer.reset();
            }
        });

        exitTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(levelTwoPlayer);
                frame.remove(twoMenu);
                frame.remove(bottomGameMenu);
                frame.add(outside);
                frame.update(frame.getGraphics());
                outside.revalidate();
                levelTwoPlayer.restartGame();
            }
        });

        // put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // start the game running
        levelTwoPlayer.reset();
    }

    // Start game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
