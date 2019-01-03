package game.square.estate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import game.building.Building;
import game.building.Skyscraper;

public class ColorGroup {
	
	private PropertyColor color; 
	private ArrayList<Property> propertyColorSquares;
	private PropertyLevel level;
	private String monopolyOwnerName = ""; 
	private String majorityOwnerName = "";
	private int squaresUpgraded = 0;
	
	public ColorGroup(PropertyColor color){
		propertyColorSquares = new ArrayList<>();
		this.color = color; 
		this.level = PropertyLevel.ZERO_HOUSE_LEVEL;
	}
	
	public void addPropertySquare(Property s){
		propertyColorSquares.add(s);
		s.setColorGroup(this);
	}
	public PropertyColor getColor(){
		return color; 
	}
	public void upgradeLevel(){
		level = level.next();
	}
	public void updateLevel(){
		squaresUpgraded++; 
		if(squaresUpgraded == propertyColorSquares.size()){
			squaresUpgraded = 0;
			upgradeLevel();
		}
	}
	public ArrayList<Property> getAvailableSquares(){
		ArrayList<Property> properties = new ArrayList<>();
		if(level == PropertyLevel.ZERO_HOUSE_LEVEL){
			for(Property p : propertyColorSquares){
				if(p.getBuildings().size()==0){
					properties.add(p);
				}
			}
		}else if(level == PropertyLevel.ONE_HOUSE_LEVEL){
			for(Property p : propertyColorSquares){
				if(p.getBuildings().size()==1){
					properties.add(p);
				}
			}
		}else if(level == PropertyLevel.TWO_HOUSE_LEVEL){
			for(Property p : propertyColorSquares){
				if(p.getBuildings().size()==2){
					properties.add(p);
				}
			}
		}else if(level == PropertyLevel.THREE_HOUSE_LEVEL){
			for(Property p : propertyColorSquares){
				if(p.getBuildings().size()==3){
					properties.add(p);
				}
			}
		}else if(level == PropertyLevel.FOUR_HOUSE_LEVEL){
			for(Property p : propertyColorSquares){
				if(p.getBuildings().size()==4){
					properties.add(p);
				}
			}
		}else if(level == PropertyLevel.HOTEL_LEVEL){
			for(Property p : propertyColorSquares){
				boolean noSkyscraper = true;
				for(Building b : p.getBuildings()){
					if(b instanceof Skyscraper){
						noSkyscraper = false;
					}
				}
				if(noSkyscraper)
					properties.add(p);
			}
		}
		return properties;
	}
	
	public void updateMonopolyAndMajority(){
		ArrayList<String> ownerNames = new ArrayList<>();
		for(Property p : propertyColorSquares){
			if(p.getOwner()!=null){
				ownerNames.add(p.getOwner().getName());
			}
		}
		HashMap<Integer, String> commonOwners = new HashMap<>();
		for(String name : ownerNames){
			int freq = Collections.frequency(ownerNames, name);
			commonOwners.put(freq, name);
		}
		int max_freq = Collections.max(commonOwners.keySet());
		String mostCommonPlayer = commonOwners.get(max_freq);
		if(max_freq == propertyColorSquares.size()){
			majorityOwnerName = "";
			monopolyOwnerName = mostCommonPlayer;
		}
		if(max_freq == propertyColorSquares.size()-1){
			majorityOwnerName = mostCommonPlayer;
		}
	}

	public String getMonopolyOwnerName() {
		updateMonopolyAndMajority();
		return monopolyOwnerName;
	}
	
	public String getMajorityOwnerName() {
		updateMonopolyAndMajority();
		return majorityOwnerName;
	}

	public ArrayList<Property> getPropertyColorSquares() {
		return propertyColorSquares;
	}

	public void setPropertyColorSquares(ArrayList<Property> propertyColorSquares) {
		this.propertyColorSquares = propertyColorSquares;
	}

	public PropertyLevel getLevel() {
		return level;
	}

	public void setLevel(PropertyLevel level) {
		this.level = level;
	}

	public void setMajorityOwnerName(String majorityOwnerName) {
		this.majorityOwnerName = majorityOwnerName;
	}

	public void setColor(PropertyColor color) {
		this.color = color;
	}

	public void setMonopolyOwnerName(String monopolyOwnerName) {
		this.monopolyOwnerName = monopolyOwnerName;
	}

}
