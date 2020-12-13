import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.LinkedList;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {
    
    @Test
    public void testNewSnake() {
        Square snake = new Square(300, 300, Color.BLACK);
        CoordinatePair p = snake.getHeadCoords();
        CoordinatePair p2 = snake.getTailCoords();
        
        assertEquals(p, p2);
    }
    
    @Test
    public void testSnakeElongate() {
        Square snake = new Square(300, 300, Color.BLACK);
        snake.elongate();
        snake.elongate();
        snake.elongate();
        int num = snake.getBodyPositions().size();
        
        assertEquals(num, 4);
    }
    
    @Test
    public void testSnakeBodyEncapsulation() {
        Square snake = new Square(300, 300, Color.BLACK);
        LinkedList<CoordinatePair> snakebody = snake.getBodyPositions();
        // snake's field for bodyPositions should remain unchanged by the following
        // three lines
        snakebody.addLast(new CoordinatePair(1,1));
        snakebody.addLast(new CoordinatePair(1,2));
        snakebody.addLast(new CoordinatePair(1,3));
        
        int num = snake.getBodyPositions().size();
        assertEquals(num, 1);
    }
    
    @Test
    public void testFruitIsEatenBySnake() {
        Square snake = new Square(300, 300, Color.BLACK);
        // snake's field for bodyPositions should remain unchanged by the following
        // three lines
        LongFruit fruit = new LongFruit(300, 300);
        fruit.isEatenBy(snake); // this method should elongate snake 6 times
        
        int num = snake.getBodyPositions().size();
        assertEquals(num, 7);
    }
    
    @Test
    public void testSnakeMoveMethodOverridesCorrectly() {
        Square snake = new Square(300, 300, Color.BLACK);
        snake.addBodySquareFirst(0, 0);
        snake.addBodySquareFirst(0, 1);
        
        int num1 = snake.getBodyPositions().size();
        snake.move();
        
        int num2 = snake.getBodyPositions().size();
        assertEquals(num1, num2);
        assertEquals(3, num1);
    }
    

}
