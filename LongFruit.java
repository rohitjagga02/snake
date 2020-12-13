import java.awt.Color;

public class LongFruit extends Circle {
    
    public LongFruit(int courtWidth, int courtHeight) {
        
        super(courtWidth, courtHeight, Color.GREEN, 
                (int)(Math.random() * 161) + 20, (int)(Math.random() * 161) + 20, 
                0, 0);
        
    }

    @Override
    public void isEatenBy(Square snake) {
        snake.elongate();
        snake.elongate();
        snake.elongate();
        snake.elongate();
        snake.elongate();
        snake.elongate();
    }
    
}

