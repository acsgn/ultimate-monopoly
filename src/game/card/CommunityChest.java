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
		}
		// ref: cards "Changing Lanes", the alteration with 'below'
		else if (name.equalsIgnoreCase("Changing Lanes - Below")) {
			
			// Player does not move if they are already on the outer track.
			if (player.getCurrentTrack() == TrackType.INNER_TRACK) {
				
				// there is only one Community Chest square in the inner track (at
				// index 4), so we can proceed without checking anything else. 
				player.goTo(TrackType.MIDDLE_TRACK, 6);
				
			} else if (player.getCurrentTrack() == TrackType.MIDDLE_TRACK) {
				
				int curSquareInd = player.getPosition();
				
				if (curSquareInd == 2) {
					player.goTo(TrackType.OUTER_TRACK, 4);
				} else if (curSquareInd == 17) {
					player.goTo(TrackType.OUTER_TRACK, 23);
				} else if (curSquareInd == 33) {
					player.goTo(TrackType.OUTER_TRACK, 47);
				}
				
			}
			
		}
		// ref: cards "Changing Lanes", the alteration with 'above'
		else if (name.equalsIgnoreCase("Changing Lanes - Above")) {

			int curSquareInd = player.getPosition();
			
			// Player does not move if they are already on the inner track.
			if (player.getCurrentTrack() == TrackType.MIDDLE_TRACK) {
				
				if (curSquareInd == 2) {
					player.goTo(TrackType.INNER_TRACK, 0);
				} else if (curSquareInd == 17) {
					player.goTo(TrackType.INNER_TRACK, 11);
				} else if (curSquareInd == 33) {
					player.goTo(TrackType.INNER_TRACK, 19);
				}
				
			} else if (player.getCurrentTrack() == TrackType.OUTER_TRACK) {
				
				if (curSquareInd == 2) {
					player.goTo(TrackType.MIDDLE_TRACK, 0);
				} else if (curSquareInd == 24) {
					player.goTo(TrackType.MIDDLE_TRACK, 18);
				} else if (curSquareInd == 36) {
					player.goTo(TrackType.MIDDLE_TRACK, 26);
				} else if (curSquareInd == 46) {
					player.goTo(TrackType.MIDDLE_TRACK, 32);
				}
				
			}
		}
		// ref: card "House Condemned"
		else if (name.equalsIgnoreCase("House Condemned")) {
			
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
		// ref: card "Advance to this Track's Pay Corner"
		// !!! Name of this particular card has been shortened in the code.
		else if (name.equalsIgnoreCase("Advance to Pay")) {
			// TODO This code treats the cases as if going to pay corners does NOT
			// 	    automatically pay the player. In case it does, delete the increase
			//      money commands. I shall review the pay corner code tomorrow.
			if (player.getCurrentTrack().equals(TrackType.OUTER_TRACK)) {
				player.goTo(TrackType.OUTER_TRACK, 0);
				player.increaseMoney(400);
			} else if (player.getCurrentTrack().equals(TrackType.MIDDLE_TRACK)) {
				player.goTo(TrackType.MIDDLE_TRACK, 0);
				player.increaseMoney(200);
			} else if (player.getCurrentTrack().equals(TrackType.INNER_TRACK)) {
				player.goTo(TrackType.INNER_TRACK, 0);
				player.increaseMoney(300);
			}
		}
		
		
		
		
		/* list of Community Chest cards not implemented:
		 * Opening Night Tickets!
		 * Sale of Stock Bonus
		 * Happy Birthday!
		 * Just Say "NO"!
		 * Business Trip
		 * Game Night!
		 * April 15, Taxes Due!
		 * A Moving Experience
		 * Special Online Pricing
		 * Entrepreneur of the Year!
		 * Elected District Attorney
		 * Renovation Success
		 * You're Getting Married
		 * Deal Buster
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
