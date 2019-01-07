package game.square.estate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import game.building.Building;
import game.building.Hotel;
import game.building.House;
import game.building.Skyscraper;

public class ColorGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PropertyColor color;
	private ArrayList<Property> propertyColorSquares;
	private PropertyLevel level;
	private String monopolyOwnerName = "";
	private int squaresUpgraded = 0;

	public ColorGroup(PropertyColor color) {
		propertyColorSquares = new ArrayList<>();
		this.color = color;
		this.level = PropertyLevel.ZERO_HOUSE_LEVEL;
	}

	public void addPropertySquare(Property s) {
		propertyColorSquares.add(s);
		s.setColorGroup(this);
	}

	public PropertyColor getColor() {
		return color;
	}

	public void upgradeLevel() {
		level = level.next();
	}

	public void updateLevel() {
		squaresUpgraded++;
		if (squaresUpgraded == propertyColorSquares.size()) {
			squaresUpgraded = 0;
			upgradeLevel();
		}
	}
	public void decreaseLevel(){
		if(level!=PropertyLevel.ZERO_HOUSE_LEVEL)
			level = level.previous();
		else{
			squaresUpgraded = 0;
		}
		for(Property p : propertyColorSquares){
			ArrayList<Building> buildings = p.getBuildings();
			switch (buildings.size()) {
			case 0:
				break;
			case 1:
				if (buildings.get(0) instanceof House) {
					buildings.remove(0);
				} else if (buildings.get(0) instanceof Hotel) {
					buildings.remove(0);
					buildings.add(new House());
					buildings.add(new House());
					buildings.add(new House());
					buildings.add(new House());
				} else {
					buildings.remove(0);
					buildings.add(new Hotel());
				}
				break;
			default:
				buildings.remove(0);
				break;
			}
		}	
	}
	public void removeBuilding(Property p){
		switch(p.getBuildings().size()){
		case 0: break;
		case 1: 
			if(p.getBuildings().get(0) instanceof House){
				p.getBuildings().remove(0);
			}else if(p.getBuildings().get(0) instanceof Hotel){
				p.getBuildings().remove(0);
				p.addBuilding(new House());
				p.addBuilding(new House());
				p.addBuilding(new House());
				p.addBuilding(new House());
			}else if(p.getBuildings().get(0) instanceof Skyscraper){
				p.getBuildings().remove(0);
				p.addBuilding(new Hotel());
			}
			break;
		default: 
			p.getBuildings().remove(0);
			break;
		}
		squaresUpgraded = (squaresUpgraded - 1 + propertyColorSquares.size()) % propertyColorSquares.size(); 
		switch(level){
		case ZERO_HOUSE_LEVEL:
			break;
		case FOUR_HOUSE_LEVEL:
			for(Property pl : propertyColorSquares){
				if(pl.getBuildings().size()==3){
					level = level.previous();
					break;
				}
			}
			break;
		case HOTEL_LEVEL:
			for(Property pl : propertyColorSquares){
				if(pl.getBuildings().size()==4){
					level = level.previous();
					break;
				}
			}
			break;
		case ONE_HOUSE_LEVEL:
			for(Property pl : propertyColorSquares){
				if(pl.getBuildings().size()==0){
					level = level.previous();
					break;
				}
			}
			break;
		case SKYSCRAPER_LEVEL:
			for(Property pl : propertyColorSquares){
				if(pl.getBuildings().get(0) instanceof Hotel){
					level = level.previous();
					break;
				}
			}
			break;
		case THREE_HOUSE_LEVEL:
			for(Property pl : propertyColorSquares){
				if(pl.getBuildings().size()==2){
					level = level.previous();
					break;
				}
			}
			break;
		case TWO_HOUSE_LEVEL:
			for(Property pl : propertyColorSquares){
				if(pl.getBuildings().size()==1){
					level = level.previous();
					break;
				}
			}
			break;
		default:
			break;
		
		}
	}

	public ArrayList<Property> getAvailableSquares() {
		ArrayList<Property> properties = new ArrayList<>();
		if (level == PropertyLevel.ZERO_HOUSE_LEVEL) {
			for (Property p : propertyColorSquares) {
				if (p.getBuildings().size() == 0) {
					properties.add(p);
				}
			}
		} else if (level == PropertyLevel.ONE_HOUSE_LEVEL) {
			for (Property p : propertyColorSquares) {
				if (p.getBuildings().size() == 1) {
					properties.add(p);
				}
			}
		} else if (level == PropertyLevel.TWO_HOUSE_LEVEL) {
			for (Property p : propertyColorSquares) {
				if (p.getBuildings().size() == 2) {
					properties.add(p);
				}
			}
		} else if (level == PropertyLevel.THREE_HOUSE_LEVEL) {
			for (Property p : propertyColorSquares) {
				if (p.getBuildings().size() == 3) {
					properties.add(p);
				}
			}
		} else if (level == PropertyLevel.FOUR_HOUSE_LEVEL) {
			for (Property p : propertyColorSquares) {
				if (p.getBuildings().size() == 4) {
					properties.add(p);
				}
			}
		} else if (level == PropertyLevel.HOTEL_LEVEL) {
			for (Property p : propertyColorSquares) {
				boolean noSkyscraper = true;
				for (Building b : p.getBuildings()) {
					if (b instanceof Skyscraper) {
						noSkyscraper = false;
					}
				}
				if (noSkyscraper)
					properties.add(p);
			}
		}
		return properties;
	}

	public void updateMonopoly() {
		ArrayList<String> ownerNames = new ArrayList<>();
		for (Property p : propertyColorSquares) {
			if (p.getOwner() != null) {
				ownerNames.add(p.getOwner().getName());
			}
		}
		HashMap<Integer, String> commonOwners = new HashMap<>();
		for (String name : ownerNames) {
			int freq = Collections.frequency(ownerNames, name);
			commonOwners.put(freq, name);
		}
		int max_freq = Collections.max(commonOwners.keySet());
		String mostCommonPlayer = commonOwners.get(max_freq);
		if (max_freq == propertyColorSquares.size()) {
			monopolyOwnerName = mostCommonPlayer;
		}
	}

	public String getMonopolyOwnerName() {
		updateMonopoly();
		return monopolyOwnerName;
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

	public void setColor(PropertyColor color) {
		this.color = color;
	}

	public void setMonopolyOwnerName(String monopolyOwnerName) {
		this.monopolyOwnerName = monopolyOwnerName;
	}

}
