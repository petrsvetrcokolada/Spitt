package cz.skylights.spitt;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.Display;;

// Micahl SVOBODA ... 2012
// engine class
//
public class SpatterEngine extends OptionsEngine {
	//
	private static long _startGame;
	public static long GameTime;
	
    // objekty
	// zvukove vlakno
    public static Thread soundThread;
    // display
    public static Display display;
    public static Context context;
    public static Resources resources;
     
    
    // zahajeni hry
    public static void Start()
    {
    	_startGame = System.currentTimeMillis();    	
    }
    
    // vrati herni cas
    // budeme podle toho cassovat hru
    public static long GetGameTime()
    {
    	GameTime = System.currentTimeMillis()-_startGame;
    	return GameTime;
    }
    
    public static long GameObjectTime(GameObject obj)
    {
    	return System.currentTimeMillis()-_startGame-obj.StartTime;
    }
    
    public boolean onExit(View v)
    {
    	try
    	{
    		Intent sound = new Intent(context, SpatterSound.class);
    		context.stopService(sound);
    		soundThread.stop();    		
    		return true;
    	}
    	catch(Exception e)
    	{    		
    		return false;
    	}
    }
    
}
