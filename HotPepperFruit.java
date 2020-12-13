import java.awt.Color;

public class HotPepperFruit extends Circle {
    
    public HotPepperFruit(int courtWidth, int courtHeight) {
        
        super(courtWidth, courtHeight, Color.RED, 
                (int)(Math.random() * 161) + 20, (int)(Math.random() * 161) + 20, 
                (int)(Math.random() * 16) - 8, (int)(Math.random() * 16) - 8);
        
    }

    @Override
    public void isEatenBy(Square snake) {
        snake.elongate();
        snake.elongate();
        //int initialVx = snake.getVx();
        //int initialVy = snake.getVy();
        //snake.setVx(3 * initialVx);
        //snake.setVy(3 * initialVy);
    }
    
}