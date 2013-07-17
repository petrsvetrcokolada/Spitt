package cz.skylights.spitt.model;

import java.io.IOException;
import java.math.BigDecimal;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;

public class GameObjectModel extends AbstarctModel{
		
	public static final String elName = "OBJECT";
	
	private String id;
	private String ResourceId;
	private Integer width;
	private Integer height;
	private float speedObject;
	private String trajectoryType;
	private Long startTime;
	private Long xPosition;
	private Long moveDelay;
	private Integer frames;
	private BigDecimal frameSpeed;
	
	
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

	

	public String getResourceId() {
		return ResourceId;
	}

	public void setResourceId(String resourceId) {
		ResourceId = resourceId;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public float getSpeedObject() {
		return speedObject;
	}

	public void setSpeedObject(float speedObject) {
		this.speedObject = speedObject;
	}

	public String getTrajectoryType() {
		return trajectoryType;
	}

	public void setTrajectoryType(String trajectoryType) {
		this.trajectoryType = trajectoryType;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getxPosition() {
		return xPosition;
	}

	public void setxPosition(Long xPosition) {
		this.xPosition = xPosition;
	}

	public Long getMoveDelay() {
		return moveDelay;
	}

	public void setMoveDelay(Long moveDelay) {
		this.moveDelay = moveDelay;
	}

	public Integer getFrames() {
		return frames;
	}

	public void setFrames(Integer frames) {
		this.frames = frames;
	}

	public BigDecimal getFrameSpeed() {
		return frameSpeed;
	}

	public void setFrameSpeed(BigDecimal frameSpeed) {
		this.frameSpeed = frameSpeed;
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
		if("RESOURCE_ID".equals(currEl)){
			this.setResourceId(xmlResParser.getText());
		}
		else if("SPEED".equals(currEl)){
			this.setSpeedObject(Double.valueOf(xmlResParser.getText()).floatValue());
		}
		else if("TRAJECTORY".equals(currEl)){
			this.setTrajectoryType(xmlResParser.getText());
		}
		else if("WIDTH".equals(currEl)){
			this.setWidth(Integer.valueOf(xmlResParser.getText()));
		}
		else if("HEIGHT".equals(currEl)){
			this.setHeight(Integer.valueOf(xmlResParser.getText()));
		}
		else if("FRAMES".equals(currEl)){
			this.setFrames(Integer.valueOf(xmlResParser.getText()));
		}
		else if("START_TIME".equals(currEl)){
			this.setStartTime(Long.valueOf(xmlResParser.getText()));
		}
		else if("X_POS".equals(currEl)){
			this.setxPosition(Long.valueOf(xmlResParser.getText()));
		}
		else if("MOVE_DELAY".equals(currEl)){
			this.setMoveDelay(Long.valueOf(xmlResParser.getText()));
		}
		else if("FRAMES".equals(currEl)){
			this.setFrames(Integer.valueOf(xmlResParser.getText()));
			
			for(int i = 0, n = this.xmlResParser.getAttributeCount(); i < n; ++i) {								
				if(this.xmlResParser.getAttributeName(i).equals("speed")){
					this.frameSpeed = new BigDecimal(this.xmlResParser.getAttributeValue(i));
					break;
				}
			}
		}
	}

}
