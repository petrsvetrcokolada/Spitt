package cz.skylights.spitt.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;


public class LevelModel extends AbstarctModel{
	
	public static final String elName = "LEVEL";
	
	private Integer levelNum;
	private String type;
	private String name;
	private String resource;
	private String bigIcon;
	private String smallIcon;
	 
	private List<LayerModel> layerList;
	private LayerModel layer;
	

	public LevelModel(GameDataParser gameDataParser,Integer levelId) {
		levelNum=levelId;
		try {
			parse(gameDataParser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getLevelNum() {
		return levelNum;
	}


	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}
	
	
	public String getBigIcon() {
		return bigIcon;
	}


	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}


	public String getSmallIcon() {
		return smallIcon;
	}


	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}


	@Override
	protected void onStartElement() {
		
		
	}
	@Override
	protected boolean onEndElement() {
		if (elName.equals(this.xmlResParser.getName())) {
			return false;
		} 
		
		return true;
	}
	@Override
	protected void onCharacters() {
		if("NAME".equals(currEl)){
			this.setName(xmlResParser.getText());
		}else if ("TYPE".equals(currEl)){
			this.setType(xmlResParser.getText());
		}else if ("RESOURCE".equals(currEl)){
			this.setResource(xmlResParser.getText());
		}else if ("BIG_ICON".equals(currEl)){
			this.setBigIcon(xmlResParser.getText());
		}else if ("SMALL_ICON".equals(currEl)){
			this.setSmallIcon(xmlResParser.getText());
		}
		
	}

}
