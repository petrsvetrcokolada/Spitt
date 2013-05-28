package cz.skylights.spitt.model;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;

public class GameObjectModel extends AbstarctModel{
		
	public static final String elName = "OBJECT";
	
	private String id;
	private String graphicType;
	private String sourceFileName;
	
	public GameObjectModel(GameDataParser gameDataParser){
		try {
			parse(gameDataParser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGraphicType() {
		return graphicType;
	}

	public void setGraphicType(String graphicType) {
		this.graphicType = graphicType;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	@Override
	protected void onStartElement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean onEndElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCharacters() {
		if("ID".equals(currEl)){
			this.setId(xmlResParser.getText());
		}
		else if("GR_TYPE".equals(currEl)){
			this.setGraphicType(xmlResParser.getText());
		}
		else if("OBJECT_TYPE".equals(currEl)){
			this.setGraphicType(xmlResParser.getText());
		}
	}

}
