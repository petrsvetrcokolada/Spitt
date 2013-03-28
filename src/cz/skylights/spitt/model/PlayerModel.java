package cz.skylights.spitt.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import cz.skylights.spitt.xml.GameDataParser;


public class PlayerModel extends AbstarctModel{
	
	public static final String elName = "PLAYER";
	
	private String shipType;
	private String primaryWeapon;
	private String secondaryWeapon;
	private String specWeapon;
	private Integer energy;
	private Integer shield;
	private Long totalScore;
	private Long money;
	private List<GamePosition> gameProgress;
	private GamePosition position;
	private Integer episodeId;
	
	private boolean isLevel = false;
	

	public PlayerModel(GameDataParser gameDataParser) {
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
	public String getShipType() {
		return shipType;
	}
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}
	public String getPrimaryWeapon() {
		return primaryWeapon;
	}
	public void setPrimaryWeapon(String primaryWeapon) {
		this.primaryWeapon = primaryWeapon;
	}
	public String getSecondaryWeapon() {
		return secondaryWeapon;
	}
	public void setSecondaryWeapon(String secondaryWeapon) {
		this.secondaryWeapon = secondaryWeapon;
	}
	public String getSpecWeapon() {
		return specWeapon;
	}
	public void setSpecWeapon(String specWeapon) {
		this.specWeapon = specWeapon;
	}
	public Integer getEnergy() {
		return energy;
	}
	public void setEnergy(Integer energy) {
		this.energy = energy;
	}
	public Integer getShield() {
		return shield;
	}
	public void setShield(Integer shield) {
		this.shield = shield;
	}
	public Long getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	public List<GamePosition> getGameProgress() {
		return gameProgress;
	}
	public void setGameProgress(List<GamePosition> gameProgress) {
		this.gameProgress = gameProgress;
	}
	
	@Override
	protected void onStartElement() {
		if("GAME_POS".equals(this.xmlResParser.getName())){
			if(gameProgress == null){
				gameProgress = new ArrayList<GamePosition>(); 
			}			
		}
		else if("EPIZODE".equals(this.xmlResParser.getName())){
			
			//position = new GamePosition();
			
			for(int i = 0, n = this.xmlResParser.getAttributeCount(); i < n; ++i) {								
				if(this.xmlResParser.getAttributeName(i).equals("id")){
					 episodeId = this.xmlResParser.getAttributeIntValue(i, 0);
					break;
				}
			}
			
		}
		else if("LEVEL".equals(this.xmlResParser.getName())){
			position = new GamePosition();
			position.setEpizodePos(episodeId);
			for(int i = 0, n = this.xmlResParser.getAttributeCount(); i < n; ++i) {								
				if(this.xmlResParser.getAttributeName(i).equals("id")){
					position.setLevelPos(this.xmlResParser.getAttributeIntValue(i, 0));
					break;
				}
			}
		}
		
	}
	@Override
	protected boolean onEndElement() {
		if (elName.equals(this.xmlResParser.getName())) {
			return false;
		} 
		else if("LEVEL".equals(this.xmlResParser.getName())){
			gameProgress.add(position);
		}
		
		return true;
	}
	@Override
	protected void onCharacters() {
		if("SHIP".equals(currEl)){
			this.setShipType(xmlResParser.getText());
		}else if ("PRIMARY_WEP".equals(currEl)){
			this.setPrimaryWeapon(xmlResParser.getText());
		}else if ("SECOND_WEP".equals(currEl)){
			this.setSecondaryWeapon(xmlResParser.getText());
		}else if ("SPEC_WEP".equals(currEl)){
			this.setSpecWeapon(xmlResParser.getText());
		}else if ("ENERGY".equals(currEl)){
			this.setEnergy(Integer.valueOf(xmlResParser.getText()));
		}else if ("SHIELD".equals(currEl)){
			this.setShield(Integer.valueOf(xmlResParser.getText()));
		}else if ("TOTAL_SCORE".equals(currEl)){
			this.setTotalScore(Long.valueOf(xmlResParser.getText()));	
		}else if ("MONEY".equals(currEl)){
			this.setMoney(Long.valueOf(xmlResParser.getText()));
		}
		else if("SCORE".equals(currEl)){
			position.setScorePos(Long.valueOf(xmlResParser.getText()));
		}
		
	}
	
    /*public static final int PLAYER_SHIP = R.drawable.sm;
    public static final int PLAYER_BULLET = R.drawable.shoot2;
    public static final int PLAYER_LEFT = 1; 
    public static final int PLAYER_RELEASE = 0; 
    public static final int PLAYER_RIGHT = 2;
    public static final int PLAYER_UP = 1;
    public static final int PLAYER_DW = -1;
    public static final int PLAYER_FRAMES_BETWEEN_ANI = 9; 
    public static final float PLAYER_BANK_SPEED = .1f;*/

}
