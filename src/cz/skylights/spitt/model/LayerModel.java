package cz.skylights.spitt.model;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;


public class LayerModel extends AbstarctModel{
	
	public static final String elName = "LAYER";
	private Integer id;
	private String name;
	private String type;
	private float layerSpeed;
	private List<LevelModel> levels;
	private LevelModel newLevel;
	
	Class <? extends GameObjectModel> object;
	private List<? extends GameObjectModel> objects;
	
	public LayerModel (GameDataParser gameDataParser,Integer id,String name){
		this.id=id;
		this.name=name;
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
		if("OBJECT".equals(this.xmlResParser.getName())){
			for(int i = 0, n = this.xmlResParser.getAttributeCount(); i < n; ++i) {								
				if(this.xmlResParser.getAttributeName(i).equals("type")){
					type = this.xmlResParser.getAttributeValue(i);
					break;
				}
			}
			newLevel = new LevelModel(parser,id);
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
