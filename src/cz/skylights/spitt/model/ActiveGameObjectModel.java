package cz.skylights.spitt.model;

import java.math.BigDecimal;
import java.util.List;

import cz.skylights.spitt.xml.GameDataParser;

public class ActiveGameObjectModel extends GameObjectModel{
	
	private Integer strenght;
	private String weaponType;
	private String weaponTrajectory;
	private BigDecimal weaponTrajectoryspeed;
	private List<EffectObjectModel> efekty;
	
	public ActiveGameObjectModel(GameDataParser gameDataParser) {
		super(gameDataParser);
		// TODO Auto-generated constructor stub
	}
	

	public Integer getStrenght() {
		return strenght;
	}

	public void setStrenght(Integer strenght) {
		this.strenght = strenght;
	}

	public String getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(String weaponType) {
		this.weaponType = weaponType;
	}

	public String getWeaponTrajectory() {
		return weaponTrajectory;
	}

	public void setWeaponTrajectory(String weaponTrajectory) {
		this.weaponTrajectory = weaponTrajectory;
	}

	public List<EffectObjectModel> getEfekty() {
		return efekty;
	}

	public void setEfekty(List<EffectObjectModel> efekty) {
		this.efekty = efekty;
	}
	
	public BigDecimal getWeaponTrajectoryspeed() {
		return weaponTrajectoryspeed;
	}


	public void setWeaponTrajectoryspeed(BigDecimal weaponTrajectoryspeed) {
		this.weaponTrajectoryspeed = weaponTrajectoryspeed;
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
		if("STRENGH".equals(currEl)){
			this.setFrames(Integer.valueOf(xmlResParser.getText()));
		}
		else if("SPEED".equals(currEl)){
			this.setSpeedObject(Double.valueOf(xmlResParser.getText()).floatValue());
		}
		else if("WEAPON_TYPE".equals(currEl)){
			this.setTrajectoryType(xmlResParser.getText());
		}
		else if("WEAPON_TRAJECTORY".equals(currEl)){
			this.setWeaponTrajectory(xmlResParser.getText());
			
			for(int i = 0, n = this.xmlResParser.getAttributeCount(); i < n; ++i) {								
				if(this.xmlResParser.getAttributeName(i).equals("speed")){
					weaponTrajectoryspeed = new BigDecimal(this.xmlResParser.getAttributeValue(i));
					break;
				}
			}
		}
		
	}

}
