public class TimeSlower extends Entity implements Collectable, Scrollable{
    public static final String TIME_SLOWER_IMAGE_FILE = LeonSilvaGame.TIME_SLOWER_IMAGE_FILE;
    public static final int TIME_SLOWER_HEIGHT = 40;
    public static final int TIME_SLOWER_WIDTH = 40;
    public static final int TIME_SLOWER_SCROLL_SPEED = 5;
    
    public TimeSlower () {
        this(0,0);
    }

    public TimeSlower (int x, int y) {
        super(x,y, TIME_SLOWER_WIDTH, TIME_SLOWER_HEIGHT, TIME_SLOWER_IMAGE_FILE);
    }

    public int getScrollSpeed(){
        return TIME_SLOWER_SCROLL_SPEED;
    }
    
    //Move the TimeSlower left by the scroll speed
    public void scroll(){
        setX(getX() - TIME_SLOWER_SCROLL_SPEED);
    }
    
    //Colliding with a TimeAccelerator increases the speed by the default amount
    public int getPoints(){
       return 0;
    }
    
    //Colliding with a TimeAccelerator does not affect the Player
    public int getDamage(){
        return 0;
    }

    public int getTimeBoost() {
        return -20;
    }
}
