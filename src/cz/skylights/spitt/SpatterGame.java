package cz.skylights.spitt;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class SpatterGame extends Activity {
	private GameGLView gameView;
	
	public void onCreate(Bundle save)
	{
		_lastx = -1;
		
		super.onCreate(save);
		gameView = new GameGLView(this);
		setContentView(gameView);
		Start();
	}
	
	// nastartovani hry
	public void Start()
	{
		Resources resources = this.getResources();
		SpatterEngine.resources = resources;
		gameView.loadLevelXML();
		SpatterEngine.Start();
	}
	
	public void Stop()
	{
		//
	}
	
    @Override 
    protected void onResume() { 
        super.onResume(); 
        gameView.onResume(); 
    } 

    @Override 
    protected void onPause() { 
        super.onPause(); 
        gameView.onPause(); 
    } 
	
    private float _lastx;
    private float _lasty;
    public boolean onTouchEvent(MotionEvent event)
    {
     	float x = event.getX();
    	float y = event.getY();
    	boolean nomove = false;
    	
    	float rozdilx=Math.abs(_lastx - x);
    	
    	//LogFile.Write("Rozdil:"+rozdilx);
    	if (_lastx > 0 && rozdilx <= 3.0f)
    		nomove = true;
    	    	
    	float fl_x = (float)x/(float)SpatterEngine.display.getWidth(); // /SpatterEngine.scaleX;
    	float fl_y = SpatterEngine.screen_ratio * (1-(float)y/(float)SpatterEngine.display.getHeight()); // /SpatterEngine.scaleY;    	
    	//
    	Float fl1 = new Float(fl_x);
    	Float fl2 = new Float(fl_y);
    	Float fl_playerx = new Float(SpatterEngine.Player.X+SpatterEngine.scaleX);
    	Float fl_playery = new Float(SpatterEngine.Player.Y);        	
    	
    	//LogFile.Write("Screen ratio:"+SpatterEngine.screen_ratio);
    	//LogFile.Write("Spatter touch. X="+fl1.toString()+";Y="+fl2.toString()+"; plX="+fl_playerx.toString()+"; plY="+fl_playery.toString());

		switch(event.getAction())
		{
    		case MotionEvent.ACTION_DOWN:
    			if (nomove == false)
    				SpatterEngine.Player.moveTo(fl_x,fl_y);
    			
    			SpatterEngine.onTouch = true;
    			break;
    		case MotionEvent.ACTION_MOVE:
    			if (nomove == false)
    				SpatterEngine.Player.moveTo(fl_x,fl_y);    			
    			
    			SpatterEngine.onTouch = true;
    			break;
    		case MotionEvent.ACTION_UP:
    			SpatterEngine.Player.playerXState = SpatterEngine.PLAYER_RELEASE;
    			SpatterEngine.Player.playerYState = SpatterEngine.PLAYER_RELEASE;
    			SpatterEngine.onTouch = false;
    			break;	    		
		}
    	
		_lastx = x;
		_lasty = y;
    	
    	return false;
    }
	
}
