package game.building;

import java.io.Serializable;

public abstract class Building implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BuildingType buildingType;
	
	public BuildingType getBuildingType() {
		return buildingType;
	}
	
	public Building() {
		
		
	}
	
}
