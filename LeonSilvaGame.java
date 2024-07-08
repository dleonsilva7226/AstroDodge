import java.util.*;

public class LeonSilvaGame extends ScrollingGame {
    //Name: Daniel Leon Silva
    //Entity Image Files
    public static final String AVOID_IMAGE_FILE = "assets/avoid.png";
    public static final String GET_IMAGE_FILE = "assets/get.png";
    public static final String SPECIAL_GET_IMAGE_FILE = "assets/special_get.png";
    public static final String PLAYER_IMAGE_FILE = "assets/player.png";
    public static final String BACKGROUND_IMAGE_FILE = "assets/background.png";
    public static final String TIME_ACCELERATOR_IMAGE_FILE = "assets/time_accelerator.png";
    public static final String TIME_SLOWER_IMAGE_FILE = "assets/time_slower.png";
    public static final String INTRO_SPLASH_IMAGE_FILE = "assets/intro_splash.png";
    
    //Avoid Spawn Chances for Levels 1-4
    public static final double L1_AVOID_SPAWN_CHANCE = .30;
    public static final double L2_AVOID_SPAWN_CHANCE = .32;
    public static final double L3_AVOID_SPAWN_CHANCE = .35;
    public static final double L4_AVOID_SPAWN_CHANCE = .40;

    //Get Spawn Chances for Levels 1-4
    public static final double L1_GET_SPAWN_CHANCE = .20;
    public static final double L2_GET_SPAWN_CHANCE = .17;
    public static final double L3_GET_SPAWN_CHANCE = .15;
    public static final double L4_GET_SPAWN_CHANCE = .15;
    
    //Special Get Spawn Chances for Levels 1-4
    public static final double L1_SPECIAL_GET_SPAWN_CHANCE = .15;
    public static final double L2_SPECIAL_GET_SPAWN_CHANCE = .12;
    public static final double L3_SPECIAL_GET_SPAWN_CHANCE = .10;
    public static final double L4_SPECIAL_GET_SPAWN_CHANCE = .5;

    //Time Accelerator Spawn Chances for Levels 1-4
    public static final double L1_TIME_ACC_SPAWN_CHANCE = .25;
    public static final double L2_TIME_ACC_SPAWN_CHANCE = .27;
    public static final double L3_TIME_ACC_SPAWN_CHANCE = .30;
    public static final double L4_TIME_ACC_SPAWN_CHANCE = .35;
    
    //Time Slower Spawn Chances for Levels 1-4
    public static final double L1_TIME_SLOW_SPAWN_CHANCE = .15;
    public static final double L2_TIME_SLOW_SPAWN_CHANCE = .12;
    public static final double L3_TIME_SLOW_SPAWN_CHANCE = .10;
    public static final double L4_TIME_SLOW_SPAWN_CHANCE = .5;

    //Game Speed for Each Level
    public static final int L1_GAME_SPEED = 100;
    public static final int L2_GAME_SPEED = 150;
    public static final int L3_GAME_SPEED = 200;
    public static final int L4_GAME_SPEED = 250;


    //Current Level the Game is On
    public static int currentLevel = 1;


    //Replace these later with actual splash screen
    public static final String INTRO_SPLASH_SCREEN = "assets/intro.png";
    public static final String CONTROLS_SPLASH_SCREEN = "assets/controls.png";
    public static final String RULES_GAME_INFO_SPLASH_SCREEN = "assets/rules_game_info.png";
    public static final String LOST_GAME_SPLASH_SCREEN = "assets/lost_game.png";
    public static final String L1_SPLASH_SCREEN = "assets/level_1.png";
    public static final String L2_SPLASH_SCREEN = "assets/level_2.png";
    public static final String L3_SPLASH_SCREEN = "assets/level_3.png";
    public static final String L4_SPLASH_SCREEN = "assets/level_4.png";
    public static final String FINAL_SPLASH_SCREEN = "assets/last_screen.png";





    
}