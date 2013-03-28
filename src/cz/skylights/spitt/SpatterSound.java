package cz.skylights.spitt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

// Sound service pro hru
public class SpatterSound extends Service {
	public static boolean isRunning = false;
	MediaPlayer player;
	
	
	@Override
	public IBinder onBind(Intent intent) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		setMusicOptions(this, SpatterEngine.loop_background_music, SpatterEngine.volume, SpatterEngine.menu_sound);
	}
	
	public void setMusicOptions(Context c, boolean looping, int volume, int soundId)
	{
		player = MediaPlayer.create(c, soundId);
		player.setLooping(looping);
		player.setVolume(volume, volume);
	}
	
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		try
		{
			player.start();
			isRunning=true;
		}
		catch(Exception e)
		{
			isRunning=false;
			player.stop();
		}
		return 1;
	}
	
	public void onStart(Intent intent, int startId)
	{
	 
	}
	
	public void onStop()
	{
		isRunning=false;
	}
	
	public IBinder onUnBind(Intent arg0)
	{
		return null;
			
	}
	
	
	public void onPause()
	{
		player.pause();
	}
	
	public void onDestroy()
	{
		player.stop();
		player.release();
	}
	
	public void onLowMemory()
	{
		player.stop();
	}

}
