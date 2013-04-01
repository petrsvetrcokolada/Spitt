package cz.skylights.spitt;

import cz.skylights.spitt.R;
import cz.skylights.spitt.activity.SpittSettingsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SpatterMenu extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        
        
        SpatterEngine.soundThread = new Thread()
    	{
    		public void run()
    		{
    			Intent sound = new Intent(getApplicationContext(),SpatterSound.class);
    			startService(sound);
    			SpatterEngine.context=getApplicationContext();
    		}
    	};    	
    	SpatterEngine.soundThread.start();
    	
    	final SpatterEngine engine = new SpatterEngine();
    	
        
        ImageButton start = (ImageButton)findViewById(R.id.btnStart);
        start.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// run game		
				Intent game = new Intent(getApplicationContext(), SpatterGame.class);
				SpatterMenu.this.startActivity(game);
			}
		});
        
        ImageButton settings = (ImageButton)findViewById(R.id.btnSettings);
        settings.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// run game		
				Intent sett = new Intent(getApplicationContext(), SpittSettingsActivity.class);
				SpatterMenu.this.startActivity(sett);
				
			
			}
		});
        
        ImageButton exit = (ImageButton)findViewById(R.id.btnExit);
        exit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				boolean dispose = false;
				dispose = engine.onExit(v);
				if (dispose == true)
				{
					int pid  = android.os.Process.myPid();
					android.os.Process.killProcess(pid);
				}
				
			}
		});
    }



}
