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
	
	

	public LevelModel(GameDataParser gameDataParser) {
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

}
