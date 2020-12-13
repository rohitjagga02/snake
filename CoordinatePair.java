
public class CoordinatePair implements Comparable<CoordinatePair> {
    
    private int x;
    private int y;
    
    public CoordinatePair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }
    
    public void setX(int newX) {
        this.x = newX;
    }
    public void setY(int newY) {
        this.y = newY;
    }
    
    @Override
    public boolean equals(Object o) {
        return (this.x == ((CoordinatePair) o).x() && this.y == ((CoordinatePair) o).y());
    }
    
    @Override
    public int compareTo(CoordinatePair o) {
        // TODO Auto-generated method stub
        return (o.x() - this.x() + o.y() - this.y());
    }
    
}