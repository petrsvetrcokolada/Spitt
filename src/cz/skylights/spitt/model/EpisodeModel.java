package cz.skylights.spitt.model;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;


public class EpisodeModel extends AbstarctModel{
	
	public static final String elName = "EPIZODE";
	private Integer id;
	private Integer levelId;
	private String name;
	LevelModel newLevel;
	private List<LevelModel> levels;
	
	public EpisodeModel (GameDataParser gameDataParser,Integer episodeId,String episodeName){
		id=episodeId;
		name=episodeName;
		try {
			parse(gameDataParser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LevelModel> getLevels() {
		return levels;
	}
	public void setLevels(List<LevelModel> levels) {
		this.levels = levels;
	}
	@Override
	protected void onStartElement() {
		if("LEVEL".equals(this.xmlResParser.getName())){
			for(int i = 0, n = this.xmlResParser.getAttributeCount(); i < n; ++i) {								
				if(this.xmlResParser.getAttributeName(i).equals("id")){
					levelId = this.xmlResParser.getAttributeIntValue(i, 0);
					break;
				}
			}
			newLevel = new LevelModel(parser,levelId);
		}
		
	}
	@Override
	protected boolean onEndElement() {
		if("LEVEL".equals(this.xmlResParser.getName())){
			levels.add(newLevel);
		}
		else if (elName.equals(this.xmlResParser.getName())) {
			return false;
		}
		return true;
	}
	@Override
	protected void onCharacters() {
		
	}
		
}
