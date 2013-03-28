package cz.skylights.spitt.model;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import cz.skylights.spitt.xml.GameDataParser;

public abstract class AbstarctModel {
	
	protected String currEl;
	
	protected abstract void onStartElement();
	protected abstract boolean onEndElement(); //pokud vrati false, koncim parsovani tohoto objektu
	protected abstract void onCharacters();
	
	protected GameDataParser parser;
	protected XmlResourceParser xmlResParser;
	
	protected void parse(GameDataParser parser) throws XmlPullParserException, IOException{
		this.parser = parser;
		this.xmlResParser = parser.getXmlResourceParser();
		
		int eventType = xmlResParser.getEventType();
		   while (eventType != XmlPullParser.END_DOCUMENT){
			    
			   if(eventType == XmlPullParser.START_DOCUMENT){
			   
			   }
			   else if(eventType == XmlPullParser.START_TAG){
				   currEl = xmlResParser.getName();
				   onStartElement();
			   }
			   else if(eventType == XmlPullParser.END_TAG){
				   currEl = xmlResParser.getName();
				   if (!onEndElement()) return;
			   }
			   else if(eventType == XmlPullParser.TEXT){
				   if (xmlResParser.isWhitespace()){
	                      break;
	                }
					
					onCharacters();
			   }
			   eventType = xmlResParser.next();
		   }
		   
	}

}
