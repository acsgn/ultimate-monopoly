package game.square.estate;

import java.util.ArrayList;

import game.Player;
import game.building.Building;
import game.strategy.RentStrategyFactory;
import network.NetworkFacade;

public class Property extends Estate {
	
	private static final EstateSquareType type = EstateSquareType.PROPERTY;
	
	private int rent;
	private ArrayList<Building> buildings;
	private TitleDeed titleDeed;
	private ColorGroup colorGroup;
	
	public Property(String name, int price, TitleDeed titleDeed) {
		super(name, price, type);
		this.titleDeed = titleDeed;
		buildings = new ArrayList<>();
	}
	
	public void setColorGroup(ColorGroup colorGroup){
		this.colorGroup =  colorGroup;
	}
	@Override
	public void executeWhenLanded(Player player) {
		// TODO
		if(this.getOwner()!=null){
			if(this.getOwner().getName().equals(player.getName())){
				String message = "ACTION/You landed on your owned property Haha!";
				player.publishGameEvent(message);
			}else{
				NetworkFacade.getInstance().sendMessage(player.getName()+"/PAYRENT/"+this.getOwner().getName());
			}
		}else{
			String message = "ACTION/You can buy this property!";
			player.publishGameEvent(message);
		}
	}

	@Override
	public void setOwner(Player owner){
		super.setOwner(owner);
		colorGroup.updateMonopolyAndMajority();
		if(owner.getName().equals(colorGroup.getMonopolyOwnerName())){
			owner.addMonopolyGroup(colorGroup);
		}
		if(owner.getName().equals(colorGroup.getMajorityOwnerName())){
			owner.addMajorityGroup(colorGroup);
		}
	}

	//The rent system is prone to change
	public int getRent() {
		rent = RentStrategyFactory.getInstance().getStrategy(this).getRent(this);
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public void addBuilding(Building b){
		buildings.add(b);
	}
	public void dropBuildings(){
		buildings = new ArrayList<>();
	}
	public TitleDeed getTitleDeed(){
		return titleDeed;
	}
	public ColorGroup getColorGroup(){
		return colorGroup;
	}
	
}