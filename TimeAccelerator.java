public class TimeAccelerator extends Entity implements Collectable, Scrollable{
    //Add a time boost function to the Collectable Interface to make the function make more sense
    public static final String TIME_ACCELERATOR_IMAGE_FILE = LeonSilvaGame.TIME_ACCELERATOR_IMAGE_FILE;
    public static final int TIME_ACCELERATOR_HEIGHT = 80;
    public static final int TIME_ACCELERATOR_WIDTH = 80;
    public static final int TIME_ACCELERATOR_SCROLL_SPEED = 5;
    
    public TimeAccelerator () {
        this(0,0);
    }

    public TimeAccelerator (int x, int y) {
        super(x,y, TIME_ACCELERATOR_WIDTH, TIME_ACCELERATOR_HEIGHT, TIME_ACCELERATOR_IMAGE_FILE);
    }

    public int getScrollSpeed(){
        return TIME_ACCELERATOR_SCROLL_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() - TIME_ACCELERATOR_SCROLL_SPEED);
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
        return 20;
    }
}
