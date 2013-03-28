package cz.skylights.spitt.activity;


import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.R;
import cz.skylights.spitt.model.PlayerModel;
import cz.skylights.spitt.xml.GameDataParser;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class SpittSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		GameDataParser parser = new GameDataParser(this);
		try {
			PlayerModel player = parser.parseData(PlayerModel.class, R.xml.player);
			TextView myXmlContent = (TextView)findViewById(R.id.my_xml);
			String content = "Parsovano:" + player.getShipType() + " | " + player.getPrimaryWeapon() + " | " + player.getTotalScore();
			myXmlContent.setText(content);
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
