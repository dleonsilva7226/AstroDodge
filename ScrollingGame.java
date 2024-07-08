import java.awt.*;
import java.awt.event.*;
import java.util.*;

//The basic ScrollingGame, featuring Avoids, Gets, and SpecialGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class ScrollingGame extends GameEngine {
    
 
    
    //Starting Player coordinates
    protected static final int STARTING_PLAYER_X = 0;
    protected static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game
    protected static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    protected static final int MAX_GAME_SPEED = 500;
    //Interval that the speed changes when pressing speed up/down keys
    protected static final int SPEED_CHANGE = 20;    
    
    protected static final String INTRO_SPLASH_FILE = "assets/splash.gif";        
    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    protected static final int SPAWN_INTERVAL = 45;
    
    
    //A Random object for all your random number generation needs!
    protected static final Random rand = new Random();
    
            
    
    //Player's current score
    protected int score;
    
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    protected Player player;
    
    
    
    
    
    public ScrollingGame(){
        super();
    }
    
    public ScrollingGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void pregame(){
        this.setSplashImage(LeonSilvaGame.INTRO_SPLASH_SCREEN);
        this.setTitleText("AstroDodge");
        this.setBackgroundImage(LeonSilvaGame.BACKGROUND_IMAGE_FILE);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;
    }
    
    
    //Called on each game tick
    protected void updateGame(){
        //scroll all scrollable Entities on the game board
        scrollEntities();   
        gcOutOfWindowEntities();
        //Spawn new entities only at a certain interval
        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0){            
            spawnEntities();
        }
        //Update the title text on the top of the window
        setTitleText("HP: " + player.getHP() + ", Score: " + score + ", Level: " + LeonSilvaGame.currentLevel);        
    }
    
    
    //Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities(){
        for (int i = 0; i < displayList.size(); i++){
            if (displayList.get(i) instanceof Scrollable) {
                Scrollable scrollableObj = (Scrollable) displayList.get(i);
                scrollableObj.scroll();
            }
            if (displayList.get(i) instanceof Collectable) {
                if (player.isCollidingWith(displayList.get(i))) {
                    handlePlayerCollision((Collectable)displayList.get(i));
                    
                }
            }
        }
    }
    
    
    //Handles "garbage collection" of the displayList
    //Removes entities from the displayList that have scrolled offscreen
    //(i.e. will no longer need to be drawn in the game window).
    protected void gcOutOfWindowEntities(){
        int minXWindowCoordinate = 0;
        for (int i = 0; i < displayList.size(); i++) {
            if (displayList.get(i).getX() + displayList.get(i).getWidth() < minXWindowCoordinate) {
                displayList.remove(i);
            }
        }
       
    }
    
    
    //Called whenever it has been determined that the Player collided with a collectable
    protected void handlePlayerCollision(Collectable collidedWith){
        player.modifyHP(collidedWith.getDamage());
        score += collidedWith.getPoints();
        if (getGameSpeed() + collidedWith.getTimeBoost() >= 20 && getGameSpeed() + collidedWith.getTimeBoost() <= MAX_GAME_SPEED) {
            setGameSpeed(getGameSpeed() + collidedWith.getTimeBoost());
        }
    
        for (int i = 0; i < super.displayList.size(); i++) {
            if (super.displayList.get(i).equals((Entity) collidedWith)) {
                super.displayList.remove((Entity)collidedWith);
            }
        }
    }
    
    
    //Spawn new Entities on the right edge of the game window
    protected void spawnEntities(){
        int maxNumOfEntities = rand.nextInt(5);
        int index = 0;
        
        while (index < maxNumOfEntities) {
            addEntityToList();
            index++;
        }
    }
    
    
    //Called once the game is over, performs any end-of-game operations
    protected void postgame(){
        boolean playerLost = (player.getHP() == 0);
        if (playerLost) {
            super.setTitleText("GAME OVER - You Lose!");
        }
        else {
            super.setTitleText("Congratulations - You Won!");
        }
    }
    
    
    //Determines if the game is over or not
    //Game can be over due to either a win or lose state
    protected boolean determineIfGameIsOver(){
        
        boolean playerLost = (player.getHP() == 0);
        boolean playerWon = (score == SCORE_TO_WIN);
        int xSpawn = 0;
        int ySpawn = rand.nextInt(getWindowHeight() - Player.PLAYER_HEIGHT);
        //Occurs if the player loses
        if (playerLost) {
            this.setSplashImage(LeonSilvaGame.LOST_GAME_SPLASH_SCREEN);
            return true;
        }
        //Occurs if player has finished Level 4
        if ((playerWon && LeonSilvaGame.currentLevel == 4)) {
            this.setSplashImage(LeonSilvaGame.FINAL_SPLASH_SCREEN);
            return true;
        }
        //Occurs if player has finished Level 1
        if (playerWon && LeonSilvaGame.currentLevel == 1) {
            this.setSplashImage(LeonSilvaGame.L2_SPLASH_SCREEN);
            this.setTitleText("Level 2");
            LeonSilvaGame.currentLevel++;
            setGameSpeed(LeonSilvaGame.L2_GAME_SPEED);
            score = 0;
            displayList.clear();
            player.setHP(3);
            player.setX(xSpawn);
            player.setY(ySpawn);
            displayList.add(player);
        //Occurs if player has finished Level 2
        } else if (playerWon && LeonSilvaGame.currentLevel == 2) {
            this.setSplashImage(LeonSilvaGame.L3_SPLASH_SCREEN);
            this.setTitleText("Level 3");
            LeonSilvaGame.currentLevel++;
            score = 0;
            displayList.clear();
            player.setHP(3);
            setGameSpeed(LeonSilvaGame.L3_GAME_SPEED);
            player.setX(xSpawn);
            player.setY(ySpawn);
            displayList.add(player);
        //Occurs if player has finished Level 3
        } else if (playerWon && LeonSilvaGame.currentLevel == 3) {
            this.setSplashImage(LeonSilvaGame.L4_SPLASH_SCREEN);
            this.setTitleText("Level 4");
            LeonSilvaGame.currentLevel++;
            score = 0;
            displayList.clear();
            player.setHP(3);
            setGameSpeed(LeonSilvaGame.L4_GAME_SPEED);
            player.setX(xSpawn);
            player.setY(ySpawn);
            displayList.add(player);
        }
        return false;
       
    }
    
    
    
    //Reacts to a single key press on the keyboard
    protected void reactToKey(int key){
        
        
        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key) + ",  DisplayList size: " + displayList.size());
        //if a splash screen is active, only react to the "advance splash" key... nothing else!
        if (getSplashImage() != null){
            
            if (key == ADVANCE_SPLASH_KEY && getSplashImage().equals(LeonSilvaGame.INTRO_SPLASH_SCREEN)){
                this.setTitleText("Controls");
                super.setSplashImage(LeonSilvaGame.CONTROLS_SPLASH_SCREEN);
            }
            else if (key == ADVANCE_SPLASH_KEY && getSplashImage().equals(LeonSilvaGame.CONTROLS_SPLASH_SCREEN)) {
                this.setTitleText("Rules & Game Info");
                super.setSplashImage(LeonSilvaGame.RULES_GAME_INFO_SPLASH_SCREEN);
            }
            else if (key == ADVANCE_SPLASH_KEY && getSplashImage().equals(LeonSilvaGame.RULES_GAME_INFO_SPLASH_SCREEN)) {
                this.setTitleText("Level 1");
                super.setSplashImage(LeonSilvaGame.L1_SPLASH_SCREEN);
            }
            else if (key == ADVANCE_SPLASH_KEY && getSplashImage().equals(LeonSilvaGame.L1_SPLASH_SCREEN)) {
                super.setSplashImage(null);
            }
            else if (key == ADVANCE_SPLASH_KEY && getSplashImage().equals(LeonSilvaGame.L2_SPLASH_SCREEN)) {
                super.setSplashImage(null);   
            }
            else if (key == ADVANCE_SPLASH_KEY && getSplashImage().equals(LeonSilvaGame.L3_SPLASH_SCREEN)) {
                super.setSplashImage(null);
            }
            else if (key == ADVANCE_SPLASH_KEY && getSplashImage().equals(LeonSilvaGame.L4_SPLASH_SCREEN)) {
                super.setSplashImage(null);
                
                
            }
            
            return;
        }

        
        
        

        if (!isPaused) {
            int minYWindowCoordinate = 0;
            int maxYWindowCoordinate = getWindowHeight() - 1;
            int minXWindowCoordinate = 0;
            int maxXWindowCoordinate = getWindowWidth() - 1;
            if (key == UP_KEY) {
                if ((player.getY() - player.getMovementSpeed()) > minYWindowCoordinate) {
                    player.setY(player.getY() - player.getMovementSpeed());
                }
            } 
            else if (key == DOWN_KEY) {
                if ((player.getY() + player.getHeight() + player.getMovementSpeed()) < maxYWindowCoordinate) {
                    player.setY(player.getY() + player.getMovementSpeed());
                }
            }
            else if (key == RIGHT_KEY) {
                if (((player.getX() + player.getWidth() + player.getMovementSpeed())< maxXWindowCoordinate)){
                    player.setX(player.getX() + player.getMovementSpeed());
                }
            }
            else if (key == LEFT_KEY) {
                if ((player.getX() - player.getMovementSpeed()) > minXWindowCoordinate) {
                    player.setX(player.getX() - player.getMovementSpeed());
                }
            }
            int minSpeed = 20;
            if (key == GameEngine.SPEED_UP_KEY) {
                if (getGameSpeed() + SPEED_CHANGE <= MAX_GAME_SPEED) {
                    setGameSpeed(getGameSpeed() + SPEED_CHANGE);
                }
            } else if (key == GameEngine.SPEED_DOWN_KEY) {
                if (getGameSpeed() - SPEED_CHANGE >= minSpeed) {
                    setGameSpeed(getGameSpeed() - SPEED_CHANGE);
                }
            }
        }
        
        if (key == KEY_PAUSE_GAME) {
            if (isPaused) {
                isPaused = false;
            } else {
                isPaused = true;
            }
        } 
    }    
    
    
    //Handles reacting to a single mouse click in the game window
    //Won't be used in Milestone #2... you could use it in Creative Game though!
    protected MouseEvent reactToMouseClick(MouseEvent click){
        if (click != null){ //ensure a mouse click occurred
            int clickX = click.getX();
            int clickY = click.getY();
            setDebugText("Click at: " + clickX + ", " + clickY);
        }
        return click;//returns the mouse event for any child classes overriding this method
    }
    
    
    //**********Helper Functions**********//

    
    //Spawns a random entity depending on the chances provided in the parameters.
    //The avoid is the most likely to get spawned and the special get and the time
    //slower have the highest chance of spawning
    protected Entity getRandomEntity(double avoidChance, double getChance, double specialGetChance, double timeAccChance, double timeSlowChance) {
        double entityNum = rand.nextDouble();
        int maxXWindowCoordinate = getWindowWidth() - 1;
        if (entityNum < avoidChance) {
            int y = rand.nextInt(getWindowHeight() - Avoid.AVOID_HEIGHT); 
            return new Avoid(maxXWindowCoordinate,y);
        } else if (entityNum < (avoidChance + getChance)) {
            int y = rand.nextInt(getWindowHeight() - Get.GET_HEIGHT); 
            return new Get(maxXWindowCoordinate,y);
        } else if (entityNum < (avoidChance + getChance + timeAccChance)){
            int y = rand.nextInt(getWindowHeight() - TimeAccelerator.TIME_ACCELERATOR_HEIGHT); 
            return new TimeAccelerator(maxXWindowCoordinate,y);
        } else if (entityNum < (avoidChance + getChance + timeAccChance + specialGetChance)) {
            int y = rand.nextInt(getWindowHeight() - SpecialGet.GET_HEIGHT); 
            return new SpecialGet(maxXWindowCoordinate,y);
        } else {
            int y = rand.nextInt(getWindowHeight() - TimeSlower.TIME_SLOWER_HEIGHT); 
            return new TimeSlower(maxXWindowCoordinate,y);
        }
        
    }
    
    //Calls a random entity and checks to see if the entity produced is colliding
    //with any of the entities in displayList. If they are, another random entity
    //is given until the entity is no longer colliding with anything.
    protected void addEntityToList () {
        boolean collidingWithOtherEntity = false;
        
        while (!(collidingWithOtherEntity)) {
            Entity newEntity;
            if (LeonSilvaGame.currentLevel == 1) {
                newEntity = getRandomEntity(LeonSilvaGame.L1_AVOID_SPAWN_CHANCE, LeonSilvaGame.L1_GET_SPAWN_CHANCE, LeonSilvaGame.L1_SPECIAL_GET_SPAWN_CHANCE, LeonSilvaGame.L1_TIME_ACC_SPAWN_CHANCE, LeonSilvaGame.L1_TIME_SLOW_SPAWN_CHANCE);
            } else if (LeonSilvaGame.currentLevel == 2) {
                newEntity = getRandomEntity(LeonSilvaGame.L2_AVOID_SPAWN_CHANCE, LeonSilvaGame.L2_GET_SPAWN_CHANCE, LeonSilvaGame.L2_SPECIAL_GET_SPAWN_CHANCE, LeonSilvaGame.L2_TIME_ACC_SPAWN_CHANCE, LeonSilvaGame.L2_TIME_SLOW_SPAWN_CHANCE);
            } else if (LeonSilvaGame.currentLevel == 3) {
                newEntity = getRandomEntity(LeonSilvaGame.L3_AVOID_SPAWN_CHANCE, LeonSilvaGame.L3_GET_SPAWN_CHANCE, LeonSilvaGame.L3_SPECIAL_GET_SPAWN_CHANCE, LeonSilvaGame.L3_TIME_ACC_SPAWN_CHANCE, LeonSilvaGame.L3_TIME_SLOW_SPAWN_CHANCE);
            } else {
                newEntity = getRandomEntity(LeonSilvaGame.L4_AVOID_SPAWN_CHANCE, LeonSilvaGame.L4_GET_SPAWN_CHANCE, LeonSilvaGame.L4_SPECIAL_GET_SPAWN_CHANCE, LeonSilvaGame.L4_TIME_ACC_SPAWN_CHANCE, LeonSilvaGame.L4_TIME_SLOW_SPAWN_CHANCE);
            }
            
            int collidingCount = 0;
            for (int i = 0; i < super.displayList.size(); i++) {
                if (super.displayList.get(i).isCollidingWith(newEntity)) {
                    collidingCount++;
                }
                
            }
            if (collidingCount == 0) {
                super.displayList.add(newEntity);
                collidingWithOtherEntity = true;
            }
        }
    }
    
}
