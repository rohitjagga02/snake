/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
//import java.util.Scanner;

import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Square square; // the Black Square, keyboard control
    private Circle snitch; // the Golden Snitch, bounces
    //private Poison poison; // the Poison Mushroom, doesn't move

    private boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    
    // Game score
    private int gameScore = 0;
    private int fruitScore = 0;
    
    // Store high scores as list
    private int[] scoresList = new int[5];
    private int[] fruitNumsList = new int[5];

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 300;
    public static final int SQUARE_VELOCITY = 4;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!
        
        

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && square.getVx() == 0) {
                    square.setVx(-SQUARE_VELOCITY);
                    square.setVy(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && square.getVx() == 0) {
                    square.setVx(SQUARE_VELOCITY);
                    square.setVy(0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && square.getVy() == 0) {
                    square.setVy(SQUARE_VELOCITY);
                    square.setVx(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP && square.getVy() == 0) {
                    square.setVy(-SQUARE_VELOCITY);
                    square.setVx(0);
                }
            }

            //public void keyReleased(KeyEvent e) {
                //square.setVx(0);
                //square.setVy(0);
            //s}
        });

        this.status = status;
    }
    
    public void writeScores() {
        // Setting up process for writing high-scores to file
        File file = Paths.get("files/Highscores.txt").toFile();
        BufferedWriter bw = null; 
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file, false));
            //bw.write("Score | # of Fruits Eaten");
            //bw.newLine();
            for (int i = 0; i < 5; i++) {
                bw.write(String.valueOf(scoresList[i]) + 
                        " " + String.valueOf(fruitNumsList[i]) + "\n");
            }
            bw.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String readHighScores() {
        String filePath = "files/Highscores.txt";
        BufferedReader br = null;
        String content = "";
        try {
            br = new BufferedReader(new FileReader(filePath));
            //br.readLine();
            for (int i = 0; i < 5; i++) {
                String[] values = br.readLine().split(" ");
                scoresList[i] = Integer.parseInt(values[0]);
                fruitNumsList[i] = Integer.parseInt(values[1]);
            }
        } catch (FileNotFoundException f) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
        
    }
    
    public void reorderHighScores(int newScore, int newFruitNum) {
        readHighScores();
        int[] newScoresList = new int[5];
        int[] newFruitList = new int[5];
        
        if (newScore >= scoresList[0]) {
            newScoresList[0] = newScore;
            newFruitList[0] = newFruitNum;
            for (int i = 1; i < 5; i++) {
                newScoresList[i] = scoresList[i - 1];
                newFruitList[i] = fruitNumsList[i - 1];
            }
        } else if (newScore >= scoresList[1]) {
            newScoresList[0] = scoresList[0];
            newFruitList[0] = fruitNumsList[0];
            newScoresList[1] = newScore;
            newFruitList[1] = newFruitNum;
            for (int i = 2; i < 5; i++) {
                newScoresList[i] = scoresList[i - 1];
                newFruitList[i] = fruitNumsList[i - 1];
            }
        } else if (newScore >= scoresList[2]) {
            newScoresList[0] = scoresList[0];
            newFruitList[0] = fruitNumsList[0];
            newScoresList[1] = scoresList[1];
            newFruitList[1] = fruitNumsList[1];
            newScoresList[2] = newScore;
            newFruitList[2] = newFruitNum;
            for (int i = 3; i < 5; i++) {
                newScoresList[i] = scoresList[i - 1];
                newFruitList[i] = fruitNumsList[i - 1];
            }
        } else if (newScore >= scoresList[3]) {
            newScoresList[0] = scoresList[0];
            newFruitList[0] = fruitNumsList[0];
            newScoresList[1] = scoresList[1];
            newFruitList[1] = fruitNumsList[1];
            newScoresList[2] = scoresList[2];
            newFruitList[2] = fruitNumsList[2];
            newScoresList[3] = newScore;
            newFruitList[3] = newFruitNum;
            for (int i = 4; i < 5; i++) {
                newScoresList[i] = scoresList[i - 1];
                newFruitList[i] = fruitNumsList[i - 1];
            }
        } else if (newScore >= scoresList[4]) {
            newScoresList[0] = scoresList[0];
            newFruitList[0] = fruitNumsList[0];
            newScoresList[1] = scoresList[1];
            newFruitList[1] = fruitNumsList[1];
            newScoresList[2] = scoresList[2];
            newFruitList[2] = fruitNumsList[2];
            newScoresList[3] = scoresList[3];
            newFruitList[3] = fruitNumsList[3];
            newScoresList[4] = newScore;
            newFruitList[4] = newFruitNum;
        } else {
            newScoresList = scoresList.clone();
            newFruitList = fruitNumsList.clone();
        }
        this.scoresList = newScoresList;
        this.fruitNumsList = newFruitList;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        square = new Square(COURT_WIDTH, COURT_HEIGHT, Color.BLACK);
        //poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
        
        snitch = null;
        double randNum = Math.random();
        if (randNum >= 0.5) {
            snitch = new HotPepperFruit(COURT_WIDTH, COURT_HEIGHT);
        } else {
            snitch = new LongFruit(COURT_WIDTH, COURT_HEIGHT);
        }
        
        playing = true;
        
        // write score to file if its in top 5 high scores
        this.reorderHighScores(gameScore, fruitScore);
        this.writeScores();
        
        // reset scores
        gameScore = 0;
        fruitScore = 0;
        status.setText("Playing game...   Score: " + gameScore + "   "
                + scoresList[0] + "&" + fruitNumsList[0] + "\n"
                + scoresList[1] + "&" + fruitNumsList[1] + "\n"
                + scoresList[2] + "&" + fruitNumsList[2] + "\n"
                + scoresList[3] + "&" + fruitNumsList[3] + "\n"
                + scoresList[4] + "&" + fruitNumsList[4] + "\n"
                + "      Instructions: Move the snake using up/down/right/left "
                + "and eat all the fruit. 20 points for the green fruit and 50 "
                + "for the red fruit. You lose when you hit the wall!");
        
        

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
            square.move();
            snitch.move();

            // make the snitch bounce off walls...
            snitch.bounce(snitch.hitWall());
            // ...and the mushroom
            //snitch.bounce(snitch.hitObj(poison));

            // check for the game end conditions
            if (square.hitWall() != null) {
                playing = false;
                status.setText("You lose!   Score: " + gameScore + "   "
                        + scoresList[0] + "&" + fruitNumsList[0] + "\n"
                        + scoresList[1] + "&" + fruitNumsList[1] + "\n"
                        + scoresList[2] + "&" + fruitNumsList[2] + "\n"
                        + scoresList[3] + "&" + fruitNumsList[3] + "\n"
                        + scoresList[4] + "&" + fruitNumsList[4] + "\n"
                        + "      Instructions: Move the snake using up/down/right/left "
                        + "and eat all the fruit. 20 points for the green fruit and 50 "
                        + "for the red fruit. You lose when you hit the wall!");
            } else if (square.intersects(snitch)) {
                snitch.isEatenBy(square);
                fruitScore += 1;
                if (snitch instanceof LongFruit) {
                    gameScore += 20;
                } else if (snitch instanceof HotPepperFruit) {
                    gameScore += 50;
                } 
                //playing = false;
                
                snitch = null;
                double randNum = Math.random();
                if (randNum >= 0.5) {
                    snitch = new HotPepperFruit(COURT_WIDTH, COURT_HEIGHT);
                } else {
                    snitch = new LongFruit(COURT_WIDTH, COURT_HEIGHT);
                }
                
                status.setText("Playing game...   Score: " + gameScore + "   "
                        + scoresList[0] + "&" + fruitNumsList[0] + "\n"
                        + scoresList[1] + "&" + fruitNumsList[1] + "\n"
                        + scoresList[2] + "&" + fruitNumsList[2] + "\n"
                        + scoresList[3] + "&" + fruitNumsList[3] + "\n"
                        + scoresList[4] + "&" + fruitNumsList[4] + "\n"
                        + "      Instructions: Move the snake using up/down/right/left "
                        + "and eat all the fruit. 20 points for the green fruit and 50 "
                        + "for the red fruit. You lose when you hit the wall!");
            } else if (square.intersectsSnakeBody(square)) {
                //playing = false;
                status.setText("Playing game...   Score: " + gameScore + "   "
                        + scoresList[0] + "&" + fruitNumsList[0] + "\n"
                        + scoresList[1] + "&" + fruitNumsList[1] + "\n"
                        + scoresList[2] + "&" + fruitNumsList[2] + "\n"
                        + scoresList[3] + "&" + fruitNumsList[3] + "\n"
                        + scoresList[4] + "&" + fruitNumsList[4] + "\n"
                        + "      Instructions: Move the snake using up/down/right/left "
                        + "and eat all the fruit. 20 points for the green fruit and 50 "
                        + "for the red fruit. You lose when you hit the wall!");
            } else if (snitch.intersectsSnakeBody(square)) {
                //square.elongate();
                snitch.isEatenBy(square);
                fruitScore += 1;
                if (snitch instanceof LongFruit) {
                    gameScore += 20;
                } else if (snitch instanceof HotPepperFruit) {
                    gameScore += 50;
                }
                
                
                snitch = null;
                double randNum = Math.random();
                if (randNum >= 0.5) {
                    snitch = new HotPepperFruit(COURT_WIDTH, COURT_HEIGHT);
                } else {
                    snitch = new LongFruit(COURT_WIDTH, COURT_HEIGHT);
                }
            }

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snitch.draw(g);
        square.draw(g);
        //poison.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}