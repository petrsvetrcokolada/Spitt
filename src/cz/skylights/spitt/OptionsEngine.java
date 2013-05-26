package cz.skylights.spitt;

import cz.skylights.spitt.R;

public class OptionsEngine {
	// parametry enginu
    public static final int start_menu_delay = 4000;
    // hudba
    public static final int volume = 100;    
    public static final int menu_sound = R.raw.mix1;     
    public static final boolean loop_background_music = true;
    public static final float scroll_bg1 = 0.002f;
    public static final float scroll_bg2 = 0.0035f;
    public static final int fps_thread_sleep = (1000/50); 
    public static float screen_ratio = 1f;
    // textures
    public static final int space_background=R.drawable.pozadi1;
    public static final int space_stars1=R.drawable.stars2;
    public static final int space_stars2=R.drawable.stars1;
    public static final int sprite_live = R.drawable.live;
    public static final int explose_animation = R.drawable.explose;
    public static final int explose_animation1 = R.drawable.explose1;
    public static final int explose_animation2 = R.drawable.explose2;
    public static final int particle = R.drawable.particle;
    public static final int star = R.drawable.star;
    // lev1 - background
    public static final int level1a = R.drawable.level1a;
    public static final int level1b = R.drawable.level1b;
    public static final int level1c = R.drawable.level1c;
    public static final int level1d = R.drawable.level1d;
    public static final int level1e = R.drawable.level1e;
    // text
    public static final int text_characters = R.drawable.arial;
    // player
    public static Player Player;
    public static final int PLAYER_SHIP = R.drawable.sm;
    public static final int PLAYER_BULLET = R.drawable.shoot2;
    public static final int PLAYER_LEFT = 1; 
    public static final int PLAYER_RELEASE = 0; 
    public static final int PLAYER_RIGHT = 2;
    public static final int PLAYER_UP = 1;
    public static final int PLAYER_DW = -1;
    public static final int PLAYER_FRAMES_BETWEEN_ANI = 9; 
    public static final float PLAYER_BANK_SPEED = .1f;
    // fire
    public static final int fire_count = 24;
    public static final float bullet_speed = .01f;
    public static final int bullet_timeout = 250;
    //public static float playerX=1.75f;
    public static final int lives=3;
    // enemy
    public static final int enemy=R.drawable.enemy;
    public static final int enemy1=R.drawable.enemy1;
    
    public static float playerY=0.3f;
    public static float scaleX = .25f;
    static float scaleY = .25f;
    public static boolean onTouch = false;    
}
