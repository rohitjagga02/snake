/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.util.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class Square extends GameObj {
    public static final int SIZE = 15;
    public static final int INIT_POS_X = 20;
    public static final int INIT_POS_Y = 20;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private Color color;
    // List of body square positions (each coordinate is the upper-left corner of body square)
    private LinkedList<CoordinatePair> bodyPositions;

    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public Square(int courtWidth, int courtHeight, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);

        this.color = color;
        this.bodyPositions = new LinkedList<CoordinatePair>();
        bodyPositions.add(new CoordinatePair(INIT_POS_X, INIT_POS_Y));
    }
    
    public LinkedList<CoordinatePair> getBodyPositions() {
        LinkedList<CoordinatePair> clone = new LinkedList<CoordinatePair>();
        for (CoordinatePair pair : bodyPositions) {
            clone.add(pair);
        }
        return clone;
    }
    
    public CoordinatePair getTailCoords() {
        CoordinatePair p = bodyPositions.peekLast();
        return p;
    }
    public CoordinatePair getHeadCoords() {
        CoordinatePair p = bodyPositions.peekFirst();
        return p;
    }
    
    public void addBodySquareFirst(int x, int y) {
        
        bodyPositions.addFirst(new CoordinatePair(x,y));
    }
    
    public void elongate() {
        int px = super.getPx();
        int py = super.getPy();
        int vx = super.getVx();
        int vy = super.getVy();
        
        bodyPositions.addFirst(new CoordinatePair(px + vx, py + vy));
        super.setPx(px + vx);
        super.setPy(py + vy);
        //clip();
    }
    
    
    //private void clip() {
        //super.setPx(Math.min(Math.max(super.getPx(), 0), super.getWidth() - SIZE));
        //super.setPy(Math.min(Math.max(super.getPy(), 0), super.getHeight() - SIZE));
    //}
    
    
    @Override
    public void move() {
        int px = super.getPx();
        int py = super.getPy();
        int vx = super.getVx();
        int vy = super.getVy();
        
        bodyPositions.addFirst(new CoordinatePair(px + vx, py + vy));
        bodyPositions.removeLast();
        
        super.setPx(px + vx);
        super.setPy(py + vy);

        //clip();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        
        // Draw the snake's body
        for (CoordinatePair p : bodyPositions) {
            g.fillRect(p.x(), p.y(), this.getWidth(), this.getHeight());
        }
        
    }

}



