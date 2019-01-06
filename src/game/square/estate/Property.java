package game.square.estate;

import java.util.ArrayList;

import game.Player;
import game.building.Building;
import game.strategy.RentStrategyFactory;

public class Property extends Estate {
	private static final long serialVersionUID = 1L;

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

	public void setColorGroup(ColorGroup colorGroup) {
		this.colorGroup = colorGroup;
	}

	public void setOwner(Player owner) {
		super.setOwner(owner);
		colorGroup.updateMonopoly();
		if (owner.getName().equals(colorGroup.getMonopolyOwnerName())) {
			owner.addMonopolyGroup(colorGroup);
		}
	}

	// The rent system is prone to change
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

	public void addBuilding(Building b) {
		buildings.add(b);
	}

	public void dropBuildings() {
		buildings = new ArrayList<>();
	}

	public TitleDeed getTitleDeed() {
		return titleDeed;
	}

	public ColorGroup getColorGroup() {
		return colorGroup;
	}

	@Override
	public String information() {
		String information;
		if (getOwner() != null) {
			information = "PROPERTY/" + getOwner().getName() + "/";
			if (!buildings.isEmpty()) {
				information += buildings.get(0).getBuildingType().ordinal() + "/";
				information += buildings.size();
			} else
				information += "NOBUILDING";
		} else {
			information = "NOOWNER";
		}
		return information;
	}

}