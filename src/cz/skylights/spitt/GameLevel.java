package cz.skylights.spitt;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.R;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class GameLevel extends SpatterRenderer {
	
	public void loadLevelXML()
	throws XmlPullParserException, IOException
	{
		   StringBuffer stringBuffer = new StringBuffer();
		   Resources res = SpatterEngine.resources;
		   XmlResourceParser xpp = res.getXml(R.xml.level1);	
		   
		   xpp.next();
		   int eventType = xpp.getEventType();
		   while (eventType != XmlPullParser.END_DOCUMENT)
		   {
		    if(eventType == XmlPullParser.START_DOCUMENT)
		    {
		      // start xml
		    }
		    else if(eventType == XmlPullParser.START_TAG)
		    {
		    	// start tag		    
		    }
		    else if(eventType == XmlPullParser.END_TAG)
		    {
		    	// end xml
		    }
		    else if(eventType == XmlPullParser.TEXT)
		    {
		    	// text
		    }
		    eventType = xpp.next();
		   }		   		   
	}
}
