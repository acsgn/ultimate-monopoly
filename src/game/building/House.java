package game.building; 
public class House extends Building {
	private static final long serialVersionUID = 1L;
	
	private static final BuildingType type = BuildingType.HOUSE;
	
	public BuildingType getBuildingType() {
		return type;
	}
	
	public House() {
		super();
		// TODO Auto-generated constructor stub
	}

}
