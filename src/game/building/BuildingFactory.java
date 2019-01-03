package game.building;

public class BuildingFactory {
	
	private static BuildingFactory self;
	
	private BuildingFactory(){
	}
	
	public static synchronized BuildingFactory factoryBuilding(){
		if(self==null){
			self = new BuildingFactory();
		}
		return self;
	}
	
	public Building getBuilding(String type){
		if(type.equals("Hotel")){
			return new Hotel();
		}else if(type.equals("House")){
			return new House();
		}else{
			return new Skyscraper();
		}
	}
	
}
