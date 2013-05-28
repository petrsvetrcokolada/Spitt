package cz.skylights.spitt.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;


public class GameModel extends AbstarctModel{
	
	public static final String elName = "GAME";
	
	private EpisodeModel episoda;
	private List<EpisodeModel> episodes;
	private ResourceManager resources;
	
	Integer episodeId;
	String episodeName;
	
	public GameModel(GameDataParser gameDataParser) {
		try {
			parse(gameDataParser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public List<EpisodeModel> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<EpisodeModel> episodes) {
		this.episodes = episodes;
	}
	
	@Override
	protected void onStartElement() {
		
		if("EPISODE".equals(this.xmlResParser.getName())){
			for(int i = 0, n = this.xmlResParser.getAttributeCount(); i < n; ++i) {								
				if(this.xmlResParser.getAttributeName(i).equals("id")){
					episodeId = this.xmlResParser.getAttributeIntValue(i, 0);
					break;
				}
				if(this.xmlResParser.getAttributeName(i).equals("name")){
					episodeName = this.xmlResParser.getAttributeValue(i);				
					break;
				}
			}
			episoda = new EpisodeModel(parser,episodeId,episodeName);
		}
	}
	@Override
	protected boolean onEndElement() {
		if (elName.equals(this.xmlResParser.getName())) {
			return false;
		} 
		else if("EPISODE".equals(this.xmlResParser.getName())){
			episodes.add(episoda);
		}
		
		return true;
	}
	@Override
	protected void onCharacters() {
		
	}

}
