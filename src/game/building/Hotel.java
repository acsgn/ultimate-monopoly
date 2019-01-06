package game.building;

public class Hotel extends Building {
	private static final long serialVersionUID = 1L;
	
	private static final BuildingType type = BuildingType.HOTEL;
	
	public Hotel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BuildingType getBuildingType() {
		return type;
	}
}