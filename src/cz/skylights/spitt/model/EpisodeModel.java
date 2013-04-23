package cz.skylights.spitt.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;


public class EpisodeModel{
	
	//public static final String elName = "EPIZODES";
	private Integer id;
	private String graphicType;
	private List<LevelModel> levels;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGraphicType() {
		return graphicType;
	}
	public void setGraphicType(String graphicType) {
		this.graphicType = graphicType;
	}
	public List<LevelModel> getLevels() {
		return levels;
	}
	public void setLevels(List<LevelModel> levels) {
		this.levels = levels;
	}
		
}
