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

	public void decreaseLevel() {
		level = level.previous();
		for (Property p : propertyColorSquares) {
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
			}

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
