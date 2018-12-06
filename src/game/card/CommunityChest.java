package game.card;

import game.Player;
import game.Pool;
import game.TrackType;

import java.util.*;

import game.building.*;
import game.square.estate.*;

public class CommunityChest extends Card {

	private static final CardType type = CardType.COMMUNITY_CHEST;
	private String name;

	public CommunityChest(String name){
		this.name = name;
	}
	@Override
	public void executeAction(Player player) {
		/* removed
		// ref: card "Income Tax Refund"
		if(name.equalsIgnoreCase("Income Tax Refund")){
			player.increaseMoney(20);
		} 
		// ref: card "You Inherit 100$"
		else if (name.equalsIgnoreCase("You Inherit 100")) {
			player.increaseMoney(100);
		} 
		// ref: card "Insurance Premiums Due"
		else if (name.equalsIgnoreCase("Insurance Premiums Due")) {
			Pool.getInstance().payToPool(50);
			player.reduceMoney(50);
		} 
		// ref: card "Doctor's Fee"
		else if (name.equalsIgnoreCase("Doctor's Fee")) {
			Pool.getInstance().payToPool(50);
			player.reduceMoney(50);
		} */
		// ref: card "House Condemned"
		if (name.equalsIgnoreCase("House Condemned")) {

			List<Property> properties = player.getProperties();

			for (Property property : properties) {

				for (Building building : property.getBuildings()) {
					if (building.getBuildingType() == BuildingType.HOUSE) {

						ArrayList<Building> updateBuildings = property.getBuildings();
						updateBuildings.remove(building);
						property.setBuildings(updateBuildings);

						break;
					}
				}
			}

		}
		// ref: card "Pay Hospital Bills"
		else if (name.equalsIgnoreCase("Pay Hospital Bills")) {
			Pool.getInstance().payToPool(100);
			player.reduceMoney(100);
		}





		/* list of Community Chest cards not implemented:
		 * Happy Birthday!
		 * Game Night!
		 * A Moving Experience
		 * Special Online Pricing
		 * Entrepreneur of the Year!
		 * Renovation Success
		 * You're Getting Married
		 * Hostile Takeover
		 * Discount Travel
		 * Bargain Business!
		 * Vehicle Impounded!
		 * Reverse Rent!
		 * The Rent is Too Darn High!
		 * Always Tip Your Driver
		 * Be Kind, Rewind
		 * Assessed for Street Repairs
		 * Get out of Jail Free!
		 * ... (not a complete list)
		 */
	}

	@Override
	public CardType getCardType() {
		return type;
	}

	public String getName(){
		return name;
	}

}
