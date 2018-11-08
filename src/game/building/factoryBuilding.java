package game.building;

public class factoryBuilding {
	
	private static factoryBuilding factorybuilding;
	private factoryBuilding(){
		
	}
	public static synchronized factoryBuilding factoryBuilding(){
		if(factorybuilding==null){
			factorybuilding = new factoryBuilding();
		}
		return factorybuilding;
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
