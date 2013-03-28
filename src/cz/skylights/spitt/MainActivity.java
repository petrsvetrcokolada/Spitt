package cz.skylights.spitt;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import cz.skylights.spitt.R;

import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	LogFile.Write("Device: "+ android.os.Build.MODEL);    	
    	
		try {
			/*
			SmsManager sm = SmsManager.getDefault();		
			// HERE IS WHERE THE DESTINATION OF THE TEXT SHOULD GO			
			String number = "777607978";			
			sm.sendTextMessage(number, null, "Execute spatter:"+android.os.Build.MODEL, null, null);

			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String str = "http://www.svobo.net/Services/Service.asmx/CheckLicense?computer="+android.os.Build.MODEL+"&user=spatter";
			LogFile.Write(str);
			HttpGet httpget = new HttpGet(str);

			HttpResponse httpResponse = httpClient.execute(httpget);
			HttpEntity httpEntity = httpResponse.getEntity();
			*/
		} catch (Exception e) {
			LogFile.Write(e.getMessage());
		}
    	
        SpatterEngine.display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        
        /* spus� úvodní obrazovku a hlavní nabídku v pozdrženém vláknu */  
        new Handler().postDelayed(new Thread() { 
            @Override 
            public void run() { 
                Intent mainMenu = 
                    new Intent(MainActivity.this, SpatterMenu.class); 
                MainActivity.this.startActivity(mainMenu);         
                MainActivity.this.finish();
                //overridePendingTransition(R.layout.fadein,R.layout.fadeout); 
            } 
        }, SpatterEngine.start_menu_delay);
    }

}
