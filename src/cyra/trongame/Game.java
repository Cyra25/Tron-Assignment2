package cyra.trongame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.Map;

public class Game extends Component implements Runnable {
    private String gameRequest = "LightCycles game request to start";

    // for highscores
    private final String defaultString = "Nil - 0";

    public void run() {
        // Top-level frame
        final JFrame frame = new JFrame("LightCycles");
        frame.setBackground(Color.BLACK);
        frame.setPreferredSize(new Dimension(500,560));
        frame.setLocation(400, 100);
        frame.setResizable(false);

        // outside panel
        final JPanel outside = new JPanel();
        outside.setLayout(new BorderLayout());
        outside.setBackground(Color.BLACK);


        JPanel center = new JPanel();
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel east = new JPanel();
        JPanel west = new JPanel();

        // highscores display
        JLabel highscore1 = new JLabel();
        JLabel highscore2 = new JLabel();
        JLabel highscore3 = new JLabel();
        JLabel highscore4 = new JLabel();
        JLabel highscore5 = new JLabel();

        JLabel[] highscoresDisplayList = new JLabel[]{highscore1, highscore2, highscore3, highscore4, highscore5};

        // instructions
        JLabel instructions1Player1 = new JLabel();
        JLabel instructions1Player2 = new JLabel();
        JLabel instructions2Player1 = new JLabel();
        JLabel instructions2Player2 = new JLabel();
        JLabel instructions3Player1 = new JLabel();
        JLabel instructions3Player2 = new JLabel();


        JLabel title = new JLabel();
        JLabel subTitle = new JLabel();

        // main menu panel
        final JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(1,2, 10, 10));
        mainMenu.setBackground(Color.BLACK);

        title.setText("Tron!");
        title.setBackground(Color.BLACK);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.PLAIN, 40));
        title.setHorizontalAlignment(JLabel.CENTER);

        subTitle.setText("High Scores");
        subTitle.setBackground(Color.BLACK);
        subTitle.setForeground(Color.WHITE);
        subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subTitle.setHorizontalAlignment(JLabel.CENTER);

        highscore1.setHorizontalAlignment(JLabel.CENTER);
        highscore2.setHorizontalAlignment(JLabel.CENTER);
        highscore3.setHorizontalAlignment(JLabel.CENTER);
        highscore4.setHorizontalAlignment(JLabel.CENTER);
        highscore5.setHorizontalAlignment(JLabel.CENTER);

        // instructions
        instructions1Player1.setText("Player 1 Controls:");
        instructions2Player1.setText("Up = Up Arrow, Down = Down Arrow, Left = Left Arrow, Right = Right Arrow");
        instructions3Player1.setText("Accelerate/Decelerate = N/M, Jet Wall On/Off = Space");

        instructions1Player2.setText("Player 2 Controls:");
        instructions2Player2.setText("Up = W, Down = S, Left = A, Right = D");
        instructions3Player2.setText("Accelerate/Decelerate = E/F, Jet Wall On/Off = Q");

        instructions1Player1.setForeground(Color.WHITE);
        instructions2Player1.setForeground(Color.WHITE);
        instructions3Player1.setForeground(Color.WHITE);

        instructions1Player2.setForeground(Color.WHITE);
        instructions2Player2.setForeground(Color.WHITE);
        instructions3Player2.setForeground(Color.WHITE);


        // high scores
        handleScores(highscoresDisplayList);


        // buttons for main menu
        final JButton play = new JButton("Play");
        mainMenu.add(play);

        final JButton quit = new JButton("Exit");
        mainMenu.add(quit);

        outside.add(center, BorderLayout.CENTER);
        outside.add(west, BorderLayout.WEST);
        outside.add(south, BorderLayout.SOUTH);
        outside.add(east, BorderLayout.EAST);
        outside.add(north, BorderLayout.NORTH);

        north.setBackground(Color.BLACK);
        south.setBackground(Color.BLACK);
        east.setBackground(Color.BLACK);
        west.setBackground(Color.BLACK);
        center.setBackground(Color.BLACK);

        south.add(mainMenu);

        center.setLayout(new GridLayout(12,1));


        center.add(highscore1);
        highscore1.setForeground(Color.WHITE);
        center.add(highscore2);
        highscore2.setForeground(Color.WHITE);
        center.add(highscore3);
        highscore3.setForeground(Color.WHITE);
        center.add(highscore4);
        highscore4.setForeground(Color.WHITE);
        center.add(highscore5);
        highscore5.setForeground(Color.WHITE);

        center.add(instructions1Player1);
        center.add(instructions2Player1);
        center.add(instructions3Player1);
        center.add(instructions1Player2);
        center.add(instructions2Player2);
        center.add(instructions3Player2);



        north.setLayout(new GridLayout(2, 1));
        north.add(title);
        north.add(subTitle);

        west.setPreferredSize(new Dimension(25, 300));
        south.setPreferredSize(new Dimension(200, 50));
        east.setPreferredSize(new Dimension(25, 100));
        north.setPreferredSize(new Dimension(200, 120));

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
        final JButton exitTwo = new JButton("End Game");
        bottomGameMenu.add(exitTwo);

        // the two-player level
        final TwoPlayerMap levelTwoPlayer =
                new TwoPlayerMap(scoreTwo1, scoreTwo2, 2);
        levelTwoPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // action listeners
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // set usernames
                boolean validUsers = false;
                String player1name, player2name;

                while(!validUsers){
                    player1name = JOptionPane.showInputDialog("Enter Player 1 user name", null);
                    player2name = JOptionPane.showInputDialog("Enter Player 2 user name", null);

                    ColorChoosing playerOneColor = new ColorChoosing(1);
                    ColorChoosing playerTwoColor = new ColorChoosing(2);

                    if(player1name.equals("") || player2name.equals("")){
                        JOptionPane.showMessageDialog(null,"Usernames cannot be empty");
                    }

                    else if(player1name.equalsIgnoreCase(player2name)){
                        JOptionPane.showMessageDialog(null,"Player 1 and Player 2 must have different user names.");
                    }

                    else if(playerOneColor.getColorSelected() == playerTwoColor.getColorSelected()){
                        JOptionPane.showMessageDialog(null,"Player 1 and Player 2 must have different colors");


                    }

                    else{
                        validUsers = true;
                        levelTwoPlayer.setPlayer1name(player1name);
                        levelTwoPlayer.setPlayer2name(player2name);
                        levelTwoPlayer.setPlayerOneColor(playerOneColor.getColorSelected());
                        levelTwoPlayer.setPlayerTwoColor(playerTwoColor.getColorSelected());
                    }
                }


                frame.remove(outside);
                frame.setLayout(new BorderLayout());
                frame.add(levelTwoPlayer, BorderLayout.CENTER);
                frame.add(twoMenu, BorderLayout.NORTH);
                frame.add(bottomGameMenu, BorderLayout.SOUTH);
                frame.update(frame.getGraphics());
                levelTwoPlayer.requestFocusInWindow();
                levelTwoPlayer.revalidate();
                levelTwoPlayer.reset();
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

                Map<Integer, String> highscoresDict = new HashMap<Integer, String>();

                // high scores
                FileReader fr;
                try {
                    fr = new FileReader("highscoresfile.txt");
                    BufferedReader ReadFileBuffer = new BufferedReader(fr);
                    File highscoresFile = new File("highscoresfile.txt");

                    for (int i = 0; i < 5; ++i) {
                        String scoredata = ReadFileBuffer.readLine();
                        if(scoredata!=null){
                            if(!scoredata.equals("null")){
                                String[] scoreDataParts = scoredata.split(" - ");
                                String part1 = scoreDataParts[0];
                                String part2 = scoreDataParts[1];

                                highscoresDict.put(Integer.parseInt(part1), part2);
                            }
                        }

                    }

                    //Close the Readers
                    ReadFileBuffer.close();

                } catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }



                highscoresDict.put(levelTwoPlayer.getPlayer1score(), levelTwoPlayer.getPlayer1name());
                highscoresDict.put(levelTwoPlayer.getPlayer2score(), levelTwoPlayer.getPlayer2name());

                Map<Integer, String> sortedHighscoresDict = new TreeMap<Integer, String>(highscoresDict);


                String highscoresString = "";

                for (int i = 0; i < 5; ++i) {
                    highscoresString += ((TreeMap<Integer, String>) sortedHighscoresDict).pollLastEntry() + "\n";
                }
                highscoresString = highscoresString.replace("="," - ");

                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter("highscoresfile.txt"));
                    writer.write(highscoresString );
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                frame.remove(levelTwoPlayer);
                frame.remove(twoMenu);
                frame.remove(bottomGameMenu);
                frame.add(outside);
                frame.update(frame.getGraphics());

                // high scores
                handleScores(highscoresDisplayList);

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

    public void handleScores(JLabel[] highscoresDisplayList) {
        FileReader fr = null;
        try {
            fr = new FileReader("highscoresfile.txt");
            BufferedReader ReadFileBuffer = new BufferedReader(fr);
            File highscoresFile = new File("highscoresfile.txt");
            for (int i = 0; i < 5; ++i) {
                highscoresDisplayList[i].setText(ReadFileBuffer.readLine());
            }
            //Close the Readers
            ReadFileBuffer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Start game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}