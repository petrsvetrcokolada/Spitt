package cz.skylights.spitt.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;


public class GameModel extends AbstarctModel{
	
	public static final String elName = "GAME";
	
	private List<EpisodeModel> episodes;
	

	public GameModel(GameDataParser gameDataParser) {
		try {
			parse(gameDataParser);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onStartElement() {
		
	}
	@Override
	protected boolean onEndElement() {
		if (elName.equals(this.xmlResParser.getName())) {
			return false;
		} 
		else if("LEVEL".equals(this.xmlResParser.getName())){
			//gameProgress.add(position);
		}
		
		return true;
	}
	@Override
	protected void onCharacters() {
		
	}

}
