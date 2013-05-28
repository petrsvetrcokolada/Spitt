package cz.skylights.spitt.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.R;
import cz.skylights.spitt.model.GameModel;
import cz.skylights.spitt.model.PlayerModel;
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class GameDataParser {
	
	private Resources res;
	private XmlResourceParser xmlResParser;
	
	public GameDataParser (){}
	
	public GameDataParser (Activity activita){
		res= activita.getResources();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parseData(Class<T> parseClass, int sourceXml) throws XmlPullParserException, IOException{
		T data= null;
	   xmlResParser = res.getXml(R.xml.player);
	   xmlResParser.next();
	   int eventType = xmlResParser.getEventType();
	   while (eventType != XmlPullParser.END_DOCUMENT){
		    if(eventType == XmlPullParser.START_DOCUMENT)
		    {
		   
		    }
		    else if(eventType == XmlPullParser.START_TAG)
		    {//Okres.elName.equals(currEl)
		    	String currEl = xmlResParser.getName();
		    	if(PlayerModel.elName.equals(currEl)){
		    		data = (T) new PlayerModel(this);
		    	}
		    	else if(GameModel.elName.equals(currEl)){
		    		data = (T) new GameModel(this);
		    	}
		    }
		    else if(eventType == XmlPullParser.END_TAG)
		    {
		     //stringBuffer.append("\nEND_TAG: "+xmlResParser.getName());
		    }
		    else if(eventType == XmlPullParser.TEXT)
		    {
		     //stringBuffer.append("\nTEXT: "+xmlResParser.getText());
		    }
		    eventType = xmlResParser.next();
	   }
	   
	   return data;
	}
	
	public XmlResourceParser getXmlResourceParser(){
		return xmlResParser;
	}
}
